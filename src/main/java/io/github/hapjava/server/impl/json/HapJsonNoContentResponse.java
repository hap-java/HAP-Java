package io.github.hapjava.server.impl.json;

import io.github.hapjava.server.impl.responses.OkResponse;

class HapJsonNoContentResponse extends OkResponse {

  public HapJsonNoContentResponse() {
    super(new byte[0]);
  }

  @Override
  public int getStatusCode() {
    return 204;
  }
}
