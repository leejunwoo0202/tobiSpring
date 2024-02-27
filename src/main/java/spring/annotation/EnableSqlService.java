package spring.annotation;

import org.springframework.context.annotation.Import;
import spring.configuration.SqlServiceContext;

@Import(value= SqlServiceContext.class)
public @interface EnableSqlService {
}
