package io.github.hapjava.characteristics.impl.base;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.base.BaseCharacteristic;

/**
 * A characteristic that provides an immutable String value.
 *
 * @author Andy Lintner
 */
public class StaticStringCharacteristic extends BaseCharacteristic<String> {

  private static final int MAX_LEN = 255;

  private final Optional<Supplier<CompletableFuture<String>>> getter;
  /**
   * Default constructor
   *
   * @param type a string containing a UUID that indicates the type of characteristic. Apple defines
   *     a set of these, however implementors can create their own as well.
   * @param description a description of the characteristic to be passed to the consuming device.
   */
  public StaticStringCharacteristic(
      String type,
      String description,
      Optional<Supplier<CompletableFuture<String>>> getter,
      Optional<Consumer<HomekitCharacteristicChangeCallback>> subscriber,
      Optional<Runnable> unsubscriber) {
    super(type, "string", description, getter.isPresent(), false, subscriber, unsubscriber);
    this.getter = getter;
  }

  /** {@inheritDoc} */
  @Override
  protected CompletableFuture<JsonObjectBuilder> makeBuilder(int iid) {
    return super.makeBuilder(iid).thenApply(builder -> builder.add("maxLen", MAX_LEN));
  }

  /** {@inheritDoc} */
  @Override
  public String convert(JsonValue jsonValue) {
    return ((JsonString) jsonValue).getString();
  }

  /** {@inheritDoc} */
  @Override
  public void setValue(String value) throws Exception {
    throw new Exception("Cannot modify static strings");
  }

  /** {@inheritDoc} */
  @Override
  protected CompletableFuture<String> getValue() {
    return getter.map(stringGetter -> stringGetter.get()).get();
  }

  /** {@inheritDoc} */
  @Override
  protected String getDefault() {
    return "Unknown";
  }
}
