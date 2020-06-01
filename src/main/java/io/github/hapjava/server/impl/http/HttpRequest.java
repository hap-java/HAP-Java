package io.github.hapjava.server.impl.http;

public interface HttpRequest {

  String getUri();

  byte[] getBody();

  HttpMethod getMethod();
}
