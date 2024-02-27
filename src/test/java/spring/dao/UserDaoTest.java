package spring.dao;

import configuration.TestAppContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.configuration.AppContext;
import spring.domain.Level;
import spring.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppContext.class, AppContext.class })
class UserDaoTest {


    @Autowired
    private UserDao userDao;

    @Autowired
    DefaultListableBeanFactory bf;



    User user1;
    User user2;
    User user3;



    @BeforeEach
    public void setUp(){
        System.out.print(this.userDao);
        this.user1 = new User("gyumee", "박성철", "springno1", Level.BASIC, 1, 0);
        this.user2 = new User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10);
        this.user3 = new User("bumjin", "박범진", "springno3", Level.GOLD, 100, 40);

    }

    @AfterEach
    public void delete() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User userget1 = userDao.get(user1.getId());
        checkSameUser(user1,userget1);

        User userget2 = userDao.get(user2.getId());
        checkSameUser(user2,userget2);




    }

    @Test
    public void deleteAll() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);



        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        User user2 = userDao.get(user1.getId());

        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());

    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {

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

    @Test
    public void duplicateKey() {
        userDao.deleteAll();



        // 1 try catch 사용
        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException e) {
            System.out.println("duplicateKey예외");
        }

        // 2 JUPITER 이용
        Assertions.assertThrows(DuplicateKeyException.class, () -> {
            userDao.add(user1);
            userDao.add(user1);

                }
        );
    }

    private void checkSameUser(User user1, User user2){
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }


    @Test
    public void update(){
        userDao.deleteAll();

        userDao.add(user1);
        userDao.add(user2);

        user1.setName("오민규");
        user1.setPassword("springno6");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        userDao.update(user1);

        User user1update = userDao.get(user1.getId());
        checkSameUser(user1, user1update);

        User user2same = userDao.get(user2.getId());
        checkSameUser(user2, user2same);

    }


    @Test
    public void beans()
    {
        for(String n : bf.getBeanDefinitionNames()){
            System.out.println(n + " \t " + bf.getBean(n).getClass().getName());
        }
    }

}