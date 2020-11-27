package io.github.hapjava.characteristics.impl.rtp.types;

import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils;

public class VideoCodecParamTypes {
  public static final short PROFILE_ID = 0x01;
  public static final short LEVEL = 0x02;
  public static final short PACKETIZATION_MODE = 0x03;
  public static final short CVO_ENABLED = 0x04;
  public static final short CVO_ID = 0x05;

  public VideoCodecParamTypes(short packetizationMode, short profile, short level) {
    this.packetizationMode = packetizationMode;
    this.profile = profile;
    this.level = level;
  }

  private final short packetizationMode;
  private final short profile;
  private final short level;

  public short getPacketizationMode() {
    return packetizationMode;
  }

  public short getProfile() {
    return profile;
  }

  public short getLevel() {
    return level;
  }

  public byte[] encodeTLV() {
    TypeLengthValueUtils.Encoder videoCodecParamsTLV =
        TypeLengthValueUtils.getEncoder()
            .add(VideoCodecParamTypes.PACKETIZATION_MODE, packetizationMode)
            .add(VideoCodecParamTypes.PROFILE_ID, profile)
            .add(VideoCodecParamTypes.LEVEL, level);
    return videoCodecParamsTLV.toByteArray();
  }
}
