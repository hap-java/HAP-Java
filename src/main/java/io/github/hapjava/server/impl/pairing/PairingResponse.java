package io.github.hapjava.server.impl.pairing;

import io.github.hapjava.server.impl.responses.OkResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class PairingResponse extends OkResponse {

  private static final Map<String, String> headers =
      Collections.unmodifiableMap(
          new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;

            {
              put("Content-type", "application/pairing+tlv8");
            }
          });

  public PairingResponse(byte[] body) {
    super(body);
  }

  public PairingResponse(int state) {
    this(encodeSuccess(state));
  }

  public PairingResponse(int state, ErrorCode errorCode) {
    this(encodeError(state, errorCode));
  }

  private static byte[] encodeSuccess(int state) {
    TypeLengthValueUtils.Encoder encoder = TypeLengthValueUtils.getEncoder();
    encoder.add(MessageType.STATE, (byte) state);
    return encoder.toByteArray();
  }

  private static byte[] encodeError(int state, ErrorCode errorCode) {
    TypeLengthValueUtils.Encoder encoder = TypeLengthValueUtils.getEncoder();
    encoder.add(MessageType.STATE, (byte) state);
    encoder.add(MessageType.ERROR, (byte) errorCode.getKey());
    return encoder.toByteArray();
  }

  @Override
  public Map<String, String> getHeaders() {
    return headers;
  }
}
