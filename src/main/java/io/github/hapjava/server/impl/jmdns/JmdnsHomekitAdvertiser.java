package io.github.hapjava.server.impl.jmdns;

import io.github.hapjava.server.HomekitAdvertiser;
import io.github.hapjava.server.impl.advertiser.HapDnsTxt;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** HomeKit advertiser backed by JmDNS (binds / uses UDP 5353). */
public class JmdnsHomekitAdvertiser implements HomekitAdvertiser {

  private final JmDNS jmdns;
  private boolean discoverable = true;
  private static final Logger logger = LoggerFactory.getLogger(JmdnsHomekitAdvertiser.class);
  private boolean isAdvertising = false;

  private String label;
  private String mac;
  private String setupId;
  private int port;
  private int configurationIndex;
  private ServiceInfo serviceInfo;
  private int category;

  public JmdnsHomekitAdvertiser(JmDNS jmdns) {
    this.jmdns = jmdns;
  }

  public JmdnsHomekitAdvertiser(InetAddress localAddress) throws UnknownHostException, IOException {
    jmdns = JmDNS.create(localAddress);
  }

  @Override
  public synchronized void advertise(
      String label, int category, String mac, int port, int configurationIndex, String setupId)
      throws Exception {
    if (isAdvertising) {
      throw new IllegalStateException("HomeKit advertiser is already running");
    }
    this.label = label;
    this.mac = mac;
    this.port = port;
    this.setupId = setupId;
    this.category = category;
    this.configurationIndex = configurationIndex;

    logger.trace("Advertising accessory " + label);

    registerService();

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  logger.trace("Stopping advertising in response to shutdown.");
                  jmdns.unregisterAllServices();
                }));
    isAdvertising = true;
  }

  @Override
  public synchronized void stop() {
    unregisterService();
  }

  @Override
  public synchronized void setDiscoverable(boolean discoverable) throws IOException {
    if (this.discoverable != discoverable) {
      this.discoverable = discoverable;
      if (isAdvertising) {
        logger.trace("Re-creating service due to change in discoverability to " + discoverable);
        unregisterService();
        registerService();
      }
    }
  }

  @Override
  public synchronized void setMac(String mac) throws IOException {
    if (this.mac != mac) {
      this.mac = mac;
      if (isAdvertising) {
        logger.trace("Re-creating service due to change in mac to " + mac);
        unregisterService();
        registerService();
      }
    }
  }

  @Override
  public synchronized void setConfigurationIndex(int revision) throws IOException {
    if (this.configurationIndex != revision) {
      this.configurationIndex = revision;
      if (isAdvertising) {
        logger.trace("Re-creating service due to change in configuration index to " + revision);
        unregisterService();
        registerService();
      }
    }
  }

  private void unregisterService() {
    if (serviceInfo != null) {
      jmdns.unregisterService(serviceInfo);
      serviceInfo = null;
    }
  }

  private void registerService() throws IOException {
    logger.info("Registering " + HapDnsTxt.SERVICE_TYPE_LOCAL + " on port " + port);
    if (this.serviceInfo != null) {
      throw new AssertionError(
          "Registering an already registered service without unregistering first is not allowed");
    }
    serviceInfo = buildServiceInfo();
    jmdns.registerService(serviceInfo);
  }

  private ServiceInfo buildServiceInfo() {
    logger.trace("MAC:" + mac + " Setup Id:" + setupId);
    Map<String, String> props =
        HapDnsTxt.buildProperties(discoverable, mac, label, setupId, configurationIndex, category);
    return ServiceInfo.create(HapDnsTxt.SERVICE_TYPE_LOCAL, label, port, 1, 1, props);
  }
}
