package io.github.hapjava.server.impl.dnssd;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import io.github.hapjava.server.HomekitAdvertiser;
import io.github.hapjava.server.impl.advertiser.HapDnsTxt;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
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
 * <p>Pass an {@link InetAddress} to scope registration to one LAN interface/host; the no-arg
 * constructor registers on all interfaces ({@code interfaceIndex = 0}) with the system default
 * hostname ({@code host = null}).
 *
 * @author Karel Goderis
 */
public class DnssdHomekitAdvertiser implements HomekitAdvertiser {

  private static final Logger logger = LoggerFactory.getLogger(DnssdHomekitAdvertiser.class);

  /** DNS-SD: register on all interfaces. */
  public static final int INTERFACE_INDEX_ANY = 0;

  private static final boolean SUPPORTED = detectSupport();

  private final DnsSdLibrary dnsSd;
  private final int interfaceIndex;
  private final String host;
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

  /** Register on all interfaces using the system default hostname. */
  public DnssdHomekitAdvertiser() {
    this(INTERFACE_INDEX_ANY, null);
  }

  /**
   * Scope registration to {@code localAddress}: SRV host is that address's host string, and the
   * DNS-SD interface index is taken from the matching {@link NetworkInterface} when available.
   *
   * @param localAddress LAN address the HAP TCP listener uses
   */
  public DnssdHomekitAdvertiser(InetAddress localAddress) {
    this(resolveInterfaceIndex(localAddress), resolveHost(localAddress));
  }

  /**
   * Full control over DNS-SD {@code interfaceIndex} and {@code host}.
   *
   * @param interfaceIndex {@link #INTERFACE_INDEX_ANY} (0) for all interfaces, or a platform
   *     interface index from {@link NetworkInterface#getIndex()}
   * @param host SRV target host (IP or DNS name), or {@code null} for the system default hostname
   */
  public DnssdHomekitAdvertiser(int interfaceIndex, String host) {
    if (!SUPPORTED) {
      throw new UnsupportedOperationException("Native DNS-SD is not available");
    }
    this.dnsSd = DnsSdLibrary.load();
    this.interfaceIndex = interfaceIndex;
    this.host = host;
  }

  /** Visible for tests that inject a mock library. */
  DnssdHomekitAdvertiser(DnsSdLibrary dnsSd) {
    this(dnsSd, INTERFACE_INDEX_ANY, null);
  }

  /** Visible for tests that inject a mock library with interface/host. */
  DnssdHomekitAdvertiser(DnsSdLibrary dnsSd, int interfaceIndex, String host) {
    this.dnsSd = dnsSd;
    this.interfaceIndex = interfaceIndex;
    this.host = host;
  }

  public static boolean isSupported() {
    return SUPPORTED;
  }

  /** DNS-SD interface index used for registration (0 = all interfaces). */
  public int getInterfaceIndex() {
    return interfaceIndex;
  }

  /** SRV host passed to DNS-SD, or {@code null} for the system default. */
  public String getHost() {
    return host;
  }

  private static boolean detectSupport() {
    try {
      DnsSdLibrary.load();
      return true;
    } catch (LinkageError | Exception e) {
      // LinkageError covers NoClassDefFoundError when optional JNA is absent, and
      // UnsatisfiedLinkError when libSystem / libdns_sd cannot be loaded.
      logger.debug("Native DNS-SD not available: {}", e.toString());
      return false;
    }
  }

  private static String resolveHost(InetAddress localAddress) {
    Objects.requireNonNull(localAddress, "localAddress");
    return localAddress.getHostAddress();
  }

  private static int resolveInterfaceIndex(InetAddress localAddress) {
    Objects.requireNonNull(localAddress, "localAddress");
    try {
      NetworkInterface nif = NetworkInterface.getByInetAddress(localAddress);
      if (nif != null) {
        int index = nif.getIndex();
        if (index > 0) {
          return index;
        }
      }
    } catch (SocketException e) {
      logger.debug("Could not resolve NetworkInterface for {}: {}", localAddress, e.toString());
    }
    logger.debug(
        "No network interface index for {}; registering on all interfaces (index 0)", localAddress);
    return INTERFACE_INDEX_ANY;
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
      logger.trace(
          "Advertising accessory {} via native DNS-SD (interfaceIndex={}, host={})",
          label,
          interfaceIndex,
          host);
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
            interfaceIndex,
            label,
            HapDnsTxt.SERVICE_TYPE,
            HapDnsTxt.DOMAIN_LOCAL,
            host,
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
        "Registered {} via native DNS-SD on port {} (interfaceIndex={}, host={}, no UDP 5353 bind)",
        HapDnsTxt.SERVICE_TYPE,
        port,
        interfaceIndex,
        host);
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
