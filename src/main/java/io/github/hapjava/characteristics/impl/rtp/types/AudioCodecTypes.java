package io.github.hapjava.characteristics.impl.rtp.types;

import io.github.hapjava.server.impl.pairing.TLVObject;
import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils;
import java.util.List;

public class AudioCodecTypes implements TLVObject {
  public static final short PCMU = 0x00;
  public static final short PCMA = 0x01;
  public static final short AACELD = 0x02;
  public static final short OPUS = 0x03;
  private final short codecType;
  private final List<AudioCodecParamTypes> codecParamTypes;

  public AudioCodecTypes(short codecType, List<AudioCodecParamTypes> codecParamTypes) {
    this.codecType = codecType;
    this.codecParamTypes = codecParamTypes;
  }

  @Override
  public byte[] encodeTLV() {
    TypeLengthValueUtils.Encoder ret = TypeLengthValueUtils.getEncoder();
    ret.add(AudioTypes.CODEC, codecType);
    ret.addAllEmptySeparated(AudioTypes.CODEC_PARAM, codecParamTypes);
    return ret.toByteArray();
  }
}
