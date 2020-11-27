package io.github.hapjava.characteristics;

import io.github.hapjava.characteristics.impl.base.BaseCharacteristic;
import java.util.Optional;
import java.util.function.Consumer;
import javax.json.JsonString;
import javax.json.JsonValue;

public abstract class Base64TlvCharacteristic extends BaseCharacteristic<String> {
  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param isWritable indicates whether the value can be changed.
   * @param isReadable indicates whether the value can be retrieved.
   * @param description a description of the characteristic to be passed to the consuming device.
   */
  public Base64TlvCharacteristic(
      String type,
      String description,
      boolean isReadable,
      boolean isWritable,
      Optional<Consumer<HomekitCharacteristicChangeCallback>> subscriber,
      Optional<Runnable> unsubscriber) {
    super(type, "tlv8", description, isReadable, isWritable, subscriber, unsubscriber);
  }

  @Override
  protected String convert(JsonValue jsonValue) {
    if (jsonValue.getValueType().equals(JsonValue.ValueType.STRING)) {
      return ((JsonString) jsonValue).getString();
    }
    return null;
  }

  @Override
  protected String getDefault() {
    return null;
  }

  //  @Override
  //  protected CompletableFuture<String> getValueListing() {
  //    return CompletableFuture.completedFuture(null);
  //  }
}
