package com.beowulfe.hap.impl.connections;

import java.util.function.Consumer;

import com.beowulfe.hap.HomekitAuthInfo;
import com.beowulfe.hap.impl.HomekitRegistry;
import com.beowulfe.hap.impl.http.HomekitClientConnection;
import com.beowulfe.hap.impl.http.HomekitClientConnectionFactory;
import com.beowulfe.hap.impl.http.HttpResponse;

public class HomekitClientConnectionFactoryImpl implements HomekitClientConnectionFactory{

	private final HomekitAuthInfo authInfo;
	private final HomekitRegistry registry;
	private final SubscriptionManager subscriptions;
	
	public HomekitClientConnectionFactoryImpl(HomekitAuthInfo authInfo,
			HomekitRegistry registry, SubscriptionManager subscriptions) {
		this.registry = registry;
		this.authInfo = authInfo;
		this.subscriptions = subscriptions;
	}
	
	@Override
	public HomekitClientConnection createConnection(Consumer<HttpResponse> outOfBandMessageCallback) {
		return new ConnectionImpl(authInfo, registry, outOfBandMessageCallback, subscriptions);
	}

	
	
}
