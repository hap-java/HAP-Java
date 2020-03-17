package io.github.hapjava.server.impl.http.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHandler extends ChannelDuplexHandler {

  private static final Logger logger = LoggerFactory.getLogger(LoggingHandler.class);
  private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (logger.isTraceEnabled() && msg instanceof ByteBuf) {
      logBytes("READ", (ByteBuf) msg, ctx);
    }
    super.channelRead(ctx, msg);
  }

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
      throws Exception {
    if (logger.isTraceEnabled() && msg instanceof ByteBuf) {
      logBytes("WRITE", (ByteBuf) msg, ctx);
    }
    super.write(ctx, msg, promise);
  }

  private void logBytes(String type, ByteBuf buf, ChannelHandlerContext ctx) throws IOException {
    if (buf.readableBytes() > 0) {
      byte[] bytes = new byte[buf.readableBytes()];
      buf.getBytes(0, bytes, 0, bytes.length);
      logger.trace(
          String.format(
              "%s %s [%s]:%n%s%n",
              type, buf, ctx.channel().remoteAddress().toString(), bytesToHex(bytes)));
    }
  }

  /*
  This method is licensed under CC-BY-SA and published on Stackoverflow
  https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
  from the answer by Melquiades https://stackoverflow.com/users/2964945/melquiades
  */
  private String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }
}
