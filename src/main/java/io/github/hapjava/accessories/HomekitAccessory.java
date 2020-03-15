package io.github.hapjava.accessories;

import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.AccessoryInformationService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * Base interface for all Homekit Accessories. You can implement this interface directly, but most
 * users will prefer to use the more full featured interfaces in {@link
 * io.github.hapjava.accessories} which include a default implementation of {@link #getServices()}.
 *
 * @author Andy Lintner
 */
public interface HomekitAccessory {

  /**
   * A unique identifier that must remain static across invocations to prevent errors with paired
   * iOS devices. When used as a child of a Bridge, this value must be greater than 1, as that ID is
   * reserved for the Bridge itself.
   *
   * @return the unique identifier.
   */
  int getId();

  /**
   * Returns a name to display in iOS.
   *
   * @return the label.
   */
  CompletableFuture<String> getName();

  /**
   * Performs an operation that can be used to identify the accessory. This action can be performed
   * without pairing.
   */
  void identify();

  /**
   * Returns a serial number to display in iOS.
   *
   * @return the serial number, or null.
   */
  CompletableFuture<String> getSerialNumber();

  /**
   * Returns a model name to display in iOS.
   *
   * @return the model name, or null.
   */
  CompletableFuture<String> getModel();

  /**
   * Returns a manufacturer to display in iOS.
   *
   * @return the manufacturer, or null.
   */
  CompletableFuture<String> getManufacturer();

  /**
   * Returns a firmware revision to display in iOS.
   *
   * @return the firmware revision, or null.
   */
  CompletableFuture<String> getFirmwareRevision();

  /**
   * The collection of Services this accessory supports. Services are the primary way to interact
   * with the accessory via Homekit. Besides the Services offered here, the accessory will
   * automatically include the required information service.
   *
   * <p>This method will only be useful if you're implementing your own accessory type. For the
   * standard accessories, use the default implementation provided by the interfaces in {@link
   * io.github.hapjava.accessories}.
   *
   * @return the collection of services.
   */
  default Collection<Service> getServices() {
    return Collections.singleton(new AccessoryInformationService(this));
  };
}
