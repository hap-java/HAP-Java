package io.github.hapjava.characteristics;

public interface ExceptionalConsumer<T> {
  void accept(T t) throws Exception;
}
