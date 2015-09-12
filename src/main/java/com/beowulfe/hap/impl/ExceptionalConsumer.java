package com.beowulfe.hap.impl;

public interface ExceptionalConsumer<T> {
	void accept(T t) throws Exception;
}
