package tobiSpring.tobiSpring;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JUnitTest extends Condition<Collection<? extends JUnitTest>> {

    Set<JUnitTest> testObjects = new HashSet<>();

    @Test
    public void test1() {

        Assertions.assertThat(testObjects).isNotIn(this);
        testObjects.add(this);
        
        for(JUnitTest element : testObjects)
        {
            System.out.println("element = " + element);
            System.out.println("테스트 하나 종료 ");
        }



    }

    @Test
    public void test2() {
        Assertions.assertThat(testObjects).isNotIn(this);
        testObjects.add(this);

        for(JUnitTest element : testObjects)
        {
            System.out.println("element = " + element);
            System.out.println("테스트 하나 종료 ");
        }

    }

    @Test
    public void test3() {
        Assertions.assertThat(testObjects).isNotIn(this);
        testObjects.add(this);

        for(JUnitTest element : testObjects)
        {
            System.out.println("element = " + element);
            System.out.println("테스트 하나 종료 ");
        }

    }
}
