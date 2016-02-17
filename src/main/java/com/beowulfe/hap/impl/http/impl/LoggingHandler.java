package com.beowulfe.hap.impl.http.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.HexDump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHandler extends ChannelDuplexHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(NettyHomekitHttpService.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (logger.isTraceEnabled() && msg instanceof ByteBuf) {
			logBytes("READ", (ByteBuf) msg, ctx);
		}
		super.channelRead(ctx, msg);
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		if (logger.isTraceEnabled() && msg instanceof ByteBuf) {
			logBytes("WRITE", (ByteBuf) msg, ctx);
		}
		super.write(ctx, msg, promise);
	}
	
	private void logBytes(String type, ByteBuf buf, ChannelHandlerContext ctx) throws IOException {
		if (buf.readableBytes() > 0) {
			try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
				byte[] bytes = new byte[buf.readableBytes()];
				buf.getBytes(0, bytes, 0, bytes.length);
				HexDump.dump(bytes, 0, stream, 0);
				stream.flush();
				logger.trace(String.format("%s %s [%s]:%n%s%n", type, buf, ctx.channel().remoteAddress().toString(),
						stream.toString(StandardCharsets.UTF_8.name())));
			}
		}
	}
}
