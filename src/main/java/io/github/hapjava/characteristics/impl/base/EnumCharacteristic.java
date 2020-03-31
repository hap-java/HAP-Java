package io.github.hapjava.characteristics.impl.base;

import io.github.hapjava.characteristics.CharacteristicEnum;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * Characteristic that exposes an Enum value. Enums are represented as an Integer value in the
 * Homekit protocol, and classes extending this one must handle the static mapping to an Integer
 * value.
 *
 * @author Andy Lintner
 */
public abstract class EnumCharacteristic<T extends CharacteristicEnum>
    extends BaseCharacteristic<Integer> {

  private final int maxValue;
  Optional<Supplier<CompletableFuture<T>>> getter;
  protected Optional<ExceptionalConsumer<T>> setter;

  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param description a description of the characteristic to be passed to the consuming device.
   * @param maxValue the number of enum items.
   * @param getter getter to retrieve the value
   * @param setter setter to set value
   * @param subscriber subscriber to subscribe to changes
   * @param unsubscriber unsubscriber to unsubscribe from chnages
   */
  public EnumCharacteristic(
      String type,
      String description,
      int maxValue,
      Optional<Supplier<CompletableFuture<T>>> getter,
      Optional<ExceptionalConsumer<T>> setter,
      Optional<Consumer<HomekitCharacteristicChangeCallback>> subscriber,
      Optional<Runnable> unsubscriber) {
    super(
        type, "int", description, getter.isPresent(), setter.isPresent(), subscriber, unsubscriber);
    this.maxValue = maxValue;
    this.getter = getter;
    this.setter = setter;
  }

  /** {@inheritDoc} */
  @Override
  protected CompletableFuture<JsonObjectBuilder> makeBuilder(int iid) {
    return super.makeBuilder(iid)
        .thenApply(
            builder -> {
              return builder.add("minValue", 0).add("maxValue", maxValue).add("minStep", 1);
            });
  }

  /** {@inheritDoc} */
  @Override
  protected Integer convert(JsonValue jsonValue) {
    if (jsonValue instanceof JsonNumber) {
      return ((JsonNumber) jsonValue).intValue();
    } else if (jsonValue == JsonObject.TRUE) {
      return 1; // For at least one enum type (locks), homekit will send a true instead of 1
    } else if (jsonValue == JsonObject.FALSE) {
      return 0;
    } else {
      throw new IndexOutOfBoundsException("Cannot convert " + jsonValue.getClass() + " to int");
    }
  }

  @Override
  protected CompletableFuture<Integer> getValue() {
    if (!getter.isPresent()) {
      return null;
    }
    return getter.get().get().thenApply(T::getCode);
  }

  @Override
  protected void setValue(Integer value) throws Exception {
    if (!setter.isPresent()) {
      return;
    }

    // TODO implement setter here?
  }

  /** {@inheritDoc} */
  @Override
  protected Integer getDefault() {
    return 0;
  }
}
