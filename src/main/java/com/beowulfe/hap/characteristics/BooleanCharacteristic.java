package com.beowulfe.hap.characteristics;

import javax.json.JsonNumber;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

/**
 * Characteristic that exposes a Boolean value.
 *
 * @author Andy Lintner
 */
public abstract class BooleanCharacteristic extends BaseCharacteristic<Boolean> {

  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param isWritable indicates whether the value can be changed.
   * @param isReadable indicates whether the value can be retrieved.
   * @param description a description of the characteristic to be passed to the consuming device.
   */
  public BooleanCharacteristic(
      String type, boolean isWritable, boolean isReadable, String description) {
    super(type, "bool", isWritable, isReadable, description);
  }

  /** {@inheritDoc} */
  @Override
  protected Boolean convert(JsonValue jsonValue) {
    if (jsonValue.getValueType().equals(ValueType.NUMBER)) {
      return ((JsonNumber) jsonValue).intValue() > 0;
    }
    return jsonValue.equals(JsonValue.TRUE);
  }

  /** {@inheritDoc} */
  @Override
  protected Boolean getDefault() {
    return false;
  }
}
