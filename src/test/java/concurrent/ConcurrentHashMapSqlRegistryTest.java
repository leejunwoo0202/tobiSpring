package concurrent;

import abstractSqlRegistry.AbstractUpdatableSqlRegistryTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.sql.SqlNotFoundException;
import spring.sql.SqlUpdateFailureException;
import spring.sql.UpdatableSqlRegistry;
import spring.sql.updatable.ConcurrentHashMapSqlRegistry;

import java.util.HashMap;
import java.util.Map;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();
    }
}
