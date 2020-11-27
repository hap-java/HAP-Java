package io.github.hapjava.characteristics.impl.rtp.types;

import io.github.hapjava.server.impl.pairing.TLVObject;
import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils;

public class VideoAttributesTypes implements TLVObject {
  public static final short IMAGE_WIDTH = 0x01;
  public static final short IMAGE_HEIGHT = 0x02;
  public static final short FRAME_RATE = 0x03;

  public VideoAttributesTypes(int width, int height, int frameRate) {
    this.width = width;
    this.height = height;
    this.frameRate = frameRate;
  }

  private final int width;
  private final int height;
  private final int frameRate;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getFrameRate() {
    return frameRate;
  }

  @Override
  public byte[] encodeTLV() {
    TypeLengthValueUtils.Encoder videoAttrTLV =
        TypeLengthValueUtils.getEncoder()
            .addShort(VideoAttributesTypes.IMAGE_WIDTH, width)
            .addShort(VideoAttributesTypes.IMAGE_HEIGHT, height)
            .add(VideoAttributesTypes.FRAME_RATE, (short) frameRate);

    return videoAttrTLV.toByteArray();
  }
}
