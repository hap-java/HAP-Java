package io.github.hapjava.server.impl.dnssd;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * Minimal JNA mapping of Apple's {@code dns_sd.h} / Avahi compat {@code DNSServiceRegister} API.
 *
 * <p>On macOS the symbols live in {@code libSystem}; on Linux with Avahi they typically live in
 * {@code libdns_sd}.
 *
 * @author Karel Goderis
 */
public interface DnsSdLibrary extends Library {

  int kDNSServiceErr_NoError = 0;
  int kDNSServiceFlagsNoAutoRename = 0; // default flags = 0

  int DNSServiceRegister(
      PointerByReference sdRef,
      int flags,
      int interfaceIndex,
      String name,
      String regtype,
      String domain,
      String host,
      short port, // network byte order
      short txtLen,
      byte[] txtRecord,
      Pointer callBack, // may be null
      Pointer context);

  void DNSServiceRefDeallocate(Pointer sdRef);

  int DNSServiceRefSockFD(Pointer sdRef);

  int DNSServiceProcessResult(Pointer sdRef);

  static DnsSdLibrary load() {
    String os = System.getProperty("os.name", "").toLowerCase();
    if (os.contains("mac") || os.contains("darwin")) {
      return Native.load("System", DnsSdLibrary.class);
    }
    // Avahi Bonjour compatibility library
    return Native.load("dns_sd", DnsSdLibrary.class);
  }
}
