package io.github.hapjava.characteristics.impl.base;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
  private final Optional<Supplier<CompletableFuture<Integer>>> getter;
  private final Optional<ExceptionalConsumer<Integer>> setter;

  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param description a description of the characteristic to be passed to the consuming device.
   * @param minValue the minimum supported value.
   * @param maxValue the maximum supported value
   * @param unit a description of the unit this characteristic supports.
   * @param getter getter for value
   * @param setter setter for value
   * @param subscriber subscribers to changes
   * @param unsubscriber unsubscribers to changes
   */
  public IntegerCharacteristic(
      String type,
      String description,
      int minValue,
      int maxValue,
      String unit,
      Optional<Supplier<CompletableFuture<Integer>>> getter,
      Optional<ExceptionalConsumer<Integer>> setter,
      Optional<Consumer<HomekitCharacteristicChangeCallback>> subscriber,
      Optional<Runnable> unsubscriber) {
    super(
        type, "int", description, getter.isPresent(), setter.isPresent(), subscriber, unsubscriber);
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.unit = unit;
    this.getter = getter;
    this.setter = setter;
  }

  /** {@inheritDoc} */
  @Override
  protected CompletableFuture<JsonObjectBuilder> makeBuilder(int iid) {
    return super.makeBuilder(iid)
        .thenApply(
            builder -> {
              builder.add("minValue", minValue).add("maxValue", maxValue).add("minStep", 1);
              if (this.unit != null) {
                builder.add("unit", unit);
              }
              return builder;
            });
  }

  @Override
  public CompletableFuture<Integer> getValue() {
    return getter.map(integerGetter -> integerGetter.get()).orElse(null);
  }

  @Override
  public void setValue(Integer value) throws Exception {
    if (setter.isPresent()) setter.get().accept(value);
  }

  /** {@inheritDoc} */
  @Override
  public Integer getDefault() {
    return minValue;
  }

  /** {@inheritDoc} */
  @Override
  protected Integer convert(JsonValue jsonValue) {
    return ((JsonNumber) jsonValue).intValue();
  }
}
