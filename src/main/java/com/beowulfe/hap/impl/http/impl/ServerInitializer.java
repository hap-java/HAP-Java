package com.beowulfe.hap.impl.http.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import com.beowulfe.hap.impl.http.HomekitClientConnectionFactory;

class ServerInitializer extends ChannelInitializer<SocketChannel> {

	private static final int MAX_POST = 1000000;
	
	public static final String HTTP_HANDLER_NAME = "http";
	
	private final HomekitClientConnectionFactory homekit;
	private final ChannelGroup allChannels;
	private final EventExecutorGroup blockingExecutorGroup;
	
	public ServerInitializer(HomekitClientConnectionFactory homekit, ChannelGroup allChannels, int nThreads) {
		this.homekit = homekit;
		this.allChannels = allChannels;
		this.blockingExecutorGroup = new DefaultEventExecutorGroup(nThreads);
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LoggingHandler()); 
		pipeline.addLast(HTTP_HANDLER_NAME, new HttpResponseEncoderAggregate());
		pipeline.addLast(new HttpRequestDecoder());
		pipeline.addLast(new HttpObjectAggregator(MAX_POST));
		pipeline.addLast(blockingExecutorGroup, new AccessoryHandler(homekit));
		allChannels.add(ch);
	}

}
