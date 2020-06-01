package io.github.hapjava.server.impl.pairing;

import io.github.hapjava.server.HomekitAuthInfo;
import io.github.hapjava.server.impl.HomekitRegistry;
import io.github.hapjava.server.impl.http.HttpRequest;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.github.hapjava.server.impl.responses.NotFoundResponse;
import io.github.hapjava.server.impl.responses.UnauthorizedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PairingManager {

  private static final Logger logger = LoggerFactory.getLogger(PairingManager.class);

  private final HomekitAuthInfo authInfo;
  private final HomekitRegistry registry;

  private SrpHandler srpHandler;

  public PairingManager(HomekitAuthInfo authInfo, HomekitRegistry registry) {
    this.authInfo = authInfo;
    this.registry = registry;
  }

  public HttpResponse handle(HttpRequest httpRequest) throws Exception {
    PairSetupRequest req = PairSetupRequest.of(httpRequest.getBody());

    if (req.getStage() == Stage.ONE) {
      logger.trace("Starting pair for " + registry.getLabel());
      srpHandler = new SrpHandler(authInfo.getPin(), authInfo.getSalt());
      return srpHandler.handle(req);
    } else if (req.getStage() == Stage.TWO) {
      logger.trace("Entering second stage of pair for " + registry.getLabel());
      if (srpHandler == null) {
        logger.warn("Received unexpected stage 2 request for " + registry.getLabel());
        return new UnauthorizedResponse();
      } else {
        try {
          return srpHandler.handle(req);
        } catch (Exception e) {
          srpHandler = null; // You don't get to try again - need a new key
          logger.warn("Exception encountered while processing pairing request", e);
          return new UnauthorizedResponse();
        }
      }
    } else if (req.getStage() == Stage.THREE) {
      logger.trace("Entering third stage of pair for " + registry.getLabel());
      if (srpHandler == null) {
        logger.warn("Received unexpected stage 3 request for " + registry.getLabel());
        return new UnauthorizedResponse();
      } else {
        FinalPairHandler handler = new FinalPairHandler(srpHandler.getK(), authInfo);
        try {
          return handler.handle(req);
        } catch (Exception e) {
          logger.warn("Exception while finalizing pairing", e);
          return new UnauthorizedResponse();
        }
      }
    }

    return new NotFoundResponse();
  }
}
