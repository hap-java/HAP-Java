package com.beowulfe.hap.impl.http;

import java.io.IOException;

public interface HomekitClientConnection {

	public HttpResponse handleRequest(HttpRequest request) throws IOException;

	byte[] decryptRequest(byte[] ciphertext);
	
	byte[] encryptResponse(byte[] plaintext) throws IOException;
	
	public void close();

	public void outOfBand(HttpResponse message);
	
}
