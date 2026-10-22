# Native DNS-SD / Bonjour advertiser

## Problem

HAP-Java historically advertises `_hap._tcp` with **JmDNS**, which opens a multicast UDP socket on **port 5353**.

On **macOS**, Apple’s `mDNSResponder` already owns `*:5353`. A second process (or a JVM) that tries to bind 5353 gets `Address already in use`, and Home / Controllers never see the accessory — even when the HAP TCP port (e.g. 9124) is listening.

Typical symptoms on macOS: empty `dns-sd -B _hap._tcp` results while `lsof -iUDP:5353` only lists `mDNSResponder`.

## Solution

This fork adds a pluggable **`HomekitAdvertiser`** and a native implementation **`DnssdHomekitAdvertiser`** that calls Apple’s DNS-SD API (`DNSServiceRegister`) via JNA:

| Implementation | Class | Binds UDP 5353? | Typical platform |
| --- | --- | --- | --- |
| JmDNS (default / classic) | `JmdnsHomekitAdvertiser` | Yes (unless sharing an existing `JmDNS`) | Linux containers, Windows |
| Native DNS-SD | `DnssdHomekitAdvertiser` | **No** — registers through the system daemon | **macOS** (mDNSResponder), Linux with Avahi `libdns_sd` |

TXT records (`sf`, `id`, `md`, `sh`, `c#`, `s#`, `ff`, `ci`, `pv`) are shared via `HapDnsTxt` so both advertisers publish the same HAP payload.

## Quick start

```java
import io.github.hapjava.server.HomekitAdvertisers;
import io.github.hapjava.server.impl.HomekitServer;
import java.net.InetAddress;

InetAddress bind = InetAddress.getByName("192.168.0.6");
HomekitServer server =
    new HomekitServer(bind, 9124, HomekitAdvertisers.nativeDnssd());

// or: prefer native when present, else JmDNS
HomekitServer auto =
    new HomekitServer(bind, 9124, HomekitAdvertisers.auto(bind));
```

Check availability:

```java
if (HomekitAdvertisers.isNativeDnssdSupported()) {
  // safe to call nativeDnssd()
}
```

Existing constructors that take only `InetAddress` / `JmDNS` keep the previous JmDNS behaviour (backward compatible).

## Verifying on macOS

After starting a server with `HomekitAdvertisers.nativeDnssd()` (or `auto(...)`):

```bash
dns-sd -B _hap._tcp local.
# or
dns-sd -Z _hap._tcp local.
```

You should see the bridge/accessory instance. No Java process should be listening on UDP 5353 (`lsof -iUDP:5353` should only show `mDNSResponder`).

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
| `jmdns(JmDNS)` | Wrap an existing JmDNS |
| `jmdns(InetAddress)` | Create JmDNS on that address |
| `nativeDnssd()` | Native Bonjour / dns_sd (throws if unsupported) |
| `auto(InetAddress)` | Native if supported, else JmDNS |
| `isNativeDnssdSupported()` | Probe without constructing |

### `HomekitServer` constructors

```java
new HomekitServer(InetAddress localAddress, int port, HomekitAdvertiser advertiser)
new HomekitServer(InetAddress localAddress, int port, int nThreads, HomekitAdvertiser advertiser)
```

When an advertiser is supplied, `createBridge` / `createStandaloneAccessory` use it and do **not** open a private JmDNS socket.

## Implementation notes

* **JNA** loads `System` on macOS/Darwin and `dns_sd` elsewhere (Avahi Bonjour compatibility).
* `DNSServiceRegister` receives the TCP port in **network byte order**.
* TXT is RFC 6763 length-prefixed `key=value` bytes (`HapDnsTxt.encodeTxt`).
* A daemon thread runs `DNSServiceProcessResult` until `DNSServiceRefDeallocate` on `stop()` / re-register.
* Re-registration (discoverability, MAC, `c#`) deallocates and registers again — same pattern as the JmDNS advertiser.

## Tests

| Test | Purpose |
| --- | --- |
| `HapDnsTxtTest` | Required TXT keys + wire encoding |
| `DnssdHomekitAdvertiserTest` | Mocked JNA register / re-register / stop / port endianness |
| `JmdnsHomekitAdvertiserTest` | Existing JmDNS behaviour (unchanged) |
| `HomekitAdvertisersTest` | Factory / support probe |
| `DnssdLiveAdvertiserIT` | Real register+stop when native DNS-SD is present |

```bash
mvn test
```

## Troubleshooting

| Symptom | Likely cause |
| --- | --- |
| `Address already in use` / “no suitable mDNS” | Still using JmDNS against mDNSResponder — switch to `nativeDnssd()` |
| `UnsupportedOperationException: Native DNS-SD` | No `libSystem`/`libdns_sd` on this JVM/OS |
| `DNSServiceRegister failed with error code …` | Bad name/type, permissions, or daemon unavailable — check Console / `mDNSResponder` |
| Browse empty after register | Wrong interface, firewall, or advertiser stopped; confirm with `dns-sd -B _hap._tcp` |

## License

Same MIT license as HAP-Java. Native symbols are provided by the host OS; this library only calls them through JNA.
