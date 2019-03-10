package io.github.hapjava.impl.json;

class HapJsonNoContentResponse extends HapJsonResponse {

  public HapJsonNoContentResponse() {
    super(new byte[0]);
  }

  @Override
  public int getStatusCode() {
    return 204;
  }
}
