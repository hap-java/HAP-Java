package io.github.hapjava.characteristics.impl.rtp.types;

import io.github.hapjava.server.impl.pairing.TLVObject;
import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils;
import java.util.List;

public class AudioCodecParamTypes implements TLVObject {
  public static final short CHANNEL = 0x01;
  public static final short BIT_RATE = 0x02;
  public static final short SAMPLE_RATE = 0x03;
  public static final short PACKET_TIME = 0x04;

  private final short channels;
  private final short bitrateType;
  private final List<AudioCodecParamSampleRateTypes> sampleRates;

  public AudioCodecParamTypes(
      short channels, short bitrateType, List<AudioCodecParamSampleRateTypes> sampleRates) {
    this.channels = channels;
    this.bitrateType = bitrateType;
    this.sampleRates = sampleRates;
  }

  @Override
  public byte[] encodeTLV() {
    TypeLengthValueUtils.Encoder ret = TypeLengthValueUtils.getEncoder();
    ret.add(AudioCodecParamTypes.CHANNEL, channels);
    ret.add(AudioCodecParamTypes.BIT_RATE, bitrateType);
    ret.addAllEmptySeparated(AudioCodecParamTypes.SAMPLE_RATE, sampleRates);
    return ret.toByteArray();
  }
}
