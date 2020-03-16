package com.beowulfe.hap.sample;

import java.util.concurrent.CompletableFuture;

import io.github.hapjava.accessories.LightbulbAccessory;
import io.github.hapjava.accessories.optionalcharacteristic.AccessoryWithHardwareRevision;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;

public class MockSwitch implements LightbulbAccessory, AccessoryWithHardwareRevision {
	
	private boolean powerState = false;
	private HomekitCharacteristicChangeCallback subscribeCallback = null;

	@Override
	public int getId() {
		return 2;
	}

	@Override
	public CompletableFuture<String> getName() {
		return CompletableFuture.completedFuture("Test Switch Name");
	}

	@Override
	public void identify() {
		System.out.println("Identifying light");
	}

	@Override
	public CompletableFuture<String> getSerialNumber() {
		return CompletableFuture.completedFuture("Test SwitchSN");
	}

	@Override
	public CompletableFuture<String> getModel() {
		return CompletableFuture.completedFuture("TestSwitch Model");
	}

	@Override
	public CompletableFuture<String> getManufacturer() {
		return CompletableFuture.completedFuture("Test SwitchManufacturer");
	}

	@Override
	public CompletableFuture<String> getFirmwareRevision() {
		return CompletableFuture.completedFuture("Test Switch Firmware");
	}

	@Override
	public CompletableFuture<Boolean> getLightbulbPowerState() {
		return CompletableFuture.completedFuture(powerState);
	}

	@Override
	public CompletableFuture<Void> setLightbulbPowerState(boolean powerState)
			throws Exception {
		this.powerState = powerState;
		if (subscribeCallback != null) {
			subscribeCallback.changed();
		}
		System.out.println("The lightbulb is now "+(powerState ? "on" : "off"));
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public void subscribeLightbulbPowerState(
			HomekitCharacteristicChangeCallback callback) {
		this.subscribeCallback = callback;
	}

	@Override
	public void unsubscribeLightbulbPowerState() {
		this.subscribeCallback = null;
	}

	@Override
	public CompletableFuture<String> getHardwareRevision() {
		return CompletableFuture.completedFuture("Test Switch Hardware");
	}
}
