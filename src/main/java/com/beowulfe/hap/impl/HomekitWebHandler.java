package com.beowulfe.hap.impl;

import java.util.concurrent.CompletableFuture;

import com.beowulfe.hap.impl.http.HomekitClientConnectionFactory;


public interface HomekitWebHandler {

	public CompletableFuture<Integer> start(HomekitClientConnectionFactory clientConnectionFactory);
	
	public void stop();

	public void resetConnections();
	
}
