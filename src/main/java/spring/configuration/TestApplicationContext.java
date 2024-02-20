package spring.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import spring.dao.UserDao;
import spring.dao.UserDaoJdbc;
import spring.service.UserService;
import spring.service.UserServiceImpl;
import spring.sql.OxmSqlService;
import spring.sql.SqlRegistry;
import spring.sql.SqlService;
import spring.sql.updatable.EmbeddedDbSqlRegistry;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TestApplicationContext {
    /**
     * DB연결과 트랜잭션
     */

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource ();

        dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mariadb://localhost:3307/bootex");
        dataSource.setUsername("bootuser");
        dataSource.setPassword("bootuser");

        return dataSource;
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





    /**
     * SQL서비스
     */

    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlRegistry(sqlRegistry());
        return sqlService;
    }


    @Bean
    public SqlRegistry sqlRegistry() {
        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
        sqlRegistry.setDataSource(embeddedDatabase());
        return sqlRegistry;
    }

    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("spring.sql.jaxb");
        return marshaller;
    }

    @Bean
    public DataSource embeddedDatabase() {
        return (DataSource) new EmbeddedDatabaseBuilder()
                .setName("embeddedDatabase")
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:/sql/sqlRegistrySchema.sql")
                .build();
    }
}
