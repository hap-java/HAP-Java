# HomeKit advertising backends (JmDNS & native DNS-SD)

## Motivation

HAP-Java must publish `_hap._tcp` so Home / controllers can find the accessory. Historically that was done with a **dedicated JmDNS** instance, which opens its own multicast UDP stack on port 5353.

That works on many hosts, but it is often **suboptimal**:

* The OS already runs an mDNS responder (**mDNSResponder** on macOS, **Avahi** on many Linux systems).
* A second, in-process multicast stack duplicates work, competes for the same LAN traffic, and is harder to reason about when the host (or another library) already owns discovery.
* Embedders such as **openHAB** already maintain a **shared** mDNS service for the whole runtime. HomeKit should reuse that policy rather than always spawning a private JmDNS.

This work therefore:

1. Introduces a pluggable **`HomekitAdvertiser`** SPI so any backend can publish HAP DNS-SD records.
2. Ships a **JmDNS** backend (default / classic) and an optional **native DNS-SD** backend (`DNSServiceRegister` via JNA) for standalone embeds that want to talk to the system daemon directly.
3. Leaves **platform policy** (which backend, shared vs dedicated) to the application — especially openHAB.

### What this is *not*

Hard “UDP 5353 is exclusively locked forever on macOS” is **not** the durable motivation. mDNSResponder typically listens with `SO_REUSEPORT`; a correct JmDNS can share the port. A temporary **JmDNS 3.6.4** regression in openHAB snapshots could fail to bind and looked like a permanent conflict; **3.6.5** and other releases behave normally. Prefer framing native / shared advertising as **optimization and clean host integration**, not as the only way to avoid an unfixable port clash.

## Architecture split

| Concern | Belongs in |
| --- | --- |
| HAP advertise lifecycle (`advertise` / `stop` / TXT updates for `_hap._tcp`) | **HAP-Java** (`HomekitAdvertiser`) |
| Default backends: JmDNS; optional Bonjour / dns_sd (Avahi compat) | **HAP-Java** (`HomekitAdvertisers`, implementations) |
| Shared mDNS for *all* bindings, JmDNS version, future native Bonjour for the runtime | **openHAB core** (`MDNSService` / equivalent) |
| Choosing shared OH mDNS vs dedicated JmDNS vs system DNS-SD for HomeKit | **openHAB `io.homekit`** (config / wiring) |

### Why the split

* **openHAB** already centralizes mDNS. If the runtime grows a native Bonjour/Avahi backend, every binding benefits — HomeKit should not be the only place that invents that.
* **HAP-Java** is also used **outside** openHAB. It still needs a way to advertise when no host mDNS service exists, plus a way for the host to **inject** whatever it does provide.
* Native DNS-SD **inside HAP** is therefore an optional convenience for standalone / non-OH embeds, not the place where openHAB solves fleet-wide mDNS on macOS.

```text
                    ┌─────────────────────────────────────┐
                    │           Application               │
                    │  (openHAB io.homekit, other embeds) │
                    │  picks / implements HomekitAdvertiser│
                    └─────────────────┬───────────────────┘
                                      │ injects
                                      ▼
┌──────────────────────┐    ┌─────────────────────────────┐
│ openHAB core mDNS    │    │ HAP-Java HomekitServer      │
│ (shared JmDNS today; │───▶│ uses HomekitAdvertiser SPI  │
│  native later?)      │    └─────────────────────────────┘
└──────────────────────┘
                                      ▲
                    HAP-Java backends │ optional defaults
          ┌───────────────────────────┴───────────────────────────┐
          │ JmdnsHomekitAdvertiser     DnssdHomekitAdvertiser     │
          │ (javax.jmdns)              (JNA → dns_sd / Bonjour)   │
          └───────────────────────────────────────────────────────┘
```

## Is `HomekitAdvertiser` enough for a non-JmDNS openHAB backend?

**Yes.** The SPI is intentionally backend-agnostic:

```text
advertise(label, category, mac, port, configurationIndex, setupId)
stop()
setDiscoverable(boolean)
setMac(String)
setConfigurationIndex(int)
```

It does **not** expose `JmDNS`, sockets, or DNS-SD handles. openHAB can:

1. Keep using `HomekitAdvertisers.jmdns(sharedJmDNS)` when core still speaks JmDNS.
2. Implement `HomekitAdvertiser` in `io.homekit` (or core) as a thin wrapper around a future native / Avahi / core API that is **not** JmDNS.
3. Optionally call HAP’s `nativeDnssd(...)` only for experiments or when core does not yet provide a native path — without making that the long-term OH design.

So HAP-Java’s contract is: **accept an interface for whatever mDNS backend you want**. Backends *shipped here* are JmDNS and native DNS-SD (Bonjour / Avahi `libdns_sd`). Backends *shipped by the host* can wrap the same or different mechanisms.

## Backends

| Backend | Class / factory | UDP 5353 in-process? | Role |
| --- | --- | --- | --- |
| JmDNS (shared instance) | `HomekitAdvertisers.jmdns(JmDNS)` | Reuses caller’s socket | Preferred when the host already has JmDNS (e.g. openHAB `useOHmDNS`) |
| JmDNS (dedicated) | `HomekitAdvertisers.jmdns(InetAddress)` or classic `HomekitServer` ctors | Yes — own multicast stack | Simple standalone default |
| Native DNS-SD | `HomekitAdvertisers.nativeDnssd()` / `nativeDnssd(InetAddress)` | No — registers via system daemon | Optional; macOS Bonjour / Avahi dns_sd compat |
| Host-provided | Application implements `HomekitAdvertiser` | Whatever the host does | openHAB (or others) wrap core mDNS |

TXT records (`sf`, `id`, `md`, `sh`, `c#`, `s#`, `ff`, `ci`, `pv`) are built once via `HapDnsTxt` so every backend publishes the same HAP payload.

**Policy belongs in the application.** Do not rely on HAP-Java to auto-pick (`auto(...)` is deprecated).

## Quick start

### Share an existing JmDNS (typical openHAB path)

```java
HomekitServer server =
    new HomekitServer(bind, 9124, HomekitAdvertisers.jmdns(sharedJmDNS));
```

### Dedicated JmDNS (standalone default)

```java
HomekitServer server = new HomekitServer(bind, 9124); // classic behaviour
// or: HomekitAdvertisers.jmdns(bind)
```

### Optional native DNS-SD (standalone / explicit)

Declare optional JNA if you use the native advertiser (JmDNS-only consumers can omit it):

```xml
<dependency>
  <groupId>net.java.dev.jna</groupId>
  <artifactId>jna</artifactId>
  <version>5.14.0</version>
</dependency>
```

```java
// Scope by bind interface; SRV host stays null (system hostname + A/AAAA).
HomekitServer server =
    new HomekitServer(bind, 9124, HomekitAdvertisers.nativeDnssd(bind));
```

```java
if (HomekitAdvertisers.isNativeDnssdSupported()) {
  // JNA + dns_sd loaded — not a guarantee that registration will succeed on the LAN
}
```

Existing constructors that take only `InetAddress` / `JmDNS` keep the previous JmDNS behaviour (backward compatible).

## openHAB wiring (target shape)

Suggested choices in `io.homekit` (names illustrative):

| User / config intent | Implementation |
| --- | --- |
| Use openHAB’s shared mDNS | `HomekitAdvertisers.jmdns(ohJmDNS)` or wrap core API as `HomekitAdvertiser` |
| Dedicated in-process JmDNS | `HomekitAdvertisers.jmdns(localAddress)` |
| System mDNS (Bonjour / Avahi) | Prefer **core-provided** advertiser when it exists; until then optionally `nativeDnssd(bind)` as a stopgap |

Fleet-wide native Bonjour for every binding remains an **openHAB core** enhancement, not a HomeKit-only fork of DNS-SD.

## Verifying on macOS (manual)

There is no automated live IT in CI (ubuntu runners lack Bonjour). After starting with a chosen advertiser:

```bash
dns-sd -B _hap._tcp local.
dns-sd -L "<instance name>" _hap._tcp local.
dns-sd -Z _hap._tcp local.
```

With the **native** backend, the Java process should not hold a UDP 5353 socket (`lsof -iUDP:5353` typically only shows `mDNSResponder`). With **shared JmDNS**, expect the host process to share 5353 via reuse — that is normal.

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
| `jmdns(JmDNS)` | Wrap an existing JmDNS (share host instance) |
| `jmdns(InetAddress)` | Create dedicated JmDNS on that address |
| `nativeDnssd()` | Native Bonjour on **all** interfaces (`interfaceIndex = 0`, default host) |
| `nativeDnssd(InetAddress)` | Native Bonjour on that address’s interface index; SRV host = system default (`null`) |
| `isNativeDnssdSupported()` | Whether JNA + dns_sd **load** (not a live register probe) |
| `auto(InetAddress)` | **Deprecated** — application must choose |

### `HomekitServer` constructors

```java
new HomekitServer(InetAddress localAddress, int port, HomekitAdvertiser advertiser)
new HomekitServer(InetAddress localAddress, int port, int nThreads, HomekitAdvertiser advertiser)
```

When an advertiser is supplied, `createBridge` / `createStandaloneAccessory` use it and do **not** open a private JmDNS socket.

## Native DNS-SD implementation notes

* **JNA** is **optional**. Loads `System` on macOS/Darwin and `dns_sd` elsewhere (Avahi Bonjour compatibility). Missing JNA → `LinkageError`; `isNativeDnssdSupported()` returns `false`.
* `DNSServiceRegister` expects the TCP port in **network byte order**.
* `nativeDnssd(InetAddress)` only sets **interface index**; **`host` stays `null`**. Never pass a raw IP as DNS-SD `host` — the daemon does not synthesize an A record, and Home hangs on “Setting up” after accepting the QR code.
* TXT is RFC 6763 length-prefixed `key=value` (`HapDnsTxt.encodeTxt`). Key insertion order is stable; RFC 6763 does not require a particular pair order.
* A daemon thread runs `DNSServiceProcessResult` until `DNSServiceRefDeallocate` on `stop()` / re-register.
* Re-registration (discoverability, MAC, `c#`) matches the JmDNS advertiser pattern.

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
| Extra multicast / busy 5353 / confusing dual stacks | Dedicated JmDNS while the OS or host already runs mDNS — share host JmDNS or use native / host advertiser |
| `Address already in use` on `JmDNS.create` | Often a JmDNS/OS edge case (e.g. historical 3.6.4); try sharing host mDNS, upgrading JmDNS, or native DNS-SD for standalone |
| `UnsupportedOperationException: Native DNS-SD` | No JNA on classpath, or no `libSystem` / `libdns_sd` |
| `DNSServiceRegister failed with error code …` | Bad name/type, permissions, or daemon unavailable |
| QR accepted, Home hangs on “Setting up” | SRV `host` set to a raw IP (no A record) — use `host = null` / `nativeDnssd(InetAddress)` as documented |
| Browse empty / too many interfaces | Prefer `nativeDnssd(localAddress)` to scope interface index |

## License

Same MIT license as HAP-Java. Native symbols are provided by the host OS; this library only calls them through JNA when the optional native backend is used.
