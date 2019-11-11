package io.github.hapjava.impl.pairing;

import io.github.hapjava.HomekitAuthInfo;
import io.github.hapjava.impl.HomekitRegistry;
import io.github.hapjava.impl.http.HttpRequest;
import io.github.hapjava.impl.http.HttpResponse;
import io.github.hapjava.impl.responses.NotFoundResponse;
import io.github.hapjava.impl.responses.UnauthorizedResponse;
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
      logger.info("Starting pair for " + registry.getLabel());
      srpHandler = new SrpHandler(authInfo.getPin(), authInfo.getSalt());
      return srpHandler.handle(req);
    } else if (req.getStage() == Stage.TWO) {
      logger.debug("Entering second stage of pair for " + registry.getLabel());
      if (srpHandler == null) {
        logger.warn("Received unexpected stage 2 request for " + registry.getLabel());
        return new UnauthorizedResponse();
      } else {
        try {
          return srpHandler.handle(req);
        } catch (Exception e) {
          srpHandler = null; // You don't get to try again - need a new key
          logger.error("Exception encountered while processing pairing request", e);
          return new UnauthorizedResponse();
        }
      }
    } else if (req.getStage() == Stage.THREE) {
      logger.debug("Entering third stage of pair for " + registry.getLabel());
      if (srpHandler == null) {
        logger.warn("Received unexpected stage 3 request for " + registry.getLabel());
        return new UnauthorizedResponse();
      } else {
        FinalPairHandler handler = new FinalPairHandler(srpHandler.getK(), authInfo);
        try {
          return handler.handle(req);
        } catch (Exception e) {
          logger.error("Exception while finalizing pairing", e);
          return new UnauthorizedResponse();
        }
      }
    }

    return new NotFoundResponse();
  }
}
