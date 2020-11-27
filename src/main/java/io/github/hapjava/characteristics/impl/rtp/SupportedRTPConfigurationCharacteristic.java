package io.github.hapjava.characteristics.impl.rtp;

import io.github.hapjava.characteristics.Base64TlvCharacteristic;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SupportedRTPConfigurationCharacteristic extends Base64TlvCharacteristic {
  /** Default constructor */
  public SupportedRTPConfigurationCharacteristic(
      Supplier<CompletableFuture<String>> rtpCryptoSuite) {
    super(
        "00000116-0000-1000-8000-0026BB765291",
        "Supported RTP Configuration",
        true,
        false,
        Optional.empty(),
        Optional.empty());
    this.getter = rtpCryptoSuite;
  }

  private Supplier<CompletableFuture<String>> getter;

  @Override
  protected void setValue(String value) throws Exception {}

  @Override
  protected CompletableFuture<String> getValue() {
    return getter.get();
  }
}
