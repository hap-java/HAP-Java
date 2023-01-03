package io.github.hapjava.server.impl.responses;

import io.github.hapjava.server.impl.http.HttpResponse;

public class BadRequestResponse implements HttpResponse {

  @Override
  public int getStatusCode() {
    return 400;
  }
}
