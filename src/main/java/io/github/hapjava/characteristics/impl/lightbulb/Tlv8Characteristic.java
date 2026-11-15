package io.github.hapjava.characteristics.impl.lightbulb;

import io.github.hapjava.characteristics.WriteResponseCharacteristic;
import io.github.hapjava.characteristics.impl.base.BaseCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

/** Characteristic with a base64 encoded TLV8 value. A write is answered with a TLV8 value. */
class Tlv8Characteristic extends BaseCharacteristic<String> implements WriteResponseCharacteristic {

  private final Supplier<String> getter;
  private final Function<String, String> writer;

  /**
   * @param getter supplies the value
   * @param writer handles a write and returns the response, null for a read-only characteristic
   */
  Tlv8Characteristic(String type, Supplier<String> getter, Function<String, String> writer) {
    super(type, "tlv8", "", true, writer != null, Optional.empty(), Optional.empty());
    this.getter = getter;
    this.writer = writer;
  }

  @Override
  protected CompletableFuture<JsonObjectBuilder> makeBuilder(int iid) {
    if (writer == null) {
      return super.makeBuilder(iid);
    }
    // replaces the perms to add "wr", which BaseCharacteristic does not know
    JsonArrayBuilder perms = Json.createArrayBuilder().add("pr").add("pw").add("wr");
    return super.makeBuilder(iid).thenApply(builder -> builder.add("perms", perms));
  }

  @Override
  protected String convert(JsonValue jsonValue) {
    return ((JsonString) jsonValue).getString();
  }

  @Override
  public void setValue(String value) {
    if (writer != null) {
      writer.apply(value);
    }
  }

  @Override
  public void setValueWithResponse(
      JsonValue jsonValue, String username, JsonObjectBuilder response) {
    if (writer != null) {
      response.add("value", writer.apply(convert(jsonValue)));
    }
  }

  @Override
  public CompletableFuture<String> getValue() {
    return CompletableFuture.completedFuture(getter.get());
  }

  @Override
  public String getDefault() {
    return "";
  }
}
