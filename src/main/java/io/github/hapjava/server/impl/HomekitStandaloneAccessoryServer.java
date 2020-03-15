package io.github.hapjava.server.impl;

import io.github.hapjava.accessories.HomekitAccessory;
import io.github.hapjava.server.HomekitAuthInfo;
import io.github.hapjava.server.HomekitWebHandler;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * A server for exposing standalone Homekit accessory (as opposed to a Bridge accessory which
 * contains multiple accessories). Each standalone accessory will have its own pairing information,
 * port, and pin. Instantiate this class via {@link
 * HomekitServer#createStandaloneAccessory(HomekitAuthInfo, HomekitAccessory)}.
 *
 * @author Andy Lintner
 */
public class HomekitStandaloneAccessoryServer {

  private final HomekitRoot root;

  HomekitStandaloneAccessoryServer(
      HomekitAccessory accessory,
      HomekitWebHandler webHandler,
      InetAddress localhost,
      HomekitAuthInfo authInfo)
      throws UnknownHostException, IOException, ExecutionException, InterruptedException {
    root = new HomekitRoot(accessory.getName().get(), webHandler, localhost, authInfo);
    root.addAccessory(accessory);
  }

  /** Begins advertising and handling requests for this accessory. */
  public void start() {
    root.start();
  }
}
