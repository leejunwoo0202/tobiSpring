package spring.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import spring.annotation.EnableSqlService;
import spring.dao.UserDao;
import spring.dao.UserDaoJdbc;
import spring.service.UserService;
import spring.service.UserServiceImpl;
import spring.sql.SqlService;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@EnableTransactionManagement
@Import({SqlServiceContext.class })
@EnableSqlService
@PropertySource("/database.properties")
public class AppContext implements SqlMapConfig{
    /**
     * DB연결과 트랜잭션
     */

    @Autowired
    Environment env;

    @Value("${db.driverClass}") Class<? extends Driver> driverClass;
    @Value("${db.url}") String url;
    @Value("${db.username}") String username;
    @Value("${db.password}") String password;


    @Override
    public Resource getSqlMapResource()
    {
        return new ClassPathResource("sqlmap.xml", UserDao.class);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource()
    {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();


        ds.setDriverClass(this.driverClass);
        ds.setUrl(this.url);
        ds.setUsername(this.username);
        ds.setPassword(this.password);

        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource((javax.sql.DataSource) dataSource());
        return tm;
    }

    /**
     * 애플리케이션 로직 & 테스트용 빈
     */

    @Autowired
    SqlService sqlService;

    @Bean
    public UserDao userDao() {
        UserDaoJdbc dao = new UserDaoJdbc();
        dao.setDataSource((javax.sql.DataSource) dataSource());
        dao.setSqlService(this.sqlService);
        return dao;
    }

    @Bean
    public UserService userService() {
        UserServiceImpl service = new UserServiceImpl();
        service.setUserDao(userDao());

        return service;
    }

//    @Bean
//    public UserService testUserService() {
//        TestUserService testService = new TestUserService();
//        testService.setUserDao(userDao());
//
//        return testService;
//    }






}
