package spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import spring.dao.MockUserDao;
import spring.dao.UserDao;
import spring.domain.Level;
import spring.domain.User;
import spring.handler.TransactionHandler;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/resources/FactoryBeanTest-context.xml")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserDao userDao;



    @Autowired
    ApplicationContext context;

    List<User> users;




    @BeforeEach
    public void setUp() {

        System.out.println("userDao = " + userDao);
        System.out.println("userService = " + userServiceImpl);
        users = Arrays.asList(
                new User("a", "aaa", "1111", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("b", "bbb", "2222", Level.BASIC, UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("c", "ccc", "3333", Level.SILVER, 60, UserServiceImpl.MIN_RECOMMEND_FOR_GOLD-1),
                new User("d", "ddd", "4444", Level.SILVER, 60, UserServiceImpl.MIN_RECOMMEND_FOR_GOLD),
                new User("e", "eee", "5555", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void bean(){
        Assertions.assertThat(this.userServiceImpl).isNotNull();
    }

    @Test
    public void upgradeLevels() throws SQLException {

        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        userServiceImpl.canUpgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        Assertions.assertThat(updated.size()).isEqualTo(2);
        checkUserAndLevel(updated.get(0), "a", Level.SILVER);
        checkUserAndLevel(updated.get(1), "b", Level.GOLD);





    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel){
        Assertions.assertThat(updated.getId()).isEqualTo(expectedId);
        Assertions.assertThat(updated.getLevel()).isEqualTo(expectedLevel);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {

        User userUpdate = userDao.get(user.getId());
        if(upgraded){
            Assertions.assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        }else{
            Assertions.assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }

    }

    private void checkLevel(User user, Level expectedLevel){
        User userUpdate = userDao.get(user.getId());
        Assertions.assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    public void add(){
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userServiceImpl.add(userWithLevel);
        userServiceImpl.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        Assertions.assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        Assertions.assertThat(userWithoutLevelRead.getLevel()).isEqualTo(userWithoutLevel.getLevel());

    }

    @Test
    @DirtiesContext
    public void upgradeAllOrNothing(){

        TestUserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);

        ProxyFactoryBean txProxyFactoryBean =
                context.getBean("&userService", ProxyFactoryBean.class);

        txProxyFactoryBean.setTarget(testUserService);
        UserService txUserService = (UserService) txProxyFactoryBean.getObject();


        userDao.deleteAll();

        for(User user : users) userDao.add(user);

        try{
            txUserService.canUpgradeLevels();
            fail("TestUserServiceException expected");
        }catch(TestUserServiceException | SQLException e){

        }

        checkLevelUpgraded(users.get(1), false);


    }

    static class TestUserService extends UserServiceImpl {
        private String id;

        private TestUserService(String id){
            this.id = id;
        }

        protected void upgradeLevel(User user){
            if(user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException{

    }

    static class MockUserDao implements UserDao{

        private List<User> users;
        private List<User> updated = new ArrayList<>();

        private MockUserDao(List<User> users){
            this.users = users;
        }

        public List<User> getUpdated(){
            return this.updated;
        }

        @Override
        public List<User> getAll() {
            return this.users;
        }

        @Override
        public void update(User user) {
            updated.add(user);
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }



        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(String id) {
            throw new UnsupportedOperationException();
        }
    }







}