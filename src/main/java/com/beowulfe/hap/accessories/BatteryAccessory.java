package com.beowulfe.hap.accessories;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;

import java.util.concurrent.CompletableFuture;

/**
 * An accessory that runs on batteries. Accessories that run on batteries are able to report
 * battery level.
 *
 * @author Gaston Dombiak
 */
public interface BatteryAccessory {

    /**
     * Retrieves the battery level of the accessory.
     *
     * @return a future that will contain the accessory's battery state
     */
    CompletableFuture<Integer> getBatteryLevelState();

    /**
     * Subscribes to changes in the battery level.
     *
     * @param callback the function to call when battery level changes.
     */
    void subscribeBatteryLevelState(HomekitCharacteristicChangeCallback callback);

    /**
     * Unsubscribes from changes in the battery level.
     */
    void unsubscribeBatteryLevelState();
}
