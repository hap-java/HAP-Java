package com.beowulfe.hap.impl.http.impl;

import io.netty.handler.codec.http.FullHttpRequest;

class FullRequestHttpRequestImpl extends DefaultHttpRequestImpl {
	
	private final FullHttpRequest nettyRequest;
	
	public FullRequestHttpRequestImpl(FullHttpRequest nettyRequest) {
		super(nettyRequest);
		this.nettyRequest = nettyRequest;
	}


	@Override
	public byte[] getBody() {
		byte[] ret = new byte[nettyRequest.content().readableBytes()];
		nettyRequest.content().readBytes(ret);
		return ret;
	}

}
