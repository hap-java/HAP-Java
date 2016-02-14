package com.beowulfe.hap.impl.http.impl;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.impl.HomekitWebHandler;
import com.beowulfe.hap.impl.http.HomekitClientConnectionFactory;

public class HomekitHttpServer implements HomekitWebHandler {

	private NettyHomekitHttpService service = null;
	private final int port;
	private final int nThreads;

	@Override
	public void stop() {
		if (this.service != null) {
			this.service.shutdown();
		}
	}
	
	public HomekitHttpServer(int port, int nThreads) {
		this.port = port;
		this.nThreads = nThreads;
	}

	@Override
	public CompletableFuture<Integer> start(HomekitClientConnectionFactory clientConnectionFactory) {
		if (service == null) {
			this.service = NettyHomekitHttpService.create(port, nThreads);
			return this.service.create(clientConnectionFactory);
		} else {
			throw new RuntimeException("HomekitHttpServer can only be started once");
		}
	}

	@Override
	public void resetConnections() {
		service.resetConnections();
	}

}
