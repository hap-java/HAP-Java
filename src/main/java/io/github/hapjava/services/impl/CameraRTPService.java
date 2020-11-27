package io.github.hapjava.services.impl;

import io.github.hapjava.characteristics.Characteristic;
import io.github.hapjava.characteristics.impl.common.ActiveCharacteristic;
import io.github.hapjava.characteristics.impl.rtp.SelectedRTPStreamConfigurationCharacteristic;
import io.github.hapjava.characteristics.impl.rtp.SetupEndpointsCharacteristic;
import io.github.hapjava.characteristics.impl.rtp.StreamingStatusCharacteristic;
import io.github.hapjava.characteristics.impl.rtp.SupportedAudioStreamConfigurationCharacteristic;
import io.github.hapjava.characteristics.impl.rtp.SupportedRTPConfigurationCharacteristic;
import io.github.hapjava.characteristics.impl.rtp.SupportedVideoStreamConfigurationCharacteristic;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class CameraRTPService extends AbstractServiceImpl implements ResourceService {
  private List<Characteristic> characteristics;
  private BiFunction<Integer, Integer, CompletableFuture<byte[]>> imageSupplier;

  public CameraRTPService(
      SupportedVideoStreamConfigurationCharacteristic supportedVideo,
      SupportedAudioStreamConfigurationCharacteristic supportedAudio,
      SupportedRTPConfigurationCharacteristic supportedRTP,
      SelectedRTPStreamConfigurationCharacteristic selectedRTP,
      StreamingStatusCharacteristic streamingStatus,
      SetupEndpointsCharacteristic setupEndpoints,
      ActiveCharacteristic activeCharacteristic,
      BiFunction<Integer, Integer, CompletableFuture<byte[]>> imageSupplier) {
    super("00000110-0000-1000-8000-0026BB765291");

    characteristics =
        Arrays.asList(
            supportedVideo,
            supportedAudio,
            supportedRTP,
            selectedRTP,
            streamingStatus,
            setupEndpoints,
            activeCharacteristic);
    this.imageSupplier = imageSupplier;
  }

  @Override
  public String getResourceType() {
    return "image";
  }

  @Override
  public CompletableFuture<byte[]> handle(int width, int height) {
    return imageSupplier.apply(width, height);
  }

  @Override
  public List<Characteristic> getCharacteristics() {
    return Collections.unmodifiableList(characteristics);
  }
}
