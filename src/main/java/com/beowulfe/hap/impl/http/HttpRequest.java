package com.beowulfe.hap.impl.http;

public interface HttpRequest {

	public String getUri();
	public byte[] getBody();
	public HttpMethod getMethod();
	
}
