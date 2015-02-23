package com.beowulfe.hap.impl.http.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoggingHandler extends ChannelInboundHandlerAdapter {
	
	private final FileOutputStream fos;
	
	public LoggingHandler() throws IOException {
		File file = File.createTempFile("hap", "log");
		fos = new FileOutputStream(file);
		System.out.println("Logging to "+file.getCanonicalPath());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof ByteBuf) {
			ByteBuf buf = (ByteBuf) msg;
			buf.getBytes(0, fos, buf.readableBytes());
			fos.flush();
		}
		super.channelRead(ctx, msg);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		fos.close();
	}
}
