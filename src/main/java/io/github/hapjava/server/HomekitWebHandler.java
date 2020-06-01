package io.github.hapjava.server;

import io.github.hapjava.server.impl.http.HomekitClientConnectionFactory;
import java.util.concurrent.CompletableFuture;

public interface HomekitWebHandler {

  CompletableFuture<Integer> start(HomekitClientConnectionFactory clientConnectionFactory);

  void stop();

  void resetConnections();
}
