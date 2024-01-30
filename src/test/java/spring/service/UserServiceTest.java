package spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.dao.UserDao;
import spring.domain.Level;
import spring.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/resources/applicationContext.xml")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    List<User> users;


    @BeforeEach
    public void setUp() {

        System.out.println("userDao = " + userDao);
        System.out.println("userService = " + userService);
        users = Arrays.asList(
                new User("a", "aaa", "1111", Level.BASIC, UserService.MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("b", "bbb", "2222", Level.BASIC, UserService.MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("c", "ccc", "3333", Level.BASIC, 60, UserService.MIN_RECOMMEND_FOR_GOLD-1),
                new User("d", "ddd", "4444", Level.SILVER, 60, UserService.MIN_RECOMMEND_FOR_GOLD),
                new User("e", "eee", "5555", Level.SILVER, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void bean(){
        Assertions.assertThat(this.userService).isNotNull();
    }

    @Test
    public void upgradeLevels(){
        userDao.deleteAll();

        for(User user : users) userDao.add(user);

        userService.canUpgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);



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

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        Assertions.assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        Assertions.assertThat(userWithoutLevelRead.getLevel()).isEqualTo(userWithoutLevel.getLevel());

    }



}