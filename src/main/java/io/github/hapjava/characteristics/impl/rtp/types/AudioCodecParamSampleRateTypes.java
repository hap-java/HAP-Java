package io.github.hapjava.characteristics.impl.rtp.types;

import io.github.hapjava.server.impl.pairing.TLVObject;
import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils;

public class AudioCodecParamSampleRateTypes implements TLVObject {
  public static final short KHZ_8 = 0x00;
  public static final short KHZ_16 = 0x01;
  public static final short KHZ_24 = 0x02;
  private final short sampleRateType;

  public AudioCodecParamSampleRateTypes(short sampleRateType) {
    this.sampleRateType = sampleRateType;
  }

  @Override
  public byte[] encodeTLV() {
    TypeLengthValueUtils.Encoder encoder = TypeLengthValueUtils.getEncoder();
    encoder.add(AudioCodecParamTypes.SAMPLE_RATE, sampleRateType);
    return encoder.toByteArray();
  }
}
