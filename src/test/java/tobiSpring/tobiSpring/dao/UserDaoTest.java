package tobiSpring.tobiSpring.dao;

import tobiSpring.tobiSpring.domain.User;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao dao = new UserDao();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        User user2 = dao.get(user.getId());

        if(!user.getName().equals(user2.getName())){
            System.out.println("테스트 실패 (name)");

        }
        else if(!user.getPassword().equals(user2.getPassword())){
            System.out.println("테스트 실패 (password)");

        }
        else {
            System.out.println("조회 테스트 성공");

        }




    }

}