package io.github.hapjava.impl.responses;

import io.github.hapjava.impl.http.HttpResponse;

public class NotFoundResponse implements HttpResponse {

  @Override
  public int getStatusCode() {
    return 404;
  }
}
