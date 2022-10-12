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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A characteristic that provides a Float value type.
 *
 * @author Andy Lintner
 */
public abstract class FloatCharacteristic extends BaseCharacteristic<Double> {

  private static final Logger LOGGER = LoggerFactory.getLogger(FloatCharacteristic.class);

  private final double minValue;
  private final double maxValue;
  private final double minStep;
  private final String unit;
  private final Optional<Supplier<CompletableFuture<Double>>> getter;
  private final Optional<ExceptionalConsumer<Double>> setter;

  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param description a description of the characteristic to be passed to the consuming device.
   * @param minValue the minimum supported value.
   * @param maxValue the maximum supported value
   * @param minStep the smallest supported step. Values will be rounded to a multiple of this.
   * @param unit a description of the unit this characteristic supports.
   * @param getter getter to retrieve the value
   * @param setter setter to set value
   * @param subscriber subscriber to subscribe to changes
   * @param unsubscriber unsubscriber to unsubscribe from chnages
   */
  public FloatCharacteristic(
      String type,
      String description,
      double minValue,
      double maxValue,
      double minStep,
      String unit,
      Optional<Supplier<CompletableFuture<Double>>> getter,
      Optional<ExceptionalConsumer<Double>> setter,
      Optional<Consumer<HomekitCharacteristicChangeCallback>> subscriber,
      Optional<Runnable> unsubscriber) {
    super(
        type,
        "float",
        description,
        getter.isPresent(),
        setter.isPresent(),
        subscriber,
        unsubscriber);
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.unit = unit;
    this.getter = getter;
    this.setter = setter;
    this.minStep = minStep;
  }

  /** {@inheritDoc} */
  @Override
  protected CompletableFuture<JsonObjectBuilder> makeBuilder(int iid) {
    return super.makeBuilder(iid)
        .thenApply(
            builder ->
                builder
                    .add("minValue", minValue)
                    .add("maxValue", maxValue)
                    .add("minStep", minStep)
                    .add("unit", unit));
  }

  /** {@inheritDoc} */
  @Override
  protected Double convert(JsonValue jsonValue) {
    return ((JsonNumber) jsonValue).doubleValue();
  }

  /**
   * {@inheritDoc}. Calls the getDoubleValue method and applies rounding to the minStep supplied in
   * the constructor.
   */
  @Override
  protected final CompletableFuture<Double> getValue() {
    if (!getter.isPresent()) {
      return null;
    }
    double rounder = 1 / this.minStep;
    return getter
        .get()
        .get()
        .thenApply(d -> d == null ? null : Math.round(d * rounder) / rounder)
        .thenApply(
            d -> {
              if (d != null) {
                if (d < minValue) {
                  LOGGER.warn(
                      "Detected value out of range "
                          + d
                          + ". Returning min value instead. Characteristic "
                          + this);
                  return minValue;
                }
                if (d > maxValue) {
                  LOGGER.warn(
                      "Detected value out of range "
                          + d
                          + ". Returning max value instead. Characteristic "
                          + this);
                  return maxValue;
                }
                return d;
              }
              return null;
            });
  }

  @Override
  protected void setValue(Double value) throws Exception {
    if (setter.isPresent()) setter.get().accept(value);
  }

  /** {@inheritDoc} */
  @Override
  public Double getDefault() {
    return minValue;
  }
}
