# Native DNS-SD / Bonjour advertiser

## Problem

HAP-Java historically advertises `_hap._tcp` with **JmDNS**, which opens a multicast UDP socket on **port 5353**.

On **macOS**, Apple’s `mDNSResponder` already owns `*:5353`. A second process (or a JVM) that tries to bind 5353 gets `Address already in use`, and Home / Controllers never see the accessory — even when the HAP TCP port (e.g. 9124) is listening.

Typical symptoms on macOS: empty `dns-sd -B _hap._tcp` results while `lsof -iUDP:5353` only lists `mDNSResponder`.

## Solution

This library adds a pluggable **`HomekitAdvertiser`** and a native implementation **`DnssdHomekitAdvertiser`** that calls Apple’s DNS-SD API (`DNSServiceRegister`) via JNA:

| Implementation | Class | Binds UDP 5353? | Typical platform |
| --- | --- | --- | --- |
| JmDNS (default / classic) | `JmdnsHomekitAdvertiser` | Yes (unless sharing an existing `JmDNS`) | Linux containers, Windows |
| Native DNS-SD | `DnssdHomekitAdvertiser` | **No** — registers through the system daemon | **macOS** (mDNSResponder), Linux with Avahi `libdns_sd` |

TXT records (`sf`, `id`, `md`, `sh`, `c#`, `s#`, `ff`, `ci`, `pv`) are shared via `HapDnsTxt` so both advertisers publish the same HAP payload.

**Policy belongs in the application** (e.g. openHAB): choose `jmdns(...)` or `nativeDnssd(...)` explicitly. Do not rely on HAP-Java to auto-pick.

## Quick start

Declare the optional JNA dependency if you use the native advertiser (JmDNS-only consumers can omit it):

```xml
<dependency>
  <groupId>net.java.dev.jna</groupId>
  <artifactId>jna</artifactId>
  <version>5.14.0</version>
</dependency>
```

```java
import io.github.hapjava.server.HomekitAdvertisers;
import io.github.hapjava.server.impl.HomekitServer;
import java.net.InetAddress;

InetAddress bind = InetAddress.getByName("192.168.0.6");

// Prefer scoping native registration to the HAP bind interface (SRV host stays system default):
HomekitServer server =
    new HomekitServer(bind, 9124, HomekitAdvertisers.nativeDnssd(bind));

// Or share an existing JmDNS (e.g. openHAB's):
// HomekitServer server =
//     new HomekitServer(bind, 9124, HomekitAdvertisers.jmdns(sharedJmDNS));
```

Check whether the native library **loads** (does not prove registration will succeed on the LAN):

```java
if (HomekitAdvertisers.isNativeDnssdSupported()) {
  // safe to construct nativeDnssd(...)
}
```

Existing constructors that take only `InetAddress` / `JmDNS` keep the previous JmDNS behaviour (backward compatible).

## Verifying on macOS (manual)

There is no automated live IT in CI (ubuntu runners lack `libdns_sd` / Bonjour). After starting a server with `HomekitAdvertisers.nativeDnssd(bind)`:

```bash
dns-sd -B _hap._tcp local.
dns-sd -L "<instance name>" _hap._tcp local.
# or
dns-sd -Z _hap._tcp local.
```

You should see the bridge/accessory instance and HAP TXT keys. No Java process should be listening on UDP 5353 (`lsof -iUDP:5353` should only show `mDNSResponder`).

## API surface

### `io.github.hapjava.server.HomekitAdvertiser`

```text
advertise(label, category, mac, port, configurationIndex, setupId)
stop()
setDiscoverable(boolean)
setMac(String)
setConfigurationIndex(int)
```

### `io.github.hapjava.server.HomekitAdvertisers`

| Factory | Behaviour |
| --- | --- |
| `jmdns(JmDNS)` | Wrap an existing JmDNS (force JmDNS / share openHAB’s instance) |
| `jmdns(InetAddress)` | Create dedicated JmDNS on that address |
| `nativeDnssd()` | Native Bonjour on **all** interfaces (`interfaceIndex = 0`, default host) |
| `nativeDnssd(InetAddress)` | Native Bonjour on that address’s interface index; SRV host = system default (`null`) |
| `isNativeDnssdSupported()` | Whether JNA + dns_sd **load** (not a live register probe) |
| `auto(InetAddress)` | **Deprecated** — prefer an explicit `jmdns` / `nativeDnssd` choice |

### `HomekitServer` constructors

```java
new HomekitServer(InetAddress localAddress, int port, HomekitAdvertiser advertiser)
new HomekitServer(InetAddress localAddress, int port, int nThreads, HomekitAdvertiser advertiser)
```

When an advertiser is supplied, `createBridge` / `createStandaloneAccessory` use it and do **not** open a private JmDNS socket.

## Implementation notes

* **JNA** is an **optional** dependency. It loads `System` on macOS/Darwin and `dns_sd` elsewhere (Avahi Bonjour compatibility). Missing JNA surfaces as `LinkageError` and `isNativeDnssdSupported()` returns `false`.
* `DNSServiceRegister` receives the TCP port in **network byte order**.
* Default `nativeDnssd()` uses `interfaceIndex = 0` (all interfaces) and `host = null` (system hostname). Prefer `nativeDnssd(InetAddress)` to limit the interface index when the HAP TCP listener is bound to one LAN address — still with `host = null`. Never pass a raw IP as DNS-SD `host` (no A record → Home hangs on “Setting up”).
* TXT is RFC 6763 length-prefixed `key=value` bytes (`HapDnsTxt.encodeTxt`). Key insertion order is stable (`LinkedHashMap`); RFC 6763 does not require a particular pair order.
* A daemon thread runs `DNSServiceProcessResult` until `DNSServiceRefDeallocate` on `stop()` / re-register.
* Re-registration (discoverability, MAC, `c#`) deallocates and registers again — same pattern as the JmDNS advertiser.

## Tests

| Test | Purpose |
| --- | --- |
| `HapDnsTxtTest` | Full TXT key set, stable order, wire encoding |
| `DnssdHomekitAdvertiserTest` | Mocked JNA register / interface+host / re-register / stop / port endianness |
| `JmdnsHomekitAdvertiserTest` | JmDNS register including full HAP property set |

```bash
mvn test
```

## Troubleshooting

| Symptom | Likely cause |
| --- | --- |
| `Address already in use` / “no suitable mDNS” | Still using dedicated JmDNS against mDNSResponder — switch to `nativeDnssd(bind)` or share an existing JmDNS |
| `UnsupportedOperationException: Native DNS-SD` | No JNA on classpath, or no `libSystem`/`libdns_sd` on this JVM/OS |
| `DNSServiceRegister failed with error code …` | Bad name/type, permissions, or daemon unavailable — check Console / `mDNSResponder` |
| Browse empty after register / too many interfaces | Prefer `nativeDnssd(localAddress)` instead of all-interfaces `nativeDnssd()` |

## License

Same MIT license as HAP-Java. Native symbols are provided by the host OS; this library only calls them through JNA.
