package io.github.hapjava.characteristics.impl.rtp;


import io.github.hapjava.characteristics.Base64TlvCharacteristic;
import io.github.hapjava.characteristics.impl.rtp.types.VideoAttributesTypes;
import io.github.hapjava.characteristics.impl.rtp.types.VideoTypes;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SupportedVideoStreamConfigurationCharacteristic extends Base64TlvCharacteristic {
  public static class SupportedVideoStreamConfiguration {
    private VideoTypes videoTypes;
    private List<VideoAttributesTypes> videoAttributesTypes;

    public SupportedVideoStreamConfiguration(
        VideoTypes videoTypes, List<VideoAttributesTypes> videoAttributesTypes) {
      this.videoTypes = videoTypes;
      this.videoAttributesTypes = videoAttributesTypes;
    }
  }

  /** Default constructor */
  public SupportedVideoStreamConfigurationCharacteristic(
      Supplier<CompletableFuture<String>> getter) {
    super(
        "00000114-0000-1000-8000-0026BB765291",
        "Supported Video Stream Configuration",
        true,
        false,
        Optional.empty(),
        Optional.empty());
    this.getter = getter;
  }

  Supplier<CompletableFuture<String>> getter;

  @Override
  protected void setValue(String value) throws Exception {}

  private static void writeBytes(ByteArrayOutputStream baos, byte[] bytes) {
    baos.write(bytes, 0, bytes.length);
  }

  @Override
  protected CompletableFuture<String> getValue() {
    return getter.get();
  }
}
