package com.beowulfe.hap.impl.responses;

import com.beowulfe.hap.impl.http.HttpResponse;

public class OkResponse implements HttpResponse {

	private final byte[] body;
	
	public OkResponse(byte[] body) {
		this.body = body;
	}
	
	@Override
	public byte[] getBody() {
		return body;
	}
	
	@Override
	public int getStatusCode() {
		return 200;
	}

}
