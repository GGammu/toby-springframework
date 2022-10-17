package io.younghwang.springframeworkbasic.pointcut;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import static org.assertj.core.api.Assertions.assertThat;

class TargetTest {
    private String methodSignature;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        methodSignature = String.valueOf(Target.class.getMethod("minus", int.class, int.class));
    }

    @Test
    void methodSignaturePointcut() throws NoSuchMethodException {
        // given
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(" + methodSignature + ")");

        // when
        // then
        // Target.minus()
        assertThat(pointcut.getClassFilter().matches(Target.class) &&
                pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null))
                .isTrue();

        // Target.plus()
        assertThat(pointcut.getClassFilter().matches(Target.class) &&
                pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null))
                .isFalse();

        // Target.method()
        assertThat(pointcut.getClassFilter().matches(Target.class) &&
                pointcut.getMethodMatcher().matches(Target.class.getMethod("method"), null))
                .isFalse();
    }

    @Test
    void pointcut() throws Exception{
        // given
        // when
        // then
        //public int io.younghwang.springframeworkbasic.pointcut.Target.minus(int,int) throws java.lang.RuntimeException
        targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
        targetClassPointcutMatches("execution(* hello(..))", true, true, false, false, false, false);
        targetClassPointcutMatches("execution(* hello())", true, false, false, false, false, false);
        targetClassPointcutMatches("execution(* hello(String))", false, true, false, false, false, false);
        targetClassPointcutMatches("execution(* meth*(..))", false, false, false, false, true, true);
        targetClassPointcutMatches("execution(* *(int, int))", false, false, true, true, false, false);
        targetClassPointcutMatches("execution(* *())", true, false, false, false, true, true);
        targetClassPointcutMatches("execution(* io.younghwang.springframeworkbasic.pointcut.Target.*(..))", true, true, true, true, true, false);
        targetClassPointcutMatches("execution(* io.younghwang.springframeworkbasic.pointcut.*.*(..))", true, true, true, true, true, true);
        targetClassPointcutMatches("execution(* io.younghwang.springframeworkbasic.pointcut..*.*(..))", true, true, true, true, true, true);
        targetClassPointcutMatches("execution(* io.younghwang.springframeworkbasic..*.*(..))", true, true, true, true, true, true);
        targetClassPointcutMatches("execution(* com..*.*(..))", false, false, false, false, false, false);
        targetClassPointcutMatches("execution(* *..Target.*(..))", true, true, true, true, true, false);
        targetClassPointcutMatches("execution(* *..Tar*.*(..))", true, true, true, true, true, false);
        targetClassPointcutMatches("execution(* *..*get.*(..))", true, true, true, true, true, false);
        targetClassPointcutMatches("execution(* *..B*.*(..))", false, false, false, false, false, true);
        targetClassPointcutMatches("execution(* *..TargetInterface.*(..))", true, true, true, true, false, false);
        targetClassPointcutMatches("execution(* *(..) throws Runtime*)", false, false, false, true, false, true);
        targetClassPointcutMatches("execution(int *(..))", false, false, true, true, false, false);
        targetClassPointcutMatches("execution(void *(..))", true, true, false, false, true, true);
    }

    private void targetClassPointcutMatches(String execution, boolean... expected) throws Exception {
        pointcutMatches(execution, expected[0], Target.class, "hello");
        pointcutMatches(execution, expected[1], Target.class, "hello", String.class);
        pointcutMatches(execution, expected[2], Target.class, "plus", int.class, int.class);
        pointcutMatches(execution, expected[3], Target.class, "minus", int.class, int.class);
        pointcutMatches(execution, expected[4], Target.class, "method");
        pointcutMatches(execution, expected[5], Bean.class, "method");
    }

    private void pointcutMatches(String expression, Boolean expected, Class<?> clazz, String methodName, Class<?>... args) throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        assertThat(pointcut.getClassFilter().matches(clazz) &&
                pointcut.getMethodMatcher().matches(clazz.getMethod(methodName, args), null))
                .isEqualTo(expected);
    }
}