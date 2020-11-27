package io.github.hapjava.characteristics.impl.rtp;

import io.github.hapjava.characteristics.Base64TlvCharacteristic;
import io.github.hapjava.characteristics.ExceptionalConsumer;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SetupEndpointsCharacteristic extends Base64TlvCharacteristic {
  /** Default constructor */
  public SetupEndpointsCharacteristic(
      Supplier<CompletableFuture<String>> getter, ExceptionalConsumer<String> setter) {
    super(
        "00000118-0000-1000-8000-0026BB765291",
        "Setup Endpoints",
        true,
        true,
        Optional.empty(),
        Optional.empty());
    this.getter = getter;
    this.setter = setter;
  }

  Supplier<CompletableFuture<String>> getter;
  ExceptionalConsumer<String> setter;

  @Override
  protected void setValue(String value) throws Exception {
    setter.accept(value);
  }

  @Override
  protected CompletableFuture<String> getValue() {
    return getter.get();
  }
}
