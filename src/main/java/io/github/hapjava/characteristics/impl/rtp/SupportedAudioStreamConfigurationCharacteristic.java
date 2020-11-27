package io.github.hapjava.characteristics.impl.rtp;


import io.github.hapjava.characteristics.Base64TlvCharacteristic;
import io.github.hapjava.characteristics.impl.rtp.types.AudioCodecTypes;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SupportedAudioStreamConfigurationCharacteristic extends Base64TlvCharacteristic {
  public static class SupportedAudioStreamConfiguration {
    private final List<AudioCodecTypes> supportedCodecs;
    private final boolean comfortNoiseSupport;

    public SupportedAudioStreamConfiguration(
        List<AudioCodecTypes> supportedCodecs, boolean comfortNoiseSupport) {
      this.supportedCodecs = supportedCodecs;
      this.comfortNoiseSupport = comfortNoiseSupport;
    }
  }
  /** Default constructor */
  public SupportedAudioStreamConfigurationCharacteristic(
      Supplier<CompletableFuture<String>> getter) {
    super(
        "00000115-0000-1000-8000-0026BB765291",
        "Supported Audio Stream Configuration",
        true,
        false,
        Optional.empty(),
        Optional.empty());
    this.getter = getter;
  }

  private final Supplier<CompletableFuture<String>> getter;

  @Override
  protected void setValue(String value) throws Exception {}

  @Override
  protected CompletableFuture<String> getValue() {
    return getter.get();
  }
}
