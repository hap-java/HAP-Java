package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.Door;
import io.github.hapjava.impl.characteristics.windowcovering.CurrentPositionCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.PositionStateCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.TargetPositionCharacteristic;

public class DoorService extends AbstractServiceImpl {

    public DoorService(Door door) {
        this(door, door.getLabel());
    }

    public DoorService(Door door, String serviceName) {
        super("00000081-0000-1000-8000-0026BB765291", door, serviceName);

        addCharacteristic(new CurrentPositionCharacteristic(door));
        addCharacteristic(new PositionStateCharacteristic(door));
        addCharacteristic(new TargetPositionCharacteristic(door));

        /*
         * if (door instanceof HorizontalTiltingWindowCovering) {
         * addCharacteristic(new CurrentHorizontalTiltAngleCharacteristic((HorizontalTiltingWindowCovering) door));
         * addCharacteristic(new TargetHorizontalTiltAngleCharacteristic((HorizontalTiltingWindowCovering) door));
         * }
         * if (door instanceof VerticalTiltingWindowCovering) {
         * addCharacteristic(new CurrentVerticalTiltAngleCharacteristic((VerticalTiltingWindowCovering) door));
         * addCharacteristic(new TargetVerticalTiltAngleCharacteristic((VerticalTiltingWindowCovering) door));
         * }
         * if (door instanceof HoldPositionWindowCovering) {
         * HoldPositionWindowCovering hpwc = (HoldPositionWindowCovering) door;
         * addCharacteristic(new HoldPositionCharacteristic(hpwc));
         * }
         * if (door instanceof ObstructionDetectedWindowCovering) {
         * ObstructionDetectedWindowCovering wc = (ObstructionDetectedWindowCovering) door;
         * addCharacteristic(new ObstructionDetectedCharacteristic(() -> wc.getObstructionDetected(),
         * c -> wc.subscribeObstructionDetected(c), () -> wc.unsubscribeObstructionDetected()));
         * }
         */
    }

}
