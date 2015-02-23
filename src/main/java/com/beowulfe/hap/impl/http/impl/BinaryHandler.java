package com.beowulfe.hap.impl.http.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beowulfe.hap.impl.http.HomekitClientConnection;

public class BinaryHandler extends ByteToMessageCodec<ByteBuf> {
	
	private final static Logger logger = LoggerFactory.getLogger(BinaryHandler.class);

	private final HomekitClientConnection connection;
	private boolean started = false;
	
	public BinaryHandler(HomekitClientConnection connection) {
		this.connection = connection;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out)
			throws Exception {
		if (started) {
			byte[] b = new byte[msg.readableBytes()];
			msg.readBytes(b);
			out.writeBytes(connection.encryptResponse(b));
		} else {
			out.writeBytes(msg);
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		byte[] b = new byte[in.readableBytes()];
		in.readBytes(b);
		byte[] decrypted = connection.decryptRequest(b);
		out.add(Unpooled.copiedBuffer(decrypted));
		started = true;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.error("Exception in binary handler", cause);
		super.exceptionCaught(ctx, cause);
	}

}
