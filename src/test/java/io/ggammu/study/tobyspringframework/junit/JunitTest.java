package io.ggammu.study.tobyspringframework.junit;

import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/junit.xml")
//@ContextConfiguration(locations = "/applicationContext.xml")
public class JunitTest {
//    static JunitTest testObject;
    @Autowired
    ApplicationContext context;

    static Set<JunitTest> testObjects = new HashSet<JunitTest>();
    static ApplicationContext contextObject = null;

//    @Autowired
//    Message message;

//    @Test
//    public void test5() {
//        assertThat(message).isInstanceOf(Message.class);
//        assertThat(message.getText()).isEqualTo("test");
//    }

    @Test
    public void test1() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    public void test2() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
        System.out.println("value test " + this.context);
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test
    public void test3() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
        System.out.println("value test " + this.context);
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }
}
