package io.github.hapjava.server.impl.http.impl;

import io.github.hapjava.server.impl.http.HttpMethod;
import io.github.hapjava.server.impl.http.HttpRequest;

class DefaultHttpRequestImpl implements HttpRequest {

  private final io.netty.handler.codec.http.HttpRequest request;

  public DefaultHttpRequestImpl(io.netty.handler.codec.http.HttpRequest request) {
    this.request = request;
  }

  @Override
  public String getUri() {
    return request.uri();
  }

  @Override
  public byte[] getBody() {
    return new byte[0];
  }

  @Override
  public HttpMethod getMethod() {
    io.netty.handler.codec.http.HttpMethod method = request.method();
    if (method.equals(io.netty.handler.codec.http.HttpMethod.GET)) {
      return HttpMethod.GET;
    } else if (method.equals(io.netty.handler.codec.http.HttpMethod.POST)) {
      return HttpMethod.POST;
    } else if (method.equals(io.netty.handler.codec.http.HttpMethod.PUT)) {
      return HttpMethod.PUT;
    } else {
      throw new RuntimeException("Unrecognized method: " + method.toString());
    }
  }
}
