package io.github.hapjava.accessories;

import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.common.IsConfiguredEnum;
import io.github.hapjava.characteristics.impl.inputsource.CurrentVisibilityStateEnum;
import io.github.hapjava.characteristics.impl.inputsource.InputSourceTypeEnum;
import io.github.hapjava.services.Service;
import io.github.hapjava.services.impl.InputSourceService;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/** Input Source accessory. */
public interface InputSourceAccessory extends HomekitAccessory {

  /**
   * Retrieves configured name of input source.
   *
   * @return configured name of input source
   */
  CompletableFuture<String> getConfiguredName();

  /**
   * Sets the configured name.
   *
   * @param name configured name
   * @return a future that completes when the change is made
   * @throws Exception when the change cannot be made
   */
  CompletableFuture<Void> setConfiguredName(String name) throws Exception;

  /**
   * Subscribes to changes in configured name.
   *
   * @param callback the function to call when the configured name changes.
   */
  void subscribeConfiguredName(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the configured name. */
  void unsubscribeConfiguredName();

  /**
   * Retrieves the flag whether input source is configured.
   *
   * @return a future that will contain the flag .
   */
  CompletableFuture<IsConfiguredEnum> isConfigured();
  /**
   * set the flag whether input source is configured.
   *
   * @param state is configured state
   * @return a future that completes when the change is made
   */
  CompletableFuture<Void> setIsConfigured(IsConfiguredEnum state);

  /**
   * Subscribes to changes in isConfigured.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeIsConfigured(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in isConfigured. */
  void unsubscribeIsConfigured();

  /**
   * Retrieves the input source type.
   *
   * @return a future that will contain the input source type.
   */
  CompletableFuture<InputSourceTypeEnum> getInputSourceType();

  /**
   * Subscribes to changes in input source type.
   *
   * @param callback the function to call when the type changes.
   */
  void subscribeInputSourceType(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the input source type. */
  void unsubscribeInputSourceType();

  /**
   * Retrieves the current visibility state.
   *
   * @return a future that will contain the current visibility state.
   */
  CompletableFuture<CurrentVisibilityStateEnum> getCurrentVisibilityState();

  /**
   * Subscribes to changes in current visibility state.
   *
   * @param callback the function to call when the state changes.
   */
  void subscribeCurrentVisibilityState(HomekitCharacteristicChangeCallback callback);

  /** Unsubscribes from changes in the current visibility state. */
  void unsubscribeCurrentVisibilityState();

  @Override
  default Collection<Service> getServices() {
    return Collections.singleton(new InputSourceService(this));
  }
}
