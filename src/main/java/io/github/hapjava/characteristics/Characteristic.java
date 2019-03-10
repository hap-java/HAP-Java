package io.github.hapjava.characteristics;

import java.util.concurrent.CompletableFuture;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * Interface for the characteristics provided by a Service.
 *
 * <p>Characteristics are the lowest level building block of the Homekit Accessory Protocol. They
 * define variables that can be retrieved or set by the remote client. Most consumers of this
 * library will be better served by using one of the characteristic classes in {@link
 * io.github.hapjava.characteristics} when creating custom accessory types (the standard accessories
 * from {@link io.github.hapjava.accessories} already include the necessary characteristics),
 * instead of trying to implement the JSON formats directly.
 *
 * @author Andy Lintner
 */
public interface Characteristic {

  /**
   * Adds an attribute to the passed JsonObjectBuilder named "value" with the current value of the
   * characteristic.
   *
   * @param characteristicBuilder the JsonObjectBuilder to add the value attribute to.
   */
  void supplyValue(JsonObjectBuilder characteristicBuilder);

  /**
   * Creates the JSON representation of the characteristic, in accordance with the Homekit Accessory
   * Protocol.
   *
   * @param iid The instance ID of the characteristic to be included in the serialization.
   * @return the future completing with the resulting JSON.
   */
  CompletableFuture<JsonObject> toJson(int iid);

  /**
   * Invoked by the remote client, this updates the current value of the characteristic.
   *
   * @param jsonValue the JSON serialized value to set.
   */
  void setValue(JsonValue jsonValue);
}
