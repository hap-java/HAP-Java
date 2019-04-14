package io.github.hapjava.impl.http.impl;

import io.github.hapjava.impl.http.HomekitClientConnection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryHandler extends ByteToMessageCodec<ByteBuf> {

  private static final Logger logger = LoggerFactory.getLogger(BinaryHandler.class);

  private final HomekitClientConnection connection;
  private boolean started = false;

  public BinaryHandler(HomekitClientConnection connection) {
    this.connection = connection;
  }

  @Override
  protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
    if (started) {
      debugData("Sending data", msg, ctx);
      byte[] b = new byte[msg.readableBytes()];
      msg.readBytes(b);
      out.writeBytes(connection.encryptResponse(b));
    } else {
      out.writeBytes(msg);
    }
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    byte[] b = new byte[in.readableBytes()];
    in.readBytes(b);
    byte[] decrypted = connection.decryptRequest(b);
    ByteBuf outBuf = Unpooled.copiedBuffer(decrypted);
    debugData("Received data", outBuf, ctx);
    out.add(outBuf);
    started = true;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    boolean errorLevel = !(cause instanceof IOException);
    if (errorLevel) {
      logger.error("Exception in binary handler", cause);
    } else {
      logger.debug("Exception in binary handler", cause);
    }
    super.exceptionCaught(ctx, cause);
  }

  private void debugData(String msg, ByteBuf b, ChannelHandlerContext ctx) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug(
          String.format(
              "%s [%s]:%n%s",
              msg, ctx.channel().remoteAddress().toString(), b.toString(StandardCharsets.UTF_8)));
    }
  }
}
