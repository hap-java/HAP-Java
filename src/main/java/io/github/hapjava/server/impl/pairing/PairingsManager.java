package io.github.hapjava.server.impl.pairing;

import io.github.hapjava.server.HomekitAuthInfo;
import io.github.hapjava.server.impl.http.HttpRequest;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.github.hapjava.server.impl.jmdns.JmdnsHomekitAdvertiser;
import io.github.hapjava.server.impl.pairing.TypeLengthValueUtils.DecodeResult;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PairingsManager {

  private final HomekitAuthInfo authInfo;
  private final JmdnsHomekitAdvertiser advertiser;

  private static final Logger LOGGER = LoggerFactory.getLogger(PairingsManager.class);

  public PairingsManager(HomekitAuthInfo authInfo, JmdnsHomekitAdvertiser advertiser) {
    this.authInfo = authInfo;
    this.advertiser = advertiser;
  }

  public HttpResponse handle(HttpRequest request) throws IOException {
    DecodeResult d = TypeLengthValueUtils.decode(request.getBody());

    int method = d.getByte(MessageType.METHOD);

    if (method == PairingMethod.ADD_PAIRING.getValue()) {
      byte[] username = d.getBytes(MessageType.USERNAME);
      byte[] ltpk = d.getBytes(MessageType.PUBLIC_KEY);
      byte permissions = d.getByte(MessageType.PERMISSIONS);
      authInfo.createUser(
          authInfo.getMac() + new String(username, StandardCharsets.UTF_8), ltpk, permissions == 1);
    } else if (method == PairingMethod.REMOVE_PAIRING.getValue()) {
      byte[] username = d.getBytes(MessageType.USERNAME);
      authInfo.removeUser(authInfo.getMac() + new String(username, StandardCharsets.UTF_8));
      if (!authInfo.hasUser()) {
        advertiser.setDiscoverable(true);
      }
    } else if (method == PairingMethod.LIST_PAIRINGS.getValue()) {
      TypeLengthValueUtils.Encoder e = TypeLengthValueUtils.getEncoder();

      Collection<String> usernames = authInfo.listUsers();
      boolean first = true;
      Iterator<String> iterator = usernames.iterator();
      String mac = authInfo.getMac();

      while (iterator.hasNext()) {
        String username = iterator.next();
        if (first) {
          e.add(MessageType.STATE, (byte) 2);
          first = false;
        } else {
          e.add(MessageType.SEPARATOR);
        }
        byte[] publicKey = authInfo.getUserPublicKey(username);
        boolean isAdmin = authInfo.userIsAdmin(username);
        if (username.startsWith(mac)) {
          username = username.substring(mac.length());
        }
        e.add(MessageType.USERNAME, username);
        e.add(MessageType.PUBLIC_KEY, publicKey);
        e.add(MessageType.PERMISSIONS, (short) (isAdmin ? 1 : 0));
      }

      return new PairingResponse(e.toByteArray());
    } else {
      throw new RuntimeException("Unrecognized method: " + method);
    }

    return new PairingResponse(2);
  }
}
