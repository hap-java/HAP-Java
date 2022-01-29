HAP-Java-Sample
=========
A sample implementation of a simple accessory using [HAP-Java](https://github.com/beowulfe/HAP-Java).

This is for demonstration purposes only. The persistence implementation is very primitive and should not be used except
for convenience in testing purposes, so information between app launches will maintain pairing information with the iOS
devices. Persisted data is written to a file `auth-state.bin` in the current working directory.

Setup Instructions
=========
When running this sample on MacOs, please add VM Option:

-Djava.net.preferIPv4Stack=true

Please use JDK 1.8 or else you will runinto some issue with reflection APIs being blocked by newer versions of JDK.
