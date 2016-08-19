package com.beowulfe.hap.impl.services;

import com.beowulfe.hap.HomekitAccessory;
import com.beowulfe.hap.Service;
import com.beowulfe.hap.accessories.BatteryAccessory;
import com.beowulfe.hap.characteristics.Characteristic;
import com.beowulfe.hap.impl.characteristics.common.BatteryLevelCharacteristic;
import com.beowulfe.hap.impl.characteristics.common.Name;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractServiceImpl implements Service {
	
	private final String type;
	private final List<Characteristic> characteristics = new LinkedList<>();

	/**
	 * This constructor has been deprecated and replaced with
	 * {@link #AbstractServiceImpl(String, HomekitAccessory, String)}. Usages of
	 * this constructor will need to manually configure {@link Name} characteristic
	 * and {@link BatteryLevelCharacteristic} if needed.
	 *
	 * @param type unique UUID of the service. This information can be obtained from HomeKit Accessory Simulator.
	 */
	@Deprecated
	public AbstractServiceImpl(String type) {
		this(type, null, null);
	}

	/**
	 * <p>Creates a new instance of this class with the specified UUID and {@link HomekitAccessory}.
	 * Download and install <i>HomeKit Accessory Simulator</i> to discover the corresponding UUID for
	 * the specific service.</p>
	 *
	 * <p>The new service will automatically add {@link Name} characteristic. If the accessory
	 * is battery operated then it must implement {@link BatteryAccessory} and {@link BatteryLevelCharacteristic}
	 * will be added too.</p>
	 *
	 * @param type unique UUID of the service. This information can be obtained from HomeKit Accessory Simulator.
	 * @param accessory HomeKit accessory exposed as a service.
	 * @param serviceName name of the service. This information is usually the name of the accessory.
     */
	public AbstractServiceImpl(String type, HomekitAccessory accessory, String serviceName) {
		this.type = type;

		if (accessory != null) {
			// Add name characteristic
			addCharacteristic(new Name(serviceName));

			// If battery operated accessory then add BatteryLevelCharacteristic
			if (accessory instanceof BatteryAccessory) {
				BatteryAccessory batteryAccessory = (BatteryAccessory) accessory;
				addCharacteristic(new BatteryLevelCharacteristic(
						batteryAccessory::getBatteryLevelState,
						batteryAccessory::subscribeBatteryLevelState,
						batteryAccessory::unsubscribeBatteryLevelState
				));
			}
		}
	}

	@Override
	public List<Characteristic> getCharacteristics() {
		return Collections.unmodifiableList(characteristics);
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	protected void addCharacteristic(Characteristic characteristic) {
		this.characteristics.add(characteristic);
	}
}
