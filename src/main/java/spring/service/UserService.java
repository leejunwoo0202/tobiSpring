package spring.service;

import spring.domain.User;

import java.sql.SQLException;

public interface UserService {

    void add(User user);
    void canUpgradeLevels() throws SQLException;
}
