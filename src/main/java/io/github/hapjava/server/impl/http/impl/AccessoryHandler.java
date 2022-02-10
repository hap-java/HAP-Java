package io.github.hapjava.server.impl.http.impl;

import io.github.hapjava.server.impl.http.HomekitClientConnection;
import io.github.hapjava.server.impl.http.HomekitClientConnectionFactory;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AccessoryHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccessoryHandler.class);

  private HomekitClientConnection connection;
  private final HomekitClientConnectionFactory homekitClientConnectionFactory;

  public AccessoryHandler(HomekitClientConnectionFactory homekitClientConnectionFactory) {
    this.homekitClientConnectionFactory = homekitClientConnectionFactory;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    final Channel channel = ctx.pipeline().channel();
    this.connection =
        homekitClientConnectionFactory.createConnection(
            response -> {
              if (!channel.isActive()) {
                return;
              }
              channel.writeAndFlush(NettyResponseUtil.createResponse(response));
            });
    LOGGER.trace("New HomeKit connection from " + ctx.channel().remoteAddress().toString());
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    LOGGER.trace("Terminated HomeKit connection from " + ctx.channel().remoteAddress().toString());
    super.channelInactive(ctx);
  }

  @Override
  public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
    try {
      HttpResponse response = connection.handleRequest(new FullRequestHttpRequestImpl(req));
      if (response.doUpgrade()) {
        ChannelPipeline pipeline = ctx.channel().pipeline();
        pipeline.addBefore(
            ServerInitializer.HTTP_HANDLER_NAME, "binary", new BinaryHandler(connection));
      }
      sendResponse(response, ctx);
    } catch (Exception e) {
      LOGGER.warn("Error handling homekit http request", e);
      sendResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage(), ctx);
    }
  }

  private void sendResponse(
      HttpResponseStatus status, String responseBody, ChannelHandlerContext ctx) {
    if (responseBody == null) {
      responseBody = "";
    }
    FullHttpResponse response =
        new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1,
            status,
            Unpooled.copiedBuffer(responseBody.getBytes(StandardCharsets.UTF_8)));
    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
    response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
    response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
    ctx.write(response);
    ctx.flush();
  }

  private void sendResponse(HttpResponse homekitResponse, ChannelHandlerContext ctx) {
    FullHttpResponse response = NettyResponseUtil.createResponse(homekitResponse);
    ctx.write(response);
    ctx.flush();
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
    super.channelReadComplete(ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    boolean errorLevel = !(cause instanceof IOException);
    if (errorLevel) {
      LOGGER.warn("Exception caught in web handler", cause);
    } else {
      LOGGER.debug("Exception caught in web handler", cause);
    }
    ctx.close();
  }
}
