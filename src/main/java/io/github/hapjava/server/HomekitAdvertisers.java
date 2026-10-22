package io.github.hapjava.server;

import io.github.hapjava.server.impl.dnssd.DnssdHomekitAdvertiser;
import io.github.hapjava.server.impl.jmdns.JmdnsHomekitAdvertiser;
import java.io.IOException;
import java.net.InetAddress;
import javax.jmdns.JmDNS;

/**
 * Factories for {@link HomekitAdvertiser} implementations.
 *
 * <p>Typical macOS usage:
 *
 * <pre>{@code
 * HomekitServer server =
 *     new HomekitServer(InetAddress.getByName("192.168.0.6"), 9124, HomekitAdvertisers.nativeDnssd());
 * }</pre>
 *
 * That listens on HAP TCP without binding UDP 5353; Bonjour/mDNSResponder publishes {@code
 * _hap._tcp}.
 *
 * @author Karel Goderis
 */
public final class HomekitAdvertisers {

  private HomekitAdvertisers() {}

  /**
   * Classic JmDNS advertiser (binds UDP 5353 unless sharing an existing {@link JmDNS}).
   *
   * @param jmdns existing JmDNS instance to register with
   * @return a JmDNS-backed advertiser
   */
  public static HomekitAdvertiser jmdns(JmDNS jmdns) {
    return new JmdnsHomekitAdvertiser(jmdns);
  }

  /**
   * Classic JmDNS advertiser that creates its own multicast socket on {@code localAddress}.
   *
   * @param localAddress address used to create the JmDNS instance
   * @return a JmDNS-backed advertiser
   * @throws IOException if JmDNS cannot be created on {@code localAddress}
   */
  public static HomekitAdvertiser jmdns(InetAddress localAddress) throws IOException {
    return new JmdnsHomekitAdvertiser(localAddress);
  }

  /**
   * Native DNS-SD / Bonjour advertiser (macOS {@code mDNSResponder}, or Avahi's dns_sd compat
   * layer). Does not bind UDP 5353. Requires the JNA runtime dependency.
   *
   * @return a native DNS-SD advertiser
   * @throws UnsupportedOperationException if the native library cannot be loaded
   */
  public static HomekitAdvertiser nativeDnssd() {
    if (!DnssdHomekitAdvertiser.isSupported()) {
      throw new UnsupportedOperationException(
          "Native DNS-SD (dns_sd / Bonjour) is not available on this platform/JVM");
    }
    return new DnssdHomekitAdvertiser();
  }

  /**
   * Prefer native DNS-SD when available (macOS); otherwise create a dedicated JmDNS instance on
   * {@code localAddress}.
   *
   * @param localAddress address used if falling back to JmDNS
   * @return a native DNS-SD advertiser when supported, otherwise a JmDNS-backed advertiser
   * @throws IOException if native DNS-SD is unavailable and JmDNS cannot be created
   */
  public static HomekitAdvertiser auto(InetAddress localAddress) throws IOException {
    if (DnssdHomekitAdvertiser.isSupported()) {
      return new DnssdHomekitAdvertiser();
    }
    return new JmdnsHomekitAdvertiser(localAddress);
  }

  /**
   * Whether {@link #nativeDnssd()} can be used on this JVM/OS.
   *
   * @return {@code true} if the platform DNS-SD library loaded successfully
   */
  public static boolean isNativeDnssdSupported() {
    return DnssdHomekitAdvertiser.isSupported();
  }
}
