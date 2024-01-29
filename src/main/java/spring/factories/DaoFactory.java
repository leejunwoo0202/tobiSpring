//package spring.factories;
//
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.SimpleDriverDataSource;
//import spring.dao.UserDao;
//import spring.dao.UserDaoJdbc;
//import spring.service.UserService;
//
//@Configuration
//public class DaoFactory {
//    @Bean
//    public DataSource dataSource() {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource ();
//
//        dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
//        dataSource.setUrl("jdbc:mariadb://localhost:3307/bootex");
//        dataSource.setUsername("bootuser");
//        dataSource.setPassword("bootuser");
//
//        return dataSource;
//    }
//
//    @Bean
//    public UserDao userDao() {
//        UserDaoJdbc userDao = new UserDaoJdbc();
//        userDao.setDataSource(dataSource());
//        return userDao;
//    }
//
//    @Bean
//    public UserService userService(){
//        UserService userService = new UserService();
//        userService.setUserDao(userDao());
//
//        return userService;
//    }
//}
