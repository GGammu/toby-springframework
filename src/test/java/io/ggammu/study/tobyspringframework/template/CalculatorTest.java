package io.ggammu.study.tobyspringframework.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {
    Calculator calculator;
    String numFilePath;

    @BeforeEach
    void setUp() {
        Calculator calculator = new Calculator();
        numFilePath = getClass().getResource("numbers.txt").getPath();
    }

    @Test
    void sumOfNumbers() throws IOException {
        assertThat(new Calculator().calcSum(numFilePath)).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        assertThat(new Calculator().calcMultiply(numFilePath)).isEqualTo(24);
    }
    @Test
    void concatenateOfNumbers() throws IOException {
        assertThat(new Calculator().concatenate(numFilePath)).isEqualTo("1234");
    }
}