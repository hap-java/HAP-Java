package io.github.hapjava.server;

import io.github.hapjava.server.impl.dnssd.DnssdHomekitAdvertiser;
import io.github.hapjava.server.impl.jmdns.JmdnsHomekitAdvertiser;
import java.io.IOException;
import java.net.InetAddress;
import javax.jmdns.JmDNS;

/**
 * Factories for {@link HomekitAdvertiser} implementations.
 *
 * <p>Callers (e.g. openHAB) should choose the advertiser explicitly: use {@link #jmdns(JmDNS)} to
 * share an existing JmDNS instance, {@link #jmdns(InetAddress)} for a dedicated JmDNS socket, or
 * {@link #nativeDnssd()} / {@link #nativeDnssd(InetAddress)} for Bonjour without binding UDP 5353.
 * Do not rely on HAP-Java to pick a policy automatically.
 *
 * <pre>{@code
 * // Force JmDNS (e.g. share openHAB's instance):
 * HomekitAdvertiser adv = HomekitAdvertisers.jmdns(sharedJmDNS);
 *
 * // Native DNS-SD limited to one LAN address / interface:
 * HomekitAdvertiser adv = HomekitAdvertisers.nativeDnssd(InetAddress.getByName("192.168.0.6"));
 * }</pre>
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
   * Native DNS-SD / Bonjour advertiser on <strong>all</strong> interfaces ({@code interfaceIndex =
   * 0}, default host). Prefer {@link #nativeDnssd(InetAddress)} when the HAP TCP listener is bound
   * to a single LAN address.
   *
   * <p>Requires the optional JNA dependency on the classpath.
   *
   * @return a native DNS-SD advertiser
   * @throws UnsupportedOperationException if JNA or the native library cannot be loaded
   */
  public static HomekitAdvertiser nativeDnssd() {
    if (!DnssdHomekitAdvertiser.isSupported()) {
      throw new UnsupportedOperationException(
          "Native DNS-SD (dns_sd / Bonjour) is not available on this platform/JVM");
    }
    return new DnssdHomekitAdvertiser();
  }

  /**
   * Native DNS-SD / Bonjour advertiser scoped to {@code localAddress}: SRV host is that address and
   * registration uses the matching network interface index when resolvable.
   *
   * @param localAddress LAN address the HAP TCP server listens on
   * @return a native DNS-SD advertiser
   * @throws UnsupportedOperationException if JNA or the native library cannot be loaded
   */
  public static HomekitAdvertiser nativeDnssd(InetAddress localAddress) {
    if (!DnssdHomekitAdvertiser.isSupported()) {
      throw new UnsupportedOperationException(
          "Native DNS-SD (dns_sd / Bonjour) is not available on this platform/JVM");
    }
    return new DnssdHomekitAdvertiser(localAddress);
  }

  /**
   * Convenience: prefer native DNS-SD when the library loads, otherwise JmDNS on {@code
   * localAddress}.
   *
   * <p><strong>Applications should prefer an explicit {@link #jmdns(JmDNS)} / {@link
   * #nativeDnssd(InetAddress)} choice</strong> (policy belongs in openHAB / the integrator). This
   * helper only probes whether the native library <em>loads</em>; it does not verify that
   * registration will succeed on the network.
   *
   * @param localAddress address used for native scoping or JmDNS fallback
   * @return a native DNS-SD advertiser when the library loads, otherwise a JmDNS-backed advertiser
   * @throws IOException if native DNS-SD is unavailable and JmDNS cannot be created
   * @deprecated Prefer {@link #jmdns(JmDNS)}, {@link #jmdns(InetAddress)}, or {@link
   *     #nativeDnssd(InetAddress)} so the application chooses the advertiser.
   */
  @Deprecated
  public static HomekitAdvertiser auto(InetAddress localAddress) throws IOException {
    if (DnssdHomekitAdvertiser.isSupported()) {
      return new DnssdHomekitAdvertiser(localAddress);
    }
    return new JmdnsHomekitAdvertiser(localAddress);
  }

  /**
   * Whether {@link #nativeDnssd()} can be constructed on this JVM/OS.
   *
   * <p>Only checks that JNA and the platform DNS-SD library <em>load</em>. It does not register a
   * service or prove mDNS is reachable on the LAN.
   *
   * @return {@code true} if the platform DNS-SD library loaded successfully
   */
  public static boolean isNativeDnssdSupported() {
    return DnssdHomekitAdvertiser.isSupported();
  }
}
