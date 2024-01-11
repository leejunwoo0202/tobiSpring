package tobiSpring.tobiSpring;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountTest {

    private int count = 0;  // 테스트 클래스의 멤버 변수

    @Test
    void test1() {
        count++;
        assertEquals(1, count);
    }

    @Test
    void test2() {
        count++;
        assertEquals(1, count); // 각 테스트는 독립적인 상태를 가지므로 test1에서의 count 값과는 무관
    }
}