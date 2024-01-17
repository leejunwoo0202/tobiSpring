package tobiSpring.tobiSpring.factories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import tobiSpring.tobiSpring.dao.DataSource;
import tobiSpring.tobiSpring.dao.JdbcContext;
import tobiSpring.tobiSpring.dao.UserDao;

import java.sql.SQLException;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.setDataSource((DataSource) dataSource());
        userDao.setJdbcContext(jdbcContext());
        return userDao;
    }

    @Bean
    public JdbcContext jdbcContext() throws SQLException, ClassNotFoundException {
        JdbcContext jdbcContext = new JdbcContext();
        jdbcContext.setDataSource((DataSource) dataSource());
        return jdbcContext;
    }

    @Bean
    public SimpleDriverDataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        // 여기에 데이터베이스 관련 설정 추가 (driverClassName, url, username, password 등)
        return dataSource;
    }








}
