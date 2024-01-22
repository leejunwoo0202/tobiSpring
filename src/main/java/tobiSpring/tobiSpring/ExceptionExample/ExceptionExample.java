package tobiSpring.tobiSpring.ExceptionExample;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import tobiSpring.tobiSpring.dao.JdbcContext;

import java.sql.SQLException;

public class ExceptionExample {

    JdbcContext jdbcContext = new JdbcContext();

    public void exceptionRecovery() {
        int maxRetry = 10;

        while (maxRetry > 0) {
            try {
                jdbcContext.executeSql("delete from users");
                return;

            } catch (java.lang.Exception e) {
                System.out.println(e.getMessage());
                // 정해진 시간만큼 대기
            } finally {
                // 리소스 반납
            }
        }
    }

    public void exceptionAvoidance() throws SQLException {
        jdbcContext.executeSql("delete from users");
    }


    public void exceptionChange() throws DuplicateUserIdException, SQLException {
        try
        {
            jdbcContext.executeSql("delete from users");
        }
        catch(SQLException e)
        {
            // if id중복 에러코드이면 구체적인 예외로 전환
                throw new DuplicateUserIdException(e);

        }
    }

    public void add() throws DuplicateUserIdException{
        try{
            jdbcContext.executeSql("delete from users");
        }
        catch(SQLException e)
        {
            // id 중복 에러라면 예외 전환
            throw new DuplicateUserIdException(e);
        }
    }
}
