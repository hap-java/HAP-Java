package com.beowulfe.hap.characteristics;

import java.util.concurrent.CompletableFuture;

import javax.json.JsonNumber;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * A characteristic that provides an Integer data type.
 *
 * @author Andy Lintner
 */
public abstract class IntegerCharacteristic extends BaseCharacteristic<Integer> {

	private final int minValue;
	private final int maxValue;
	private final String unit;
	
	/**
	 * Default constructor
	 * 
	 * @param type a string containing a UUID that indicates the type of characteristic. Apple defines a set of these,
	 *  however implementors can create their own as well.
	 * @param isWritable indicates whether the value can be changed.
	 * @param isReadable indicates whether the value can be retrieved.
	 * @param description a description of the characteristic to be passed to the consuming device.
	 * @param minValue the minimum supported value.
	 * @param maxValue the maximum supported value
	 * @param unit a description of the unit this characteristic supports.
	 */
	public IntegerCharacteristic(String type, boolean isWritable, boolean isReadable, String description,
			int minValue, int maxValue, String unit) {
		super(type, "int", isWritable, isReadable, description);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.unit = unit;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CompletableFuture<JsonObjectBuilder> makeBuilder(int iid) {
		return super.makeBuilder(iid).thenApply(builder -> {
			return builder
					.add("minValue", minValue)
					.add("maxValue", maxValue)
					.add("minStep", 1)
					.add("unit", unit);
		});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getDefault() {
		return minValue;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer convert(JsonValue jsonValue) {
		return ((JsonNumber) jsonValue).intValue();
	}

}
