package tobiSpring.tobiSpring.factories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import tobiSpring.tobiSpring.dao.DataSource;
import tobiSpring.tobiSpring.dao.UserDao;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao()
    {
        return new UserDao();
    }


}
