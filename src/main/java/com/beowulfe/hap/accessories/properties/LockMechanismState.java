package com.beowulfe.hap.accessories.properties;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.beowulfe.hap.accessories.LockMechanism;

/**
 * The state of a {@link LockMechanism}.
 *
 * @author Andy Lintner
 */
public enum LockMechanismState {
	
	UNSECURED(0),
	SECURED(1),
	JAMMED(2),
	UNKNOWN(3)
	;
	
	private final static Map<Integer, LockMechanismState> reverse;
	static {
		reverse = Arrays.stream(LockMechanismState.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
	}
	
	public static LockMechanismState fromCode(Integer code) {
		return reverse.get(code);
	}
	
	private final int code;
	
	private LockMechanismState(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
