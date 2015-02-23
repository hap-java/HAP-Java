package com.beowulfe.hap.impl.http;

import java.util.Collections;
import java.util.Map;

public interface HttpResponse {

	int getStatusCode();
	
	default byte[] getBody() {
		return new byte[0];
	}
	
	default HttpVersion getVersion() {
		return HttpVersion.HTTP_1_1;
	}
	
	default Map<String, String> getHeaders() {
		return Collections.emptyMap();
	}
	
	default boolean doUpgrade() { return false; }
	
	public enum HttpVersion {
		HTTP_1_1,
		EVENT_1_0
	}
}
