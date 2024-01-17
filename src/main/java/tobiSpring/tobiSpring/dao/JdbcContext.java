package tobiSpring.tobiSpring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private DataSource dataSource;
    UserDao userDao = new UserDao();

    public void setDataSource(DataSource dataSource) throws SQLException, ClassNotFoundException {

        this.dataSource = (DataSource) userDao.getConnection();
    }

    // PreparedStatement는 State를 상속받기 때문에 매개변수로 올 수 있음
    public void workWithStatementStrategy(StatementStrategy stmt) throws
            SQLException {

        Connection c = null;
        PreparedStatement ps = null;

        try{
             c = this.dataSource.getConnection();

             ps = stmt.makePreparedStatement(c);

             ps.executeUpdate();
        }catch(Exception e) {
            throw e;
        }finally{
            if(ps != null) { try {ps.close(); } catch(SQLException e) {} }
            if(c != null) { try {c.close(); } catch(SQLException e) {} }
        }
    }





}

