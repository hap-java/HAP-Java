package com.beowulfe.hap.accessories.properties;

import com.beowulfe.hap.accessories.SecuritySystem;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Type of alarm of a {@link SecuritySystem}.
 *
 * @author Gaston Dombiak
 */
public enum SecuritySystemAlarmType {

    /**
     * Alarm conditions are cleared
     */
    NO_ALARM(0),
    /**
     * Alarm type is not known
     */
    UNKNOWN(1);

    private final static Map<Integer, SecuritySystemAlarmType> reverse;
    static {
        reverse = Arrays.stream(SecuritySystemAlarmType.values()).collect(Collectors
                .toMap(SecuritySystemAlarmType::getCode, t -> t));
    }

    public static SecuritySystemAlarmType fromCode(Integer code) {
        return reverse.get(code);
    }

    private final int code;

    SecuritySystemAlarmType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
