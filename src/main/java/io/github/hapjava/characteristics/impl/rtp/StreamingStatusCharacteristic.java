package io.github.hapjava.characteristics.impl.rtp;

import io.github.hapjava.characteristics.Base64TlvCharacteristic;
import io.github.hapjava.characteristics.EventableCharacteristic;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StreamingStatusCharacteristic extends Base64TlvCharacteristic
    implements EventableCharacteristic {
  /** Default constructor */
  public StreamingStatusCharacteristic(
      Supplier<CompletableFuture<String>> streamingStatusGetter,
      Consumer<HomekitCharacteristicChangeCallback> subscriber,
      Runnable unsubscriber) {
    super(
        "00000120-0000-1000-8000-0026BB765291",
        "Streaming Status",
        true,
        false,
        Optional.of(subscriber),
        Optional.of(unsubscriber));
    this.getter = streamingStatusGetter;
  }

  Supplier<CompletableFuture<String>> getter;

  @Override
  protected void setValue(String value) throws Exception {}

  @Override
  protected CompletableFuture<String> getValue() {
    return getter.get();
  }
}
