package io.github.hapjava.server;

import java.io.IOException;

/**
 * Publishes a HomeKit accessory / bridge via DNS-SD ({@code _hap._tcp}).
 *
 * <p>The default implementation uses JmDNS and binds UDP port 5353. On platforms where a system
 * mDNS responder already owns that port (notably macOS {@code mDNSResponder}), use {@link
 * io.github.hapjava.server.impl.dnssd.DnssdHomekitAdvertiser} instead, which registers through the
 * native Bonjour / DNS-SD API without binding 5353.
 *
 * @see io.github.hapjava.server.impl.jmdns.JmdnsHomekitAdvertiser
 * @see io.github.hapjava.server.impl.dnssd.DnssdHomekitAdvertiser
 * @see HomekitAdvertisers
 * @author Karel Goderis
 */
public interface HomekitAdvertiser {

  /**
   * Start advertising the accessory.
   *
   * @param label instance name shown in Home / Discovery
   * @param category HomeKit accessory category ({@code ci} TXT key)
   * @param mac accessory id ({@code id} TXT key), typically a MAC-like string
   * @param port HAP TCP listen port
   * @param configurationIndex configuration number ({@code c#} TXT key)
   * @param setupId setup id used for the {@code sh} TXT hash
   */
  void advertise(
      String label, int category, String mac, int port, int configurationIndex, String setupId)
      throws Exception;

  /** Stop advertising and release native resources. */
  void stop();

  /** Update discoverability ({@code sf} TXT). Re-registers when already advertising. */
  void setDiscoverable(boolean discoverable) throws IOException;

  /** Update accessory id ({@code id} TXT). Re-registers when already advertising. */
  void setMac(String mac) throws IOException;

  /** Update configuration index ({@code c#} TXT). Re-registers when already advertising. */
  void setConfigurationIndex(int revision) throws IOException;
}
