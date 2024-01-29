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
                new User("a", "aaa", "1111", Level.BASIC, 49, 0),
                new User("b", "bbb", "2222", Level.BASIC, 50, 0),
                new User("c", "ccc", "3333", Level.BASIC, 60, 29),
                new User("d", "ddd", "4444", Level.SILVER, 60, 30),
                new User("e", "eee", "5555", Level.SILVER, 100, 100)
        );
    }

    @Test
    public void bean(){
        Assertions.assertThat(this.userService).isNotNull();
    }

    @Test
    public void upgradeLevels(){
        userDao.deleteAll();



        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);

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