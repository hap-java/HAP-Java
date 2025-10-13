package io.github.hapjava.characteristics;

public interface ExceptionalConsumer<T> {
  void accept(T t) throws Exception;

  default void accept(T t, String username) throws Exception {
    accept(t);
  }
}
