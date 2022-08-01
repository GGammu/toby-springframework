package io.younghwang.springframeworkbasic.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcSumTest {
    Calculator calculator;
    String numFilePath;

    @BeforeEach
    void setUp() {
        this.calculator = new Calculator();
        this.numFilePath = getClass().getResource("numbers.txt").getPath();
    }

    @Test
    void sumOfNumbers() throws IOException {
        // given
        // when
        int sum = calculator.calcSum(numFilePath);
        // then
        assertThat(sum).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        // given
        // when
        int multiply = calculator.calcMultiply(numFilePath);
        // then
        assertThat(multiply).isEqualTo(24);
    }

    @Test
    void concatenateStrings() throws IOException {
        // given
        // when
        // then
        assertThat(calculator.concatenate(this.numFilePath)).isEqualTo("1234");
    }
}
