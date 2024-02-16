package abstractSqlRegistry;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.sql.SqlNotFoundException;
import spring.sql.SqlUpdateFailureException;
import spring.sql.UpdatableSqlRegistry;
import spring.sql.updatable.ConcurrentHashMapSqlRegistry;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUpdatableSqlRegistryTest {
    UpdatableSqlRegistry sqlRegistry;

    @BeforeEach
    public void setUp(){
        sqlRegistry = new ConcurrentHashMapSqlRegistry();
        sqlRegistry.registerSql("KEY1", "SQL1");
        sqlRegistry.registerSql("KEY2", "SQL2");
        sqlRegistry.registerSql("KEY3", "SQL3");
    }

    @Test
    public void find() {
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    private void checkFindResult(String expected1, String expected2, String expected3) {

        Assertions.assertThat(sqlRegistry.findSql("KEY1")).isEqualTo(expected1);
        Assertions.assertThat(sqlRegistry.findSql("KEY2")).isEqualTo(expected2);
        Assertions.assertThat(sqlRegistry.findSql("KEY3")).isEqualTo(expected3);
    }

    @Test
    public void unknownKey(){
        Assertions.assertThatThrownBy( () -> sqlRegistry.findSql("SQL9999!#@$")).
                isInstanceOf(SqlNotFoundException.class);
    }

    @Test
    public void updateSingle() {
        sqlRegistry.updateSql("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
    }

    @Test
    public void updateMulti() {
        Map<String, String> sqlmap = new HashMap<String, String>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY3", "Modified3");

        sqlRegistry.updateSql(sqlmap);
        checkFindResult("Modified1", "SQL2", "Modified3");


    }

    @Test
    public void updateWithNotExistingKey(){
        Assertions.assertThatThrownBy( () -> sqlRegistry.updateSql("SQL9999!@#$", "Modified2")
        ).isInstanceOf(SqlUpdateFailureException.class);
    }




}