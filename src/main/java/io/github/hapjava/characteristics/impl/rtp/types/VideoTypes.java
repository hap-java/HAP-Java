package io.github.hapjava.characteristics.impl.rtp.types;

import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils;

public class VideoTypes {
  public static final short CODEC = 0x01;
  public static final short CODEC_PARAM = 0x02;
  public static final short ATTRIBUTES = 0x03;
  public static final short RTP_PARAM = 0x04;
  private short codec;
  private VideoCodecParamTypes codecParam;

  // this should actually be a list of profiles (tlv empty separated),
  // then a list of levels (tlv empty separated)
  public VideoTypes(short codec, VideoCodecParamTypes codecParam) {
    this.codec = codec;
    this.codecParam = codecParam;
  }

  public short getCodec() {
    return codec;
  }

  public VideoCodecParamTypes getCodecParam() {
    return codecParam;
  }

  public byte[] encodeTLV() {
    TypeLengthValueUtils.Encoder configurationTLV =
        TypeLengthValueUtils.getEncoder()
            .add(VideoTypes.CODEC, codec)
            .add(VideoTypes.CODEC_PARAM, codecParam.encodeTLV());

    return configurationTLV.toByteArray();
  }
}
