package io.ggammu.study.tobyspringframework.template;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
