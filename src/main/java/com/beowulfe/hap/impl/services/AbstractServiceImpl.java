package com.beowulfe.hap.impl.services;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.beowulfe.hap.Service;
import com.beowulfe.hap.characteristics.Characteristic;

abstract class AbstractServiceImpl implements Service {
	
	private final String type;
	private final List<Characteristic> characteristics = new LinkedList<>();
	
	public AbstractServiceImpl(String type) {
		this.type = type;
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
