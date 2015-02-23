package com.beowulfe.hap.impl.responses;

import com.beowulfe.hap.impl.http.HttpResponse;

public class NotFoundResponse implements HttpResponse {

	@Override
	public int getStatusCode() {
		return 404;
	}

}
