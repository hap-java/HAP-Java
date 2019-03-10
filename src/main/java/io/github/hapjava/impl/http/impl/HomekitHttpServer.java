package io.github.hapjava.impl.http.impl;

import io.github.hapjava.impl.HomekitWebHandler;
import io.github.hapjava.impl.http.HomekitClientConnectionFactory;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

public class HomekitHttpServer implements HomekitWebHandler {

  private NettyHomekitHttpService service = null;
  private final InetAddress localAddress;
  private final int port;
  private final int nThreads;

  @Override
  public void stop() {
    if (this.service != null) {
      this.service.shutdown();
    }
  }

  public HomekitHttpServer(InetAddress localAddress, int port, int nThreads) {
    this.localAddress = localAddress;
    this.port = port;
    this.nThreads = nThreads;
  }

  @Override
  public CompletableFuture<Integer> start(HomekitClientConnectionFactory clientConnectionFactory) {
    if (service == null) {
      this.service = NettyHomekitHttpService.create(localAddress, port, nThreads);
      return this.service.create(clientConnectionFactory);
    } else {
      throw new RuntimeException("HomekitHttpServer can only be started once");
    }
  }

  @Override
  public void resetConnections() {
    service.resetConnections();
  }
}
