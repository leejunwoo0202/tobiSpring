package tobiSpring.tobiSpring.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {


    /* //생성자 방식
    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }*/

    // 메서드 방식
    @Bean
    public UserDao userDao()
    {
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker());
        return userDao;
    }


    @Bean
    public ConnectionMaker connectionMaker()
    {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }
}
