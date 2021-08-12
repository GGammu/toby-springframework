package io.ggammu.study.tobyspringframework.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
//    private Hello target;
    Object target;

//    public UppercaseHandler(hello target) {
//        this.target = target;
//    }

    public UppercaseHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
//        String ret = (String)method.invoke(target, args);
//        return ret.toUpperCase();
        Object ret = method.invoke(target, args);
        if (ret instanceof String) {
            return ((String)ret).toUpperCase();
        } else {
            return ret;
        }
    }
}
