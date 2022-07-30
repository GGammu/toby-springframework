package io.younghwang.springframeworkbasic.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration()
public class JUnitTest {
    static JUnitTest testObject;
    static Set<JUnitTest> testObjects = new HashSet<>();
    @Autowired
    static ApplicationContext context;
    static ApplicationContext contextObject = null;

    @Test
    public void junit_object_test1() {
        // given
        // when
        assertThat(this).isNotSameAs(testObject);
        // then
        testObject = this;
    }

    @Test
    public void junit_object_test2() {
        // given
        // when
        assertThat(this).isNotSameAs(testObject);
        // then
        testObject = this;
    }

    @Test
    public void junit_object_test3() {
        // given
        // when
        assertThat(this).isNotSameAs(testObject);
        // then
        testObject = this;
    }

    @Test
    public void junit_objects_test1() {
        // given
        // when
        assertThat(testObjects).doesNotContain(this);
        // then
        testObjects.add(this);
    }

    @Test
    public void junit_objects_test2() {
        // given
        // when
        assertThat(testObjects).doesNotContain(this);
        // then
        testObjects.add(this);
    }

    @Test
    public void junit_objects_test3() {
        // given
        // when
        assertThat(testObjects).doesNotContain(this);
        // then
        testObjects.add(this);
    }

    @Test
    void context_object_test1() {
        // given
        // when
        assertThat(contextObject == null || contextObject == context).isTrue();
        // then
        contextObject = context;
    }

    @Test
    void context_object_test2() {
        // given
        // when
        assertThat(contextObject ).isIn(null, context);
        // then
        contextObject = context;
    }

    @Test
    void context_object_test3() {
        // given
        // when
        assertThat(contextObject).satisfiesAnyOf(
                object -> assertThat(object).isEqualTo(null),
                object -> assertThat(object).isEqualTo(context)
        );
        // then
        contextObject = context;
    }
}
