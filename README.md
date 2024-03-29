HAP-Java
=========
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.hap-java/hap/badge.svg)](https://search.maven.org/search?q=g:io.github.hap-java%20a:hap)
[![MIT License](https://img.shields.io/github/license/hap-java/HAP-Java)](https://github.com/hap-java/HAP-Java/blob/master/LICENSE)
![Maven Build Status](https://github.com/andylintner/HAP-Java/actions/workflows/maven.yml/badge.svg)

HAP-Java is a Java implementation of the HomeKit Accessory Protocol.

Using this library, you can create your own HomeKit Accessory or HomeKit Accessory Bridge.

This library would not have been possible without [Tian Zhang](https://github.com/KhaosT) who did a lot of the hard work of figuring out how the protocol works in his NodeJS implementation.

Usage
=========
Include HAP-Java in your project using maven:

```
<dependency>
	<groupId>io.github.hap-java</groupId>
	<artifactId>hap</artifactId>
	<version>2.0.1-SNAPSHOT</version>
</dependency>
```

After that, check out the [Sample](https://github.com/hap-java/HAP-Java/tree/sample) and
read the [Javadoc](https://hap-java.github.io/HAP-Java/apidocs/index.html)

Supported HomeKit Accessories
=========

Current implementation fully supports 42 HomeKit accessory/services.

| HomeKit Accessory & Service type | Supported by Java-HAP |
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
|  Faucet                           |     :white_check_mark:    |
|  Filter Maintenance               |     :white_check_mark:    |
|  Garage Door Opener               |     :white_check_mark:    |
|  HAP Protocol Information         |     :white_check_mark:    |
|  Heater Cooler                    |     :white_check_mark:    |
|  Humidifier Dehumidifier          |     :white_check_mark:    |
|  Humidity Sensor                  |     :white_check_mark:    |
|  Input Source                     |     :white_check_mark:    |
|  Irrigation System                |     :white_check_mark:    |
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
|  Service Label                    |     :white_check_mark:    |
|  Siri                             |     :x:                   |
|  Slat                             |     :white_check_mark:    |
|  Smart Speaker                    |     :white_check_mark:    |
|  Smoke Sensor                     |     :white_check_mark:    |
|  Speaker                          |     :white_check_mark:    |
|  Stateless Programmable Switch    |     :white_check_mark:    |
|  Switch                           |     :white_check_mark:    |
|  Target Control                   |     :x:                   |   
|  Target Control Management        |     :x:                   |
|  Television                       |     :white_check_mark:    |
|  Television Speaker               |     :white_check_mark:    |
|  Temperature Sensor               |     :white_check_mark:    |
|  Thermostat                       |     :white_check_mark:    |
|  Valve                            |     :white_check_mark:   |
|  Window                           |     :white_check_mark:   |
|  Window Covering                  |     :white_check_mark:   |