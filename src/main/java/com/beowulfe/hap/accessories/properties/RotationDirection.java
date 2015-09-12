package com.beowulfe.hap.accessories.properties;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum RotationDirection {
	CLOCKWISE(0),
	COUNTER_CLOCKWISE(1)
	;
	
	private final static Map<Integer, RotationDirection> reverse;
	static {
		reverse = Arrays.stream(RotationDirection.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
	}
	
	public static RotationDirection fromCode(Integer code) {
		return reverse.get(code);
	}
	
	private final int code;
	
	private RotationDirection(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
