package io.younghwang.springframeworkbasic.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MockitoArgumentMatchersTest {
    @Mock
    FlowerService flowerService;

    @Test
    void argumentMatchers() {
        // given
        doReturn("Flower").when(flowerService).analyze("poppy");

        // when
        String poppy = flowerService.analyze("poppy");

        // then
        assertThat(poppy).isEqualTo("Flower");
    }
}

class FlowerService {
    public String analyze(String name) {
        return "a";
    }
}