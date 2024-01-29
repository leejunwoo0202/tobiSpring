package spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import jakarta.annotation.Nullable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import spring.domain.Level;
import spring.domain.User;

public class UserDaoJdbc implements UserDao{
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.dataSource = dataSource;
    }

    private final RowMapper<User> userMapper =

            (ResultSet rs, int rowNum) -> {

            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            return user;
        };

    public void add(final User user) {

        this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend) values(?,?,?,?,?,?)",
            user.getId(), user.getName(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }


    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id},
                this.userMapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query(" select * from users order by id", this.userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }



    public int getCount() {

        return this.jdbcTemplate.queryForObject("select count(*) from users",Integer.class);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                "update users set name = ?, password = ?, level = ?, login = ?, " +
                        "recommend = ? where id = ? ",
                user.getName(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(),
                user.getRecommend(), user.getId());

    }
}