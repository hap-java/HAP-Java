package io.github.hapjava.server.impl.pairing;

import io.github.hapjava.server.HomekitAuthInfo;
import io.github.hapjava.server.impl.HomekitRegistry;
import io.github.hapjava.server.impl.http.HttpRequest;
import io.github.hapjava.server.impl.http.HttpResponse;
import io.github.hapjava.server.impl.responses.NotFoundResponse;
import io.github.hapjava.server.impl.responses.UnauthorizedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PairSetupManager {

  private static final Logger logger = LoggerFactory.getLogger(PairSetupManager.class);

  private final HomekitAuthInfo authInfo;
  private final HomekitRegistry registry;

  private SrpHandler srpHandler;

  public PairSetupManager(HomekitAuthInfo authInfo, HomekitRegistry registry) {
    this.authInfo = authInfo;
    this.registry = registry;
  }

  public HttpResponse handle(HttpRequest httpRequest) throws Exception {
    PairSetupRequest req = PairSetupRequest.of(httpRequest.getBody());
    logger.trace("Handling pair-setup request {}", req);

    if (req.getState() == 1) {
      logger.trace("Received SRP Start Request " + registry.getLabel());
      srpHandler = new SrpHandler(authInfo.getPin(), authInfo.getSalt());
      return srpHandler.handle(req);
    } else if (req.getState() == 3) {
      logger.trace("Receive SRP Verify Request for " + registry.getLabel());
      if (srpHandler == null) {
        logger.warn("Received unexpected SRP Verify Request for " + registry.getLabel());
        return new UnauthorizedResponse();
      } else {
        try {
          return srpHandler.handle(req);
        } catch (Exception e) {
          srpHandler = null; // You don't get to try again - need a new key
          logger.warn("Exception encountered while processing SRP Verify Request", e);
          return new UnauthorizedResponse();
        }
      }
    } else if (req.getState() == 5) {
      logger.trace("Received Exchange Request for " + registry.getLabel());
      if (srpHandler == null) {
        logger.warn("Received unexpected Exchanged Request for " + registry.getLabel());
        return new UnauthorizedResponse();
      } else {
        ExchangeHandler handler = new ExchangeHandler(srpHandler.getK(), authInfo);
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
