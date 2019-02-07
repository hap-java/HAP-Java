package com.beowulfe.hap.characteristics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
public abstract class BaseCharacteristic<T> implements Characteristic {

  private final Logger logger = LoggerFactory.getLogger(BaseCharacteristic.class);

  private final String type;
  private final String format;
  private final boolean isWritable;
  private final boolean isReadable;
  private final boolean isEventable;
  private final String description;

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
   */
  public BaseCharacteristic(
      String type, String format, boolean isWritable, boolean isReadable, String description) {
    if (type == null || format == null || description == null) {
      throw new NullPointerException();
    }

    this.type = type;
    this.format = format;
    this.isWritable = isWritable;
    this.isReadable = isReadable;
    this.isEventable = this instanceof EventableCharacteristic;
    this.description = description;
  }

  @Override
  /** {@inheritDoc} */
  public final CompletableFuture<JsonObject> toJson(int iid) {
    return makeBuilder(iid).thenApply(builder -> builder.build());
  }

  /**
   * Creates the JSON serialized form of the accessory for use over the Homekit Accessory Protocol.
   *
   * @param instanceId the static id of the accessory.
   * @return a future that will complete with the JSON builder for the object.
   */
  protected CompletableFuture<JsonObjectBuilder> makeBuilder(int instanceId) {
    CompletableFuture<T> futureValue = getValue();

    if (futureValue == null) {
      logger.error("Could not retrieve value " + this.getClass().getName());
      return null;
    }

    return futureValue
        .exceptionally(
            t -> {
              logger.error("Could not retrieve value " + this.getClass().getName(), t);
              return null;
            })
        .thenApply(
            value -> {
              JsonArrayBuilder perms = Json.createArrayBuilder();
              if (isWritable) {
                perms.add("pw");
              }
              if (isReadable) {
                perms.add("pr");
              }
              if (isEventable) {
                perms.add("ev");
              }
              JsonObjectBuilder builder =
                  Json.createObjectBuilder()
                      .add("iid", instanceId)
                      .add("type", type)
                      .add("perms", perms.build())
                      .add("format", format)
                      .add("ev", false)
                      .add("description", description);
              setJsonValue(builder, value);
              return builder;
            });
  }

  /** {@inheritDoc} */
  @Override
  public final void setValue(JsonValue jsonValue) {
    try {
      this.setValue(convert(jsonValue));
    } catch (Exception e) {
      logger.error("Error while setting JSON value", e);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void supplyValue(JsonObjectBuilder builder) {
    try {
      setJsonValue(builder, getValue().get());
    } catch (InterruptedException | ExecutionException e) {
      logger.error("Error retrieving value", e);
      setJsonValue(builder, getDefault());
    }
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
