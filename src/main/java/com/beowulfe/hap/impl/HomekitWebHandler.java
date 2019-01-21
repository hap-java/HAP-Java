package com.beowulfe.hap.impl;

import com.beowulfe.hap.impl.http.HomekitClientConnectionFactory;
import java.util.concurrent.CompletableFuture;

public interface HomekitWebHandler {

  CompletableFuture<Integer> start(HomekitClientConnectionFactory clientConnectionFactory);

  void stop();

  void resetConnections();
}
