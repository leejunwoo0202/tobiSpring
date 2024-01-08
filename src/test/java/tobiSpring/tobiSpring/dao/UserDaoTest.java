package tobiSpring.tobiSpring.dao;

import org.junit.jupiter.api.Test;
import tobiSpring.tobiSpring.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;


class UserDaoTest {



    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {

        UserDao dao = new UserDao();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        User user2 = dao.get(user.getId());

        assertThat(user.getName()).isEqualTo(user2.getName());
        assertThat(user.getPassword()).isEqualTo(user2.getPassword());



    }

}