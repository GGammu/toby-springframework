package io.ggammu.study.tobyspringframework.jdk.proxy;

import java.util.regex.Pattern;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {
    public void setMappedClassName(String mappedClassName) {
        this.setClassFilter(new SimpleClassFilter(mappedClassName));
    }

    static class SimpleClassFilter implements ClassFilter {
        String mappedName;

        private SimpleClassFilter(String mappedClassName) {
            this.mappedName = mappedClassName;
        }

        @Override
        public boolean matches(Class<?> aClass) {
            return PatternMatchUtils.simpleMatch(mappedName, aClass.getSimpleName());
        }
    }
}
