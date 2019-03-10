package io.github.hapjava.impl.http;

public interface HttpRequest {

  String getUri();

  byte[] getBody();

  HttpMethod getMethod();
}
