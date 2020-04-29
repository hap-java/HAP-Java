HAP-Java
=========
HAP-Java is a Java implementation of the Homekit Accessory Protocol.

Using this library, you can create your own Homekit Accessory or Homekit Accessory Bridge.

This library would not have been possible without [Tian Zhang](https://github.com/KhaosT) who did a lot of the hard work of figuring out how the protocol works in his NodeJS implementation.

Usage
=========
Include HAP-Java in your project using maven:
```
<dependency>
	<groupId>io.github.hap-java</groupId>
	<artifactId>hap</artifactId>
	<version>2.0.0-SNAPSHOT</version>
</dependency>
```

After that, read the [Javadoc](http://beowulfe.github.io/HAP-Java/apidocs/) and check out the [Sample](https://github.com/beowulfe/HAP-Java/tree/sample).

Supported HomeKit Accessories
=========

Current implementation is based on HAP specification Release R2 (published 2019-07-26) and
fully supports 31 out of 48 HomeKit accessories defined there.

| HomeKit Accessory | Supported by Java-HAP |
|--------------------|--------------------|
|  Accessory Information            |     :white_check_mark:    |
|  Air Purifier                     |     :white_check_mark:    |
|  Air Quality Sensor               |     :white_check_mark:    |
|  Audio Stream Management          |     :x:                   |
|  Battery Service                  |     :white_check_mark:    |
|  Camera RTP Stream Management     |     :x:                   |
|  Carbon Dioxide Sensor            |     :white_check_mark:    |
|  Carbon Monoxide Sensor           |     :white_check_mark:    |
|  Contact Sensor                   |     :white_check_mark:    |
|  Data Stream Transport Management |     :x:                   |
|  Door                             |     :white_check_mark:    |
|  Doorbell                         |     :white_check_mark:    |
|  Fan                              |     :white_check_mark:    |
|  Faucet                           |     :x:                   |
|  Filter Maintenance               |     :x:                   |
|  Garage Door Opener               |     :white_check_mark:    |
|  HAP Protocol Information         |     :x:                   |
|  Heater Cooler                    |     :x:                   |
|  Humidifier Dehumidifier          |     :x:                   |
|  Humidity Sensor                  |     :white_check_mark:    |
|  Irrigation System                |     :x:                   |
|  Leak Sensor                      |     :white_check_mark:    |
|  Light Bulb                       |     :white_check_mark:    |
|  Light Sensor                     |     :white_check_mark:    |
|  Lock Management                  |     :x:                   |
|  Lock Mechanism                   |     :white_check_mark:    |
|  Microphone                       |     :white_check_mark:    |
|  Motion Sensor                    |     :white_check_mark:    |
|  Occupancy Sensor                 |     :white_check_mark:    |
|  Outlet                           |     :white_check_mark:    |
|  Security System                  |     :white_check_mark:    |
|  Service Label                    |     :x:                   |
|  Siri                             |     :x:                   |
|  Slat                             |     :white_check_mark:    |
|  Smoke Sensor                     |     :white_check_mark:    |
|  Speaker                          |     :white_check_mark:    |
|  Stateless Programmable Switch    |     :white_check_mark:    |
|  Switch                           |     :white_check_mark:    |
|  Target Control                   |     :x:                   |   
|  Target Control Management        |     :x:                   |
|  Temperature Sensor               |     :white_check_mark:    |
|  Thermostat                       |     :white_check_mark:    |
|  Valve                            |      :white_check_mark:   |
|  Window                           |      :white_check_mark:   |
|  Window Covering                  |      :white_check_mark:   |