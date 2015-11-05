package com.beowulfe.hap.impl.characteristics.windowcovering;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.VerticalTiltingWindowCovering;
import com.beowulfe.hap.characteristics.EventableCharacteristic;
import com.beowulfe.hap.characteristics.IntegerCharacteristic;

public class TargetVerticalTiltAngleCharacteristic extends IntegerCharacteristic implements EventableCharacteristic {

	private final VerticalTiltingWindowCovering windowCovering;
	
	public TargetVerticalTiltAngleCharacteristic(VerticalTiltingWindowCovering windowCovering) {
		super("0000007D-0000-1000-8000-0026BB765291", true, true, "The target vertical tilt angle", -90, 90, "Arc Degree");
		this.windowCovering = windowCovering;
	}

	@Override
	protected void setValue(Integer value) throws Exception {
		windowCovering.setTargetVerticalTiltAngle(value);
	}

	@Override
	protected CompletableFuture<Integer> getValue() {
		return windowCovering.getTargetVerticalTiltAngle();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		windowCovering.subscribeTargetVerticalTiltAngle(callback);
	}

	@Override
	public void unsubscribe() {
		windowCovering.unsubscribeTargetVerticalTiltAngle();
	}

}
