package spring.sql;

import java.util.Map;

public interface UpdatableSqlRegistry extends SqlRegistry{

    public void updateSql(String key, String sql);

    public void updateSql(Map<String, String> sqlmap);
}
