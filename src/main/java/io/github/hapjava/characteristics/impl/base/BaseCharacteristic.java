package io.github.hapjava.characteristics.impl.base;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for implementing {@link Characteristic}.
 *
 * @author Andy Lintner
 */
public abstract class BaseCharacteristic<T> implements Characteristic, EventableCharacteristic {

  private final Logger logger = LoggerFactory.getLogger(BaseCharacteristic.class);

  private final String type;
  private final String shortType;
  private final String format;
  private final String description;
  private final boolean isReadable;
  private final boolean isWritable;
  private final Optional<Consumer<HomekitCharacteristicChangeCallback>> subscriber;
  private final Optional<Runnable> unsubscriber;

  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param format a string indicating the value type, which must be a recognized type by the
   *     consuming device.
   * @param isWritable indicates whether the value can be changed.
   * @param isReadable indicates whether the value can be retrieved.
   * @param description a description of the characteristic to be passed to the consuming device.
   * @param subscriber subscribes to changes
   * @param unsubscriber unsubscribes to changes
   */
  public BaseCharacteristic(
      String type,
      String format,
      String description,
      boolean isReadable,
      boolean isWritable,
      Optional<Consumer<HomekitCharacteristicChangeCallback>> subscriber,
      Optional<Runnable> unsubscriber) {
    if (type == null || format == null || description == null) {
      throw new NullPointerException();
    }

    this.type = type;
    this.shortType = this.type.replaceAll("^0*([0-9a-fA-F]+)-0000-1000-8000-0026BB765291$", "$1");
    this.format = format;
    this.description = description;
    this.isReadable = isReadable;
    this.isWritable = isWritable;
    this.subscriber = subscriber;
    this.unsubscriber = unsubscriber;
  }

  @Override
  /** {@inheritDoc} */
  public final CompletableFuture<JsonObject> toJson(int iid) {
    return makeBuilder(iid).thenApply(builder -> builder.build());
  }

  /**
   * Creates the JSON serialized form of the accessory for use over the HomeKit Accessory Protocol.
   *
   * @param instanceId the static id of the accessory.
   * @return a future that will complete with the JSON builder for the object.
   */
  protected CompletableFuture<JsonObjectBuilder> makeBuilder(int instanceId) {
    CompletableFuture<T> futureValue = getValueListing();

    if (futureValue == null) {
      futureValue = CompletableFuture.completedFuture(getDefault());
    }

    return futureValue
        .exceptionally(
            t -> {
              logger.warn("Could not retrieve value " + this.getClass().getName(), t);
              return null;
            })
        .thenApply(
            value -> {
              JsonArrayBuilder perms = Json.createArrayBuilder();
              if (isReadable) {
                perms.add("pr");
              }
              if (isWritable) {
                perms.add("pw");
              }
              if (subscriber.isPresent()) {
                perms.add("ev");
              }
              JsonObjectBuilder builder =
                  Json.createObjectBuilder()
                      .add("iid", instanceId)
                      .add("type", shortType)
                      .add("perms", perms.build())
                      .add("format", format)
                      //                      .add("ev", false)
                      .add("description", description);
              if (isReadable) setJsonValue(builder, value);
              return builder;
            });
  }

  /** {@inheritDoc} */
  @Override
  public final void setValue(JsonValue jsonValue) {
    try {
      setValue(convert(jsonValue));
    } catch (Exception e) {
      logger.warn("Error while setting JSON value", e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void supplyValue(JsonObjectBuilder builder) {
    try {
      setJsonValue(builder, getValue().get());
    } catch (InterruptedException | ExecutionException e) {
      logger.warn("Error retrieving value", e);
      setJsonValue(builder, getDefault());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void subscribe(HomekitCharacteristicChangeCallback callback) {
    subscriber.get().accept(callback);
  }

  /** {@inheritDoc} */
  @Override
  public void unsubscribe() {
    unsubscriber.get().run();
  }

  /**
   * Converts from the JSON value to a Java object of the type T
   *
   * @param jsonValue the JSON value to convert from.
   * @return the converted Java object.
   */
  protected abstract T convert(JsonValue jsonValue);

  /**
   * Update the characteristic value using a new value supplied by the connected client.
   *
   * @param value the new value to set.
   * @throws Exception if the value cannot be set.
   */
  protected abstract void setValue(T value) throws Exception;

  /**
   * Retrieves the current value of the characteristic.
   *
   * @return a future that will complete with the current value.
   */
  protected abstract CompletableFuture<T> getValue();

  protected CompletableFuture<T> getValueListing() {
    return getValue();
  }

  /**
   * Supplies a default value for the characteristic to send to connected clients when the real
   * value. cannot be retrieved.
   *
   * @return a sensible default value.
   */
  protected abstract T getDefault();

  /**
   * Writes the value key to the serialized characteristic
   *
   * @param builder The JSON builder to add the value to
   * @param value The value to add
   */
  protected void setJsonValue(JsonObjectBuilder builder, T value) {
    // I don't like this - there should really be a way to construct a disconnected JSONValue...
    if (value instanceof Boolean) {
      builder.add("value", (Boolean) value);
    } else if (value instanceof Double) {
      builder.add("value", (Double) value);
    } else if (value instanceof Integer) {
      builder.add("value", (Integer) value);
    } else if (value instanceof Long) {
      builder.add("value", (Long) value);
    } else if (value instanceof BigInteger) {
      builder.add("value", (BigInteger) value);
    } else if (value instanceof BigDecimal) {
      builder.add("value", (BigDecimal) value);
    } else if (value == null) {
      builder.addNull("value");
    } else {
      builder.add("value", value.toString());
    }
  }
}
