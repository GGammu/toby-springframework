package io.ggammu.study.tobyspringframework.jdk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface InvokeHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException;
}
