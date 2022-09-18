package io.younghwang.springframeworkbasic.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockSettings;
import org.mockito.exceptions.verification.TooFewActualInvocations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.AbstractList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
public class MockMethodTest {

    @Test
    void simpleMock() {
        // given
        MyList listMock = mock(MyList.class);

        // when
        when(listMock.add(anyString())).thenReturn(false);
        boolean added = listMock.add("a");

        // then
        verify(listMock).add(anyString());
        assertThat(added).isFalse();
    }


    @Test
    void makingWithMocksName() {
        // given
        MyList listMock = mock(MyList.class, "myMock");

        // when
        when(listMock.add(anyString())).thenReturn(false);
        listMock.add("a");

        // then
        assertThatThrownBy(() -> verify(listMock, times(2)).add(anyString()))
                .isInstanceOf(TooFewActualInvocations.class);

        assertThatThrownBy(() -> verify(listMock, times(2)).add(anyString()))
                .isInstanceOf(TooFewActualInvocations.class)
                .hasMessageContaining("myMock.add");
    }

    @Test
    void mockingWithAnswer() {
        // given
        MyList listMock = mock(MyList.class, new CustomAnswer());

        // when
        boolean added = listMock.add("a");

        // then
        verify(listMock).add(anyString());
        assertThat(added).isFalse();
    }

    @Test
    void mockingWithMockingSettings() {
        // given
        MockSettings customSettings = withSettings().defaultAnswer(new CustomAnswer());
        MyList listMock = mock(MyList.class, customSettings);

        // when
        boolean added = listMock.add("a");

        // then
        verify(listMock).add(anyString());
        assertThat(added).isFalse();
    }
}

class MyList extends AbstractList<String> {
    @Override
    public String get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 1;
    }
}

class CustomAnswer implements Answer<Boolean> {
    @Override
    public Boolean answer(InvocationOnMock invocation) throws Throwable {
        return false;
    }
}