package configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import spring.dao.UserDao;
import spring.service.UserService;
import spring.service.UserServiceTest;


@Configuration
@Profile("test")
public class TestAppContext {

    @Autowired
    UserDao userDao;

    @Bean
    public UserService testUserService()
    {
        return new UserServiceTest.TestUserService();




    }





}
