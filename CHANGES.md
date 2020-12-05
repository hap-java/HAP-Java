# HAP-Java 2.0.0
* major refactoring to support optional characteristics
* structure and names adapted to HAP spec structure and naming.
* structure is following
    * `accessory` package include basis accessory as the listed in HAP spec, plus interfaces for optional characteristics. clients should extend the accessory classes. e.g. `WindowCoveringAccessory` or `AccessoryWithBrightness`
    * `characteristics` package consists of all characteristics, optional and mandatory. e.g. `TargetHorizontalTiltAngleCharacteristic`. The naming is done in accordance to HAP spec.
    * `services` package consists of services, which grouping characteristics. e.g. `WindowCoveringService` defines mandatory and optional characteristics for a window covering service as it is defined in HAP spec.
    * `server` package consists classes to run HomeKit server and handle communication
* the process is following: client, e.g. openHAB bindings, extends accessory classes, e.g. `WindowCoveringAccessory` and implements all required methods. WindowCoveringAccessory is linked already to WindowCoveringService, that in turn is link to single characteristics. 

## New and improved

* Valid values are supported for enum characteristics instead of min and max values
* Supported valid states for Thermostat, SecuritySystem, HeaterCooler and HumidifierDehumidifier [#108] [#119](https://github.com/hap-java/HAP-Java/pull/119)

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
