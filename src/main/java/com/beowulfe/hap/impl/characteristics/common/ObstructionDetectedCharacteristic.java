package com.beowulfe.hap.impl.characteristics.common;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.characteristics.BooleanCharacteristic;
import com.beowulfe.hap.characteristics.EventableCharacteristic;

public class ObstructionDetectedCharacteristic extends BooleanCharacteristic implements EventableCharacteristic {

	private final Supplier<CompletableFuture<Boolean>> getter;
	private final Consumer<HomekitCharacteristicChangeCallback> subscriber;
	private final Runnable unsubscriber;
	
	public ObstructionDetectedCharacteristic(Supplier<CompletableFuture<Boolean>> getter, 
			Consumer<HomekitCharacteristicChangeCallback> subscriber, Runnable unsubscriber) {
		super("00000024-0000-1000-8000-0026BB765291", false, true, "An obstruction has been detected");
		this.getter = getter;
		this.subscriber = subscriber;
		this.unsubscriber = unsubscriber;
	}

	@Override
	protected void setValue(Boolean value) throws Exception {
		//Read Only
	}

	@Override
	protected CompletableFuture<Boolean> getValue() {
		return getter.get();
	}

	@Override
	public void subscribe(HomekitCharacteristicChangeCallback callback) {
		subscriber.accept(callback);
	}

	@Override
	public void unsubscribe() {
		unsubscriber.run();
	}

	

}
