package io.github.hapjava.server.impl.json;

class HapJsonMultiStatusResponse extends HapJsonResponse {

  public HapJsonMultiStatusResponse(byte[] body) {
    super(body);
  }

  @Override
  public int getStatusCode() {
    return 207;
  }
}
