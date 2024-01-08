package tobiSpring.tobiSpring.dao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class CountingConnectionMakerTest {


    public static void main(String[] args) {

        // Context 생성
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        // dependency lookup
        UserDao dao = context.getBean("userDao", UserDao.class);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);

        // connection 할 때마다 +1
        ccm.makeConnection();
        ccm.makeConnection();

        System.out.println("Connection counter : " + ccm.getCounter());


    }
}