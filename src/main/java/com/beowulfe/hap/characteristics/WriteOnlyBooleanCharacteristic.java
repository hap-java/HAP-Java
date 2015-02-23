package com.beowulfe.hap.characteristics;

import java.util.concurrent.CompletableFuture;

/**
 * A characteristic that provides a boolean that can only be written to, not read.
 *
 * @author Andy Lintner
 */
public abstract class WriteOnlyBooleanCharacteristic extends BooleanCharacteristic {
	
	/**
	 * Default constructor
	 * 
	 * @param type a string containing a UUID that indicates the type of characteristic. Apple defines a set of these,
	 *  however implementors can create their own as well.
	 * @param description a description of the characteristic to be passed to the consuming device.
	 */
	public WriteOnlyBooleanCharacteristic(String type, String description) {
		super(	type,
				true,
				false,
				description
			);
	}
	
	@Override
	protected final CompletableFuture<Boolean> getValue() { return CompletableFuture.completedFuture(false); }

}
