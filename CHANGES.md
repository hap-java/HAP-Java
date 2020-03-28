# HAP-Java 1.1.5

## Fixes

* `null` values are now allowed, since Homekit allows them.
* `SecuritySystemAlarmType` and `TargetSecuritySystemState` now match the Homekit documentation [#44](https://github.com/hap-java/HAP-Java/pull/44) [#45](https://github.com/hap-java/HAP-Java/pull/45)
* Null pointer exception no longer thrown if a characteristic is not implemented [#32](https://github.com/hap-java/HAP-Java/issues/32)
* Fixed incorrect support for BatteryLevel in battery-powered accessories. Appropriate characteristic is battery status. [#50](https://github.com/hap-java/HAP-Java/pull/50)
* Clear all connections in SessionManager during HomekitRoot.stop() [#54](https://github.com/hap-java/HAP-Java/issues/54)
* Fix issue in which communications would drop when encrypted frames didn't line up with network frames [#64](https://github.com/hap-java/HAP-Java/pull/64)
* Fix various spec violations and optimize communications to improve performance [#65](https://github.com/hap-java/HAP-Java/pull/65)
* Fix a pairing issue in which HAP-Java could listen on a different interface than that which it advertises [#67](https://github.com/hap-java/HAP-Java/pull/67)
* Allow window covering to be used without optional characteristics. The inclusion of `HoldPositionCharacteristic` did terrible things, and we're still not sure why. Addressed [#56](https://github.com/hap-java/HAP-Java/pull/56)

## New and improved

* Occupancy sensor support [#59](https://github.com/hap-java/HAP-Java/pull/59)
* Leak sensors and valve support added [#52](https://github.com/hap-java/HAP-Java/pull/52)
* Notifications are batched now, when possible [#66](https://github.com/hap-java/HAP-Java/pull/66)
* Support for Doors and Windows added 
