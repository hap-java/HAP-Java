package io.github.hapjava.server.impl.http;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface HomekitClientConnection {

  HttpResponse handleRequest(HttpRequest request) throws IOException;

  byte[] decryptRequest(byte[] ciphertext);

  byte[] encryptResponse(byte[] plaintext) throws IOException;

  void close();

  void outOfBand(HttpResponse message);

  CompletableFuture<HttpResponse> handleRequestAsync(HttpRequest request);
}
