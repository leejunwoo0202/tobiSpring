package tobiSpring.tobiSpring.template;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tobiSpring.tobiSpring.template.Calculator;

import java.io.IOException;

public class CalcSumTest {

    Calculator calculator;
    String numFilepath;



    @BeforeEach
    public void setUp(){
        this.calculator = new Calculator();
        this.numFilepath = "src/main/java/tobiSpring/tobiSpring/numbers.txt";
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        Assertions.assertThat(calculator.calcMultiply(numFilepath)).isEqualTo(24);
    }



    @Test
    public void sumOfNumbers() throws IOException {

        Assertions.assertThat(calculator.calcSum(numFilepath)).isEqualTo(10);

    }

    @Test
    public void concatenateStrings() throws IOException {
        Assertions.assertThat(calculator.concatenate((this.numFilepath))).isEqualTo("1234");
    }
}
