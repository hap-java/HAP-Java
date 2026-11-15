package io.github.hapjava.characteristics;

import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/** A characteristic that answers a write with a value when the client requests it. */
public interface WriteResponseCharacteristic extends Characteristic {

  /**
   * Invoked by the remote client with a write that requests a response ("r": true).
   *
   * @param jsonValue the JSON serialized value to set.
   * @param username the authenticated username making the request
   * @param response the JsonObjectBuilder to add the "value" attribute of the response to.
   * @throws Exception if the value cannot be set.
   */
  void setValueWithResponse(JsonValue jsonValue, String username, JsonObjectBuilder response)
      throws Exception;
}
