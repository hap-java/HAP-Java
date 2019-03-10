package io.github.hapjava.impl;

public interface ExceptionalConsumer<T> {
  void accept(T t) throws Exception;
}
