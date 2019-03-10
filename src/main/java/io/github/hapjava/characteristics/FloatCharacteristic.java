package io.github.hapjava.characteristics;

import java.util.concurrent.CompletableFuture;
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

  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param isWritable indicates whether the value can be changed.
   * @param isReadable indicates whether the value can be retrieved.
   * @param description a description of the characteristic to be passed to the consuming device.
   * @param minValue the minimum supported value.
   * @param maxValue the maximum supported value
   * @param minStep the smallest supported step. Values will be rounded to a multiple of this.
   * @param unit a description of the unit this characteristic supports.
   */
  public FloatCharacteristic(
      String type,
      boolean isWritable,
      boolean isReadable,
      String description,
      double minValue,
      double maxValue,
      double minStep,
      String unit) {
    super(type, "float", isWritable, isReadable, description);
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.minStep = minStep;
    this.unit = unit;
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
    double rounder = 1 / this.minStep;
    return getDoubleValue()
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

  /** {@inheritDoc} */
  @Override
  protected Double getDefault() {
    return minValue;
  }

  /**
   * Supplies the value of this characteristic as a double.
   *
   * @return a future that will contain the value.
   */
  protected abstract CompletableFuture<Double> getDoubleValue();
}
