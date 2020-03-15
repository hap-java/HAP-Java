package io.github.hapjava.server.impl.http;

import java.util.function.Consumer;

public interface HomekitClientConnectionFactory {

  HomekitClientConnection createConnection(Consumer<HttpResponse> outOfBandMessageCallback);
}
