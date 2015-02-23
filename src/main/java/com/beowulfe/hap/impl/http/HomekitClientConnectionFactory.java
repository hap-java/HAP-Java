package com.beowulfe.hap.impl.http;

import java.util.function.Consumer;

public interface HomekitClientConnectionFactory {

	HomekitClientConnection createConnection(Consumer<HttpResponse> outOfBandMessageCallback);
	
}
