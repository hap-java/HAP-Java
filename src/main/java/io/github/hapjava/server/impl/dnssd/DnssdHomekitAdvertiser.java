package io.github.hapjava.server.impl.dnssd;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import io.github.hapjava.server.HomekitAdvertiser;
import io.github.hapjava.server.impl.advertiser.HapDnsTxt;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HomeKit advertiser that registers {@code _hap._tcp} through the platform DNS-SD API (macOS
 * mDNSResponder / Avahi dns_sd compat) without binding UDP 5353.
 *
 * <p>Uses JNA to call {@code DNSServiceRegister} / {@code DNSServiceRefDeallocate}.
 *
 * @author Karel Goderis
 */
public class DnssdHomekitAdvertiser implements HomekitAdvertiser {

  private static final Logger logger = LoggerFactory.getLogger(DnssdHomekitAdvertiser.class);

  private static final boolean SUPPORTED = detectSupport();

  private final DnsSdLibrary dnsSd;
  private final Object lock = new Object();

  private boolean discoverable = true;
  private boolean isAdvertising = false;

  private String label;
  private String mac;
  private String setupId;
  private int port;
  private int configurationIndex;
  private int category;

  private Pointer serviceRef;
  private Thread processThread;
  private final AtomicBoolean runProcessLoop = new AtomicBoolean(false);

  public DnssdHomekitAdvertiser() {
    if (!SUPPORTED) {
      throw new UnsupportedOperationException("Native DNS-SD is not available");
    }
    this.dnsSd = DnsSdLibrary.load();
  }

  /** Visible for tests that inject a mock library. */
  DnssdHomekitAdvertiser(DnsSdLibrary dnsSd) {
    this.dnsSd = dnsSd;
  }

  public static boolean isSupported() {
    return SUPPORTED;
  }

  private static boolean detectSupport() {
    try {
      DnsSdLibrary.load();
      return true;
    } catch (UnsatisfiedLinkError | Exception e) {
      logger.debug("Native DNS-SD not available: {}", e.toString());
      return false;
    }
  }

  @Override
  public void advertise(
      String label, int category, String mac, int port, int configurationIndex, String setupId)
      throws Exception {
    synchronized (lock) {
      if (isAdvertising) {
        throw new IllegalStateException("HomeKit advertiser is already running");
      }
      this.label = label;
      this.mac = mac;
      this.port = port;
      this.setupId = setupId;
      this.category = category;
      this.configurationIndex = configurationIndex;
      logger.trace("Advertising accessory {} via native DNS-SD", label);
      registerService();
      isAdvertising = true;
    }
  }

  @Override
  public void stop() {
    synchronized (lock) {
      unregisterService();
      isAdvertising = false;
    }
  }

  @Override
  public void setDiscoverable(boolean discoverable) throws IOException {
    synchronized (lock) {
      if (this.discoverable != discoverable) {
        this.discoverable = discoverable;
        if (isAdvertising) {
          logger.trace("Re-creating DNSSD service due to discoverability={}", discoverable);
          unregisterService();
          registerService();
        }
      }
    }
  }

  @Override
  public void setMac(String mac) throws IOException {
    synchronized (lock) {
      if (!Objects.equals(this.mac, mac)) {
        this.mac = mac;
        if (isAdvertising) {
          logger.trace("Re-creating DNSSD service due to mac change");
          unregisterService();
          registerService();
        }
      }
    }
  }

  @Override
  public void setConfigurationIndex(int revision) throws IOException {
    synchronized (lock) {
      if (this.configurationIndex != revision) {
        this.configurationIndex = revision;
        if (isAdvertising) {
          logger.trace("Re-creating DNSSD service due to c#={}", revision);
          unregisterService();
          registerService();
        }
      }
    }
  }

  private void registerService() throws IOException {
    if (serviceRef != null) {
      throw new AssertionError("DNSSD service already registered");
    }
    Map<String, String> props =
        HapDnsTxt.buildProperties(discoverable, mac, label, setupId, configurationIndex, category);
    byte[] txt = HapDnsTxt.encodeTxt(props);
    // DNSServiceRegister requires the port in network byte order (big-endian wire form).
    short netPort = toNetworkByteOrderPort(port);

    PointerByReference refOut = new PointerByReference();
    int err =
        dnsSd.DNSServiceRegister(
            refOut,
            0,
            0,
            label,
            HapDnsTxt.SERVICE_TYPE,
            HapDnsTxt.DOMAIN_LOCAL,
            null,
            netPort,
            (short) txt.length,
            txt,
            Pointer.NULL,
            Pointer.NULL);
    if (err != DnsSdLibrary.kDNSServiceErr_NoError) {
      throw new IOException("DNSServiceRegister failed with error code " + err);
    }
    serviceRef = refOut.getValue();
    if (serviceRef == null) {
      throw new IOException("DNSServiceRegister returned a null service reference");
    }
    logger.info(
        "Registered {} via native DNS-SD on port {} (no UDP 5353 bind)",
        HapDnsTxt.SERVICE_TYPE,
        port);
    startProcessLoop(serviceRef);
  }

  private void startProcessLoop(Pointer ref) {
    runProcessLoop.set(true);
    processThread =
        new Thread(
            () -> {
              try {
                // DNSServiceProcessResult blocks until daemon IPC data arrives (or FD closes).
                while (runProcessLoop.get()) {
                  int err = dnsSd.DNSServiceProcessResult(ref);
                  if (err != DnsSdLibrary.kDNSServiceErr_NoError) {
                    if (runProcessLoop.get()) {
                      logger.debug("DNSServiceProcessResult returned {}", err);
                    }
                    break;
                  }
                }
              } catch (Throwable t) {
                if (runProcessLoop.get()) {
                  logger.warn("DNSSD process loop ended: {}", t.toString());
                }
              }
            },
            "hap-dnssd-process");
    processThread.setDaemon(true);
    processThread.start();
  }

  private void unregisterService() {
    runProcessLoop.set(false);
    Pointer ref = serviceRef;
    serviceRef = null;
    // Deallocate first so a blocking DNSServiceProcessResult returns.
    if (ref != null) {
      try {
        dnsSd.DNSServiceRefDeallocate(ref);
      } catch (Throwable t) {
        logger.debug("DNSServiceRefDeallocate: {}", t.toString());
      }
    }
    if (processThread != null) {
      try {
        processThread.join(2000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      processThread = null;
    }
  }

  /** Convert a host-order TCP port to the short value DNS-SD expects (network byte order). */
  static short toNetworkByteOrderPort(int port) {
    ByteBuffer buf = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN);
    buf.putShort((short) port);
    buf.order(ByteOrder.nativeOrder());
    return buf.getShort(0);
  }
}
