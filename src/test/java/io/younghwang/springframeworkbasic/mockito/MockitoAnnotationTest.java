package io.younghwang.springframeworkbasic.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
 class MockitoAnnotationTest {
    // 단순 Mock 객체 stub 되지 않음
    @Test
    void whenNotUseMockAnnotation_thenCorrect() {
        List mockList = Mockito.mock(ArrayList.class);
        mockList.add("one");
        Mockito.verify(mockList).add("one");
        assertThat(mockList.size()).isEqualTo(0);

        Mockito.when(mockList.size()).thenReturn(100);
        assertThat(mockList.size()).isEqualTo(100);
    }

    @Mock
    List<String> mockedList;

    @Test
    void whenUseMockAnnotation_thenCorrect() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertThat(mockedList.size()).isEqualTo(0);

        Mockito.when(mockedList.size()).thenReturn(100);
        assertThat(mockedList.size()).isEqualTo(100);
    }

    // 실제 인스턴스와 동일한 기능을 하는 Mock 객체
    @Test
     void whenNotUseSpyAnnotation_thenCorrect() {
        List<String> spyList = Mockito.spy(new ArrayList<String>());

        spyList.add("one");
        spyList.add("two");

        Mockito.verify(spyList).add("one");
        Mockito.verify(spyList).add("two");

        assertThat(spyList.size()).isEqualTo(2);

        Mockito.doReturn(100).when(spyList).size();
        assertThat(spyList.size()).isEqualTo(100);
    }

    @Spy
    List<String> spiedList = new ArrayList<String>();

    @Test
    void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        Mockito.doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }

    // Mock 실행 시 argument 값을 capture
    @Test
    void whenNotUseCaptorAnnotation_thenCorrect() {
        // given
        List mockList = Mockito.mock(List.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        // when
        mockList.add("one");
        Mockito.verify(mockList).add(arg.capture());

        // then
        assertThat(arg.getValue()).isEqualTo("one");
    }

    @Captor
    ArgumentCaptor argument;

    @Test
    void whenUseCaptorAnnotation_thenCorrect() {
        // given
        // when
        mockedList.add("one");
        Mockito.verify(mockedList).add((String) argument.capture());

        // then
        assertThat(argument.getValue()).isEqualTo("one");
    }

    // InjectionMock의 Mock 인스턴스 생성시 필요한 인스턴스를 Mock 으로 선언된 인스턴스로 주입
    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    MyDictionary dic;

    @Test
    void whenUseInjectMocksAnnotation_thenCorrect() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", dic.getMeaning("aWord"));
    }

    // injectionMock 이 spy 이면 mock 인스턴스가 자동으로 주입되지 않으며 직접 주입하여야 함
    // 오류 발생
    // @Spy
    // MyDictionary spyDic = new MyDictionary();

    MyDictionary spyDic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        spyDic = Mockito.spy(new MyDictionary(wordMap));
    }

    @Test
    public void whenUseInjectMocksAnnotationSpy_thenCorrect() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", spyDic.getMeaning("aWord"));
    }

    public static class MyDictionary {
        Map<String, String> wordMap;

        public MyDictionary(Map wordMap) {
            this.wordMap = wordMap;
        }

        public void add(final String word, final String meaning) {
            this.wordMap.put(word, meaning);
        }

        public String getMeaning(final String word) {
            return wordMap.get(word);
        }
    }

    @Test
    void whenMockitoAnnotationsUninitialized_thenNPEThrown() {
        assertThrows(NullPointerException.class, () -> {
          Mockito.when(mockedList.size()).thenThrow(NullPointerException.class);
          mockedList.size();
        });
    }
}
