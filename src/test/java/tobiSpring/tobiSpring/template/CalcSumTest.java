package tobiSpring.tobiSpring.template;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tobiSpring.tobiSpring.template.Calculator;

import java.io.IOException;

public class CalcSumTest {

    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        String filePath = "src/main/java/tobiSpring/tobiSpring/numbers.txt";
        int sum = calculator.calcSum(filePath);

        Assertions.assertThat(sum).isEqualTo(10);

    }
}
