package io.github.hapjava.characteristics.impl.base;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.json.JsonNumber;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;

/**
 * Characteristic that exposes a Boolean value.
 *
 * @author Andy Lintner
 */
public abstract class BooleanCharacteristic extends BaseCharacteristic<Boolean> {

  private final Optional<Supplier<CompletableFuture<Boolean>>> getter;
  private final Optional<ExceptionalConsumer<Boolean>> setter;

  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param description a description of the characteristic to be passed to the consuming device.
   */
  public BooleanCharacteristic(
      String type,
      String description,
      Optional<Supplier<CompletableFuture<Boolean>>> getter,
      Optional<ExceptionalConsumer<Boolean>> setter,
      Optional<Consumer<HomekitCharacteristicChangeCallback>> subscriber,
      Optional<Runnable> unsubscriber) {
    super(
        type,
        "bool",
        description,
        getter.isPresent(),
        setter.isPresent(),
        subscriber,
        unsubscriber);
    this.getter = getter;
    this.setter = setter;
  }

  /** {@inheritDoc} */
  @Override
  protected Boolean convert(JsonValue jsonValue) {
    if (jsonValue.getValueType().equals(ValueType.NUMBER)) {
      return ((JsonNumber) jsonValue).intValue() > 0;
    }
    return jsonValue.equals(JsonValue.TRUE);
  }

  @Override
  protected CompletableFuture<Boolean> getValue() {
    return getter.isPresent() ? getter.map(booleanGetter -> booleanGetter.get()).get() : null;
  }

  @Override
  protected void setValue(Boolean value) throws Exception {
    if (setter.isPresent()) setter.get().accept(value);
  }

  /** {@inheritDoc} */
  @Override
  protected Boolean getDefault() {
    return false;
  }
}
