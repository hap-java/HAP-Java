package com.beowulfe.hap.accessories.properties;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.beowulfe.hap.accessories.WindowCovering;

/**
 * The position state used by a {@link WindowCovering}
 *
 * @author Andy Lintner
 */
public enum WindowCoveringPositionState {
	DECREASING(0),
	INCREASING(1),
	STOPPED(2)
	;
	
	private final static Map<Integer, WindowCoveringPositionState> reverse;
	static {
		reverse = Arrays.stream(WindowCoveringPositionState.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
	}
	
	public static WindowCoveringPositionState fromCode(Integer code) {
		return reverse.get(code);
	}
	
	private final int code;
	
	private WindowCoveringPositionState(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
