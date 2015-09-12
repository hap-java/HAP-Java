package com.beowulfe.hap.impl.characteristics.outlet;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.Outlet;
import com.beowulfe.hap.characteristics.BooleanCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;

public class OutletInUseCharacteristic  extends BooleanCharacteristic implements EventableCharacteristic {

	private final Outlet outlet;
	
	public OutletInUseCharacteristic(Outlet outlet) {
		super("00000026-0000-1000-8000-0026BB765291", false, true, "The outlet is in use");
		this.outlet = outlet;
	}

	@Override
	protected void setValue(Boolean value) throws Exception {
		//Read Only
	}

	@Override
	protected CompletableFuture<Boolean> getValue() {
		return outlet.getOutletInUse();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		outlet.subscribeOutletInUse(callback);
	}

	@Override
	public void unsubscribe() {
		outlet.unsubscribeOutletInUse();
	}

	

}
