package com.beowulfe.hap.impl.connections;

import com.beowulfe.hap.HomekitAuthInfo;
import com.beowulfe.hap.impl.HomekitRegistry;
import com.beowulfe.hap.impl.http.HomekitClientConnection;
import com.beowulfe.hap.impl.http.HomekitClientConnectionFactory;
import com.beowulfe.hap.impl.http.HttpResponse;
import com.beowulfe.hap.impl.jmdns.JmdnsHomekitAdvertiser;
import java.util.function.Consumer;

public class HomekitClientConnectionFactoryImpl implements HomekitClientConnectionFactory {

  private final HomekitAuthInfo authInfo;
  private final HomekitRegistry registry;
  private final SubscriptionManager subscriptions;
  private final JmdnsHomekitAdvertiser advertiser;

  public HomekitClientConnectionFactoryImpl(
      HomekitAuthInfo authInfo,
      HomekitRegistry registry,
      SubscriptionManager subscriptions,
      JmdnsHomekitAdvertiser advertiser) {
    this.registry = registry;
    this.authInfo = authInfo;
    this.subscriptions = subscriptions;
    this.advertiser = advertiser;
  }

  @Override
  public HomekitClientConnection createConnection(Consumer<HttpResponse> outOfBandMessageCallback) {
    return new ConnectionImpl(
        authInfo, registry, outOfBandMessageCallback, subscriptions, advertiser);
  }
}
