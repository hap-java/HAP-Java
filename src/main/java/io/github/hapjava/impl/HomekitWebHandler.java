package io.github.hapjava.impl;

import io.github.hapjava.impl.http.HomekitClientConnectionFactory;
import java.util.concurrent.CompletableFuture;

public interface HomekitWebHandler {

  CompletableFuture<Integer> start(HomekitClientConnectionFactory clientConnectionFactory);

  void stop();

  void resetConnections();
}
