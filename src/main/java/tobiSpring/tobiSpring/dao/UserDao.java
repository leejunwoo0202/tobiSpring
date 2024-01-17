package tobiSpring.tobiSpring.dao;

import org.mariadb.jdbc.Connection;
import org.springframework.dao.EmptyResultDataAccessException;
import tobiSpring.tobiSpring.domain.User;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;
    private JdbcContext jdbcContext;

    public void setJdbcContext(JdbcContext jdbcContext){
        this.jdbcContext = jdbcContext;
    }


    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void add(final User user) throws ClassNotFoundException, SQLException {

        this.jdbcContext.workWithStatementStrategy(
                c -> {

                    PreparedStatement ps = c.prepareStatement(
                            "insert into users(id, name, password) values(?,?,?)");
                    ps.setString(1, user.getId());
                    ps.setString(2, user.getName());
                    ps.setString(3, user.getPassword());

                    return ps;

                }
        );
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {

        this.jdbcContext.workWithStatementStrategy(
                c -> c.prepareStatement("delete from users")
        );

    }


    public User get(String id) throws ClassNotFoundException, SQLException {
        java.sql.Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();


        User user = null;

        if(rs.next()){
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();


        if(user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }



    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        Connection c = (Connection) DriverManager.getConnection(
                "jdbc:mariadb://localhost:3307/bootex", "bootuser", "bootuser");
        return c;
    }


    public int getCount() throws SQLException, ClassNotFoundException {
        java.sql.Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {

                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {

                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {

                }
            }

        }







    }




}
