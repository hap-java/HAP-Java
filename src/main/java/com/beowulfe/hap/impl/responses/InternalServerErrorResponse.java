package com.beowulfe.hap.impl.responses;

import java.nio.ByteBuffer;

import com.beowulfe.hap.impl.http.HttpResponse;

public class InternalServerErrorResponse implements HttpResponse {

	private final Exception e;
	
	public InternalServerErrorResponse(Exception e) {
		this.e = e;
	}
	
	@Override
	public int getStatusCode() {
		return 500;
	}
	
	@Override
	public ByteBuffer getBody() {
		return ByteBuffer.wrap(e.getClass().getName().getBytes());
	}
	
	public Exception getException() {
		return e;
	}

}
