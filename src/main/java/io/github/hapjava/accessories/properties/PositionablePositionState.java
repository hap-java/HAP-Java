package io.github.hapjava.accessories.properties;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.hapjava.accessories.Door;
import io.github.hapjava.accessories.WindowCovering;

/**
 * The position state used by a {@link WindowCovering}, {@link Door} or {@link Window}
 *
 * @author Andy Lintner
 */
public enum PositionablePositionState {
    DECREASING(0),
    INCREASING(1),
    STOPPED(2);

    private static final Map<Integer, PositionablePositionState> reverse;

    static {
        reverse = Arrays.stream(PositionablePositionState.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
    }

    public static PositionablePositionState fromCode(Integer code) {
        return reverse.get(code);
    }

    private final int code;

    private PositionablePositionState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
