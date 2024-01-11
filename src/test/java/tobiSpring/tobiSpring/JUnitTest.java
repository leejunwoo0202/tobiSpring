package tobiSpring.tobiSpring;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

public class JUnitTest extends Condition<JUnitTest> {
    static JUnitTest testObject;

    @Test
    public void test1() {
        System.out.println("this = " + this);
        System.out.println("testObject = " + testObject);
        Assertions.assertThat(this).isNotSameAs(testObject);

    }

    @Test
    public void test2() {
        System.out.println("this = " + this);
        System.out.println("testObject = " + testObject);
        Assertions.assertThat(this).isNotSameAs(testObject);

    }

    @Test
    public void test3() {
        System.out.println("this = " + this);
        System.out.println("testObject = " + testObject);
        Assertions.assertThat(this).isNotSameAs(testObject);
       
    }
}
