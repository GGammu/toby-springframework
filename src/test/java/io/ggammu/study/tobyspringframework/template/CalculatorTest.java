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
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(numFilePath);
        assertThat(sum).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int sum = calculator.calcMultiply(numFilePath);
        assertThat(sum).isEqualTo(24);
    }
}