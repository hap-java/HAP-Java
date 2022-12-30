package io.github.hapjava.characteristics.impl.base;

import io.github.hapjava.characteristics.ExceptionalConsumer;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * A characteristic that provides an string value.
 *
 * @author Eugen Freiter
 */
public class StringCharacteristic extends BaseCharacteristic<String> {
  private final Optional<Supplier<CompletableFuture<String>>> getter;
  private final Optional<ExceptionalConsumer<String>> setter;
  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param description a description of the characteristic to be passed to the consuming device.
   * @param getter getter to retrieve the value
   * @param setter setter for value
   * @param subscriber subscriber to subscribe to changes
   * @param unsubscriber unsubscriber to unsubscribe from chnages
   */
  public StringCharacteristic(
      String type,
      String description,
      Optional<Supplier<CompletableFuture<String>>> getter,
      Optional<ExceptionalConsumer<String>> setter,
      Optional<Consumer<HomekitCharacteristicChangeCallback>> subscriber,
      Optional<Runnable> unsubscriber) {
    super(
        type,
        "string",
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
  protected CompletableFuture<JsonObjectBuilder> makeBuilder(int iid) {
    return super.makeBuilder(iid);
  }

  /** {@inheritDoc} */
  @Override
  public String convert(JsonValue jsonValue) {
    return ((JsonString) jsonValue).getString();
  }

  /** {@inheritDoc} */
  @Override
  public void setValue(String value) throws Exception {
    if (setter.isPresent()) setter.get().accept(value);
  }

  /** {@inheritDoc} */
  @Override
  public CompletableFuture<String> getValue() {
    return getter.map(stringGetter -> stringGetter.get()).orElse(null);
  }

  /** {@inheritDoc} */
  @Override
  public String getDefault() {
    return "Unknown";
  }
}
