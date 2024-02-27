package spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;


import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import spring.domain.Level;
import spring.domain.User;
import spring.sql.SqlService;

@Component
public class UserDaoJdbc implements UserDao{

    private Map<String, String> sqlMap;

    private SqlService sqlService;

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Bean
    public UserDao userDao(){
        return new UserDaoJdbc();
    }

    public void setSqlService(SqlService sqlService){
        this.sqlService = sqlService;
    }

    public void setSqlMap(Map<String, String> sqlMap){
        this.sqlMap = sqlMap;
    }


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


        this.jdbcTemplate.update(this.sqlService.getSql("userAdd"),
                user.getId(), user.getName(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }


    public User get(String id) {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"), new Object[]{id},
                this.userMapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query(this.sqlService.getSql("userGetAll"), this.userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
    }



    public int getCount() {

        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGetCount"),Integer.class);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                this.sqlService.getSql("userUpdate"),
                user.getName(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(),
                user.getRecommend(), user.getId());

    }
}