HAP-Java-Sample
=========
A sample implementation of a simple accessory using [HAP-Java](https://github.com/beowulfe/HAP-Java).

This is for demonstration purposes only. The persistence implementation is very primitive and should not be used except
for convenience in testing purposes, so information between app launches will maintain pairing information with the iOS
devices. Persisted data is written to a file `auth-state.bin` in the current working directory.
