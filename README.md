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

Current implementation is based on HAP specification Release R2 (published 2019-07-26)
fully supports 29 out of 48 HomeKit accessories defined there.

| HomeKit Accessory | Supported by Java-HAP |
|--------------------|--------------------|
|  Accessory Information            |     :white_check_mark: yes    |
|  Air Purifier                 |      :x:  no    |
|  Air Quality Sensor                |      :x:  no       |
|  Audio Stream Management              |      :x:  no     |
|  Battery Service                  |       :white_check_mark: yes    |
|  Camera RTP Stream Management                |      :x:  no     |
|  Carbon Dioxide Sensor             |       :white_check_mark: yes    |
|  Carbon Monoxide Sensor                |      :white_check_mark: yes    |
|  Contact Sensor                 |       :white_check_mark: yes    |
|  Data Stream Transport Management                 |      :x:  no      |
|  Door                 |      :white_check_mark: yes    |
|  Doorbell                 |      :white_check_mark: yes    |
|  Fan                 |      :white_check_mark: yes    |
|  Faucet              |      :x:  no    |
|  Filter Maintenance                  |      :x:  no     |
|  Garage Door Opener                  |     :white_check_mark: yes    |
|  HAP Protocol Information                 |      :x:  no     |
|  Heater Cooler                  |      :x:  no   |
|  Humidifier Dehumidifier                 |      :x:  no     |
|  Humidity Sensor                  |     :white_check_mark: yes    |
|  Irrigation System                |      :x:  no    |
|  Leak Sensor                |      :white_check_mark: yes    |
|  Light Bulb                 |       :white_check_mark: yes    |
|  Light Sensor                 |     :white_check_mark: yes    |
|  Lock Management                  |      :x:  no    |
|  Lock Mechanism                 |       :white_check_mark: yes    |
|  Microphone                  |      :white_check_mark: yes    |
|  Motion Sensor                  |      :white_check_mark: yes    |
|  Occupancy Sensor                 |      :white_check_mark: yes    |
|  Outlet                 |       :white_check_mark: yes    |
|  Security System                 |       :white_check_mark: yes    |
|  Service Label                  |      :x:  no     |
|  Siri                 |      :x:  no      |
|  Slat                 |      :white_check_mark: yes    |
|  Smoke Sensor                 |     :white_check_mark: yes    |
|  Speaker                |        :white_check_mark: yes    |
|  Stateless Programmable Switch                  |       :white_check_mark: yes    |
|  Switch                 |       :white_check_mark: yes    |
|  Target Control                 |      :x:  no     |
|  Target Control Management                 |      :x:  no    |
|  Temperature Sensor                  |       :white_check_mark: yes    |
|  Thermostat                 |     :white_check_mark: yes    |
|  Valve                 |      :white_check_mark: yes    |
|  Window                 |      :white_check_mark: yes    |
|  Window Covering                  |      :white_check_mark: yes    |