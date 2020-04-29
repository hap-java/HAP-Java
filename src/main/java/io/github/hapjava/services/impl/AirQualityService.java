package io.github.hapjava.services.impl;

import io.github.hapjava.accessories.AirQualityAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithName;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithNitrogenDioxideDensity;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithOzoneDensity;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithPM10Density;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithPM25Density;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusActive;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusFault;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusLowBattery;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithStatusTampered;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithSulphurDioxideDensity;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithVOCDensity;
import io.github.hapjava.characteristics.impl.airquality.AirQualityCharacteristic;
import io.github.hapjava.characteristics.impl.airquality.NitrogenDioxideDensityCharacteristic;
import io.github.hapjava.characteristics.impl.airquality.OzoneDensityCharacteristic;
import io.github.hapjava.characteristics.impl.airquality.PM10DensityCharacteristic;
import io.github.hapjava.characteristics.impl.airquality.PM25DensityCharacteristic;
import io.github.hapjava.characteristics.impl.airquality.SulphurDioxideDensityCharacteristic;
import io.github.hapjava.characteristics.impl.airquality.VOCDensityCharacteristic;
import io.github.hapjava.characteristics.impl.battery.StatusLowBatteryCharacteristic;
import io.github.hapjava.characteristics.impl.common.NameCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusActiveCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusFaultCharacteristic;
import io.github.hapjava.characteristics.impl.common.StatusTamperedCharacteristic;

/** This characteristic describes the subject assessment of air quality by an accessory. */
public class AirQualityService extends AbstractServiceImpl {

  public AirQualityService(AirQualityCharacteristic airQuality) {
    super("00000096-0000-1000-8000-0026BB765291");
    addCharacteristic(airQuality);
  }

  public AirQualityService(AirQualityAccessory accessory) {
    this(
        new AirQualityCharacteristic(
            accessory::getAirQuality,
            accessory::subscribeAirQuality,
            accessory::unsubscribeAirQuality));
    if (accessory instanceof AccessoryWithName) {
      addOptionalCharacteristic(new NameCharacteristic(((AccessoryWithName) accessory)::getName));
    }

    if (accessory instanceof AccessoryWithOzoneDensity) {
      addOptionalCharacteristic(
          new OzoneDensityCharacteristic(
              ((AccessoryWithOzoneDensity) accessory)::getOzoneDensity,
              ((AccessoryWithOzoneDensity) accessory)::subscribeOzoneDensity,
              ((AccessoryWithOzoneDensity) accessory)::unsubscribeOzoneDensity));
    }
    if (accessory instanceof AccessoryWithNitrogenDioxideDensity) {
      addOptionalCharacteristic(
          new NitrogenDioxideDensityCharacteristic(
              ((AccessoryWithNitrogenDioxideDensity) accessory)::getNitrogenDioxideDensity,
              ((AccessoryWithNitrogenDioxideDensity) accessory)::subscribeNitrogenDioxideDensity,
              ((AccessoryWithNitrogenDioxideDensity) accessory)
                  ::unsubscribeNitrogenDioxideDensity));
    }

    if (accessory instanceof AccessoryWithSulphurDioxideDensity) {
      addOptionalCharacteristic(
          new SulphurDioxideDensityCharacteristic(
              ((AccessoryWithSulphurDioxideDensity) accessory)::getSulphurDioxideDensity,
              ((AccessoryWithSulphurDioxideDensity) accessory)::subscribeSulphurDioxideDensity,
              ((AccessoryWithSulphurDioxideDensity) accessory)::unsubscribeSulphurDioxideDensity));
    }
    if (accessory instanceof AccessoryWithPM25Density) {
      addOptionalCharacteristic(
          new PM25DensityCharacteristic(
              ((AccessoryWithPM25Density) accessory)::getPM25Density,
              ((AccessoryWithPM25Density) accessory)::subscribePM25Density,
              ((AccessoryWithPM25Density) accessory)::unsubscribePM25Density));
    }
    if (accessory instanceof AccessoryWithPM10Density) {
      addOptionalCharacteristic(
          new PM10DensityCharacteristic(
              ((AccessoryWithPM10Density) accessory)::getPM10Density,
              ((AccessoryWithPM10Density) accessory)::subscribePM10Density,
              ((AccessoryWithPM10Density) accessory)::unsubscribePM10Density));
    }
    if (accessory instanceof AccessoryWithVOCDensity) {
      addOptionalCharacteristic(
          new VOCDensityCharacteristic(
              ((AccessoryWithVOCDensity) accessory)::getVOCDensity,
              ((AccessoryWithVOCDensity) accessory)::subscribeVOCDensity,
              ((AccessoryWithVOCDensity) accessory)::unsubscribeVOCDensity));
    }
    if (accessory instanceof AccessoryWithStatusActive) {
      addOptionalCharacteristic(
          new StatusActiveCharacteristic(
              ((AccessoryWithStatusActive) accessory)::getStatusActive,
              ((AccessoryWithStatusActive) accessory)::subscribeStatusActive,
              ((AccessoryWithStatusActive) accessory)::unsubscribeStatusActive));
    }
    if (accessory instanceof AccessoryWithStatusFault) {
      addOptionalCharacteristic(
          new StatusFaultCharacteristic(
              ((AccessoryWithStatusFault) accessory)::getStatusFault,
              ((AccessoryWithStatusFault) accessory)::subscribeStatusFault,
              ((AccessoryWithStatusFault) accessory)::unsubscribeStatusFault));
    }
    if (accessory instanceof AccessoryWithStatusTampered) {
      addOptionalCharacteristic(
          new StatusTamperedCharacteristic(
              ((AccessoryWithStatusTampered) accessory)::getStatusTampered,
              ((AccessoryWithStatusTampered) accessory)::subscribeStatusTampered,
              ((AccessoryWithStatusTampered) accessory)::unsubscribeStatusTampered));
    }
    if (accessory instanceof AccessoryWithStatusLowBattery) {
      addOptionalCharacteristic(
          new StatusLowBatteryCharacteristic(
              ((AccessoryWithStatusLowBattery) accessory)::getStatusLowBattery,
              ((AccessoryWithStatusLowBattery) accessory)::subscribeStatusLowBattery,
              ((AccessoryWithStatusLowBattery) accessory)::unsubscribeStatusLowBattery));
    }
  }

  public void addOptionalCharacteristic(NameCharacteristic name) {
    addCharacteristic(name);
  }

  public void addOptionalCharacteristic(OzoneDensityCharacteristic ozoneDensity) {
    addCharacteristic(ozoneDensity);
  }

  public void addOptionalCharacteristic(NitrogenDioxideDensityCharacteristic nitrogenDensity) {
    addCharacteristic(nitrogenDensity);
  }

  public void addOptionalCharacteristic(SulphurDioxideDensityCharacteristic sulphuhrDensity) {
    addCharacteristic(sulphuhrDensity);
  }

  public void addOptionalCharacteristic(PM25DensityCharacteristic pm25Density) {
    addCharacteristic(pm25Density);
  }

  public void addOptionalCharacteristic(PM10DensityCharacteristic pm10Density) {
    addCharacteristic(pm10Density);
  }

  public void addOptionalCharacteristic(VOCDensityCharacteristic vocDensity) {
    addCharacteristic(vocDensity);
  }

  public void addOptionalCharacteristic(StatusActiveCharacteristic statusActive) {
    addCharacteristic(statusActive);
  }

  public void addOptionalCharacteristic(StatusFaultCharacteristic statusFault) {
    addCharacteristic(statusFault);
  }

  public void addOptionalCharacteristic(StatusTamperedCharacteristic statusTampered) {
    addCharacteristic(statusTampered);
  }

  public void addOptionalCharacteristic(StatusLowBatteryCharacteristic statusLowBattery) {
    addCharacteristic(statusLowBattery);
  }
}
