package com.beowulfe.hap.accessories.properties;

import com.beowulfe.hap.accessories.SecuritySystem;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The target state of a {@link SecuritySystem}.
 *
 * @author Gaston Dombiak
 */
public enum TargetSecuritySystemState {

    /**
     * The home is occupied and residents are active.
     */
    STAY_ARM(0),
    /**
     * The home is unoccupied.
     */
    AWAY_ARM(1),
    /**
     * The home is occupied and residents are sleeping.
     */
    NIGHT_ARM(2),
    /**
     * The security system is disarmed.
     */
    DISARMED(3);

    private final static Map<Integer, TargetSecuritySystemState> reverse;
    static {
        reverse = Arrays.stream(TargetSecuritySystemState.values()).collect(Collectors
                .toMap(TargetSecuritySystemState::getCode, t -> t));
    }

    public static TargetSecuritySystemState fromCode(Integer code) {
        return reverse.get(code);
    }

    private final int code;

    TargetSecuritySystemState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
