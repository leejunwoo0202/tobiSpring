package tobiSpring.tobiSpring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import tobiSpring.tobiSpring.dao.JdbcContext;
import tobiSpring.tobiSpring.dao.StatementStrategy;
import tobiSpring.tobiSpring.domain.User;

public class UserDaoJdbc implements UserDao{
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.dataSource = dataSource;
    }



    public void add(final User user) {

        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
            user.getId(), user.getName(), user.getPassword());
    }


    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                        return user;
                    }
                }
        );
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }



    public int getCount() {

        return this.jdbcTemplate.queryForObject("select count(*) from users",Integer.class);
    }
}