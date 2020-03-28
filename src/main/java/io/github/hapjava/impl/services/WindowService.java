package io.github.hapjava.impl.services;

import io.github.hapjava.accessories.Window;
import io.github.hapjava.impl.characteristics.windowcovering.CurrentPositionCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.PositionStateCharacteristic;
import io.github.hapjava.impl.characteristics.windowcovering.TargetPositionCharacteristic;

public class WindowService extends AbstractServiceImpl {

    public WindowService(Window window) {
        this(window, window.getLabel());
    }

    public WindowService(Window window, String serviceName) {
        super("0000008B-0000-1000-8000-0026BB765291", window, serviceName);

        addCharacteristic(new CurrentPositionCharacteristic(window));
        addCharacteristic(new PositionStateCharacteristic(window));
        addCharacteristic(new TargetPositionCharacteristic(window));

        /*
         * if (window instanceof HorizontalTiltingWindowCovering) {
         * addCharacteristic(new CurrentHorizontalTiltAngleCharacteristic((HorizontalTiltingWindowCovering) window));
         * addCharacteristic(new TargetHorizontalTiltAngleCharacteristic((HorizontalTiltingWindowCovering) window));
         * }
         * if (window instanceof VerticalTiltingWindowCovering) {
         * addCharacteristic(new CurrentVerticalTiltAngleCharacteristic((VerticalTiltingWindowCovering) window));
         * addCharacteristic(new TargetVerticalTiltAngleCharacteristic((VerticalTiltingWindowCovering) window));
         * }
         * if (window instanceof HoldPositionWindowCovering) {
         * HoldPositionWindowCovering hpwc = (HoldPositionWindowCovering) window;
         * addCharacteristic(new HoldPositionCharacteristic(hpwc));
         * }
         * if (window instanceof ObstructionDetectedWindowCovering) {
         * ObstructionDetectedWindowCovering wc = (ObstructionDetectedWindowCovering) window;
         * addCharacteristic(new ObstructionDetectedCharacteristic(() -> wc.getObstructionDetected(),
         * c -> wc.subscribeObstructionDetected(c), () -> wc.unsubscribeObstructionDetected()));
         * }
         */
    }
}
