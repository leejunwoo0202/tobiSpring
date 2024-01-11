package tobiSpring.tobiSpring.dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobiSpring.tobiSpring.domain.User;
import tobiSpring.tobiSpring.factories.DaoFactory;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoTest {


    @Autowired
    private UserDao userDao;


    @BeforeEach
    public void setUp(){
        System.out.print(this.userDao);

    }

    @AfterEach
    public void delete() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {



        User user1 = new User("gyumee", "박성철", "springno1");
        User user2 = new User("leegw700", "이길원", "springno2");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User userget1 = userDao.get(user1.getId());
        assertThat(userget1.getName()).isEqualTo(user1.getName());
        assertThat(userget1.getPassword()).isEqualTo(user1.getPassword());

        User userget2 = userDao.get(user2.getId());
        assertThat(userget2.getName()).isEqualTo(user2.getName());
        assertThat(userget2.getPassword()).isEqualTo(user2.getPassword());




    }

    @Test
    public void deleteAll() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        User user = new User("gyumee", "박성철", "springno1");

        userDao.add(user);
        assertThat(userDao.getCount()).isEqualTo(1);

        User user2 = userDao.get(user.getId());

        assertThat(user.getName()).isEqualTo(user2.getName());
        assertThat(user.getPassword()).isEqualTo(user2.getPassword());

    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {

        User user1 = new User("gyumee", "박성철", "springno1");
        User user2 = new User("leegw700", "이길원", "springno2");
        User user3 = new User("bumjin", "박범진", "springno3");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);


    }

    @Test
    public void getUserFailure() throws SQLException, ClassNotFoundException{

        // JUPITER 이용

        assertThat(userDao.getCount()).isEqualTo(0);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.get("unknown_id");

        }
        );

        // ASSERTJ 이용

        org.assertj.core.api.Assertions.assertThatThrownBy( () -> userDao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);



    }

}