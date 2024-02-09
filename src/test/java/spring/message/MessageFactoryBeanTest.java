package spring.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(locations = "file:src/main/resources/FactoryBeanTest-context.xml")
class MessageFactoryBeanTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean(){
        Object message = context.getBean("message");
        Assertions.assertThat(message).isEqualTo(Message.class);
        Assertions.assertThat(((Message)message).getText()).isEqualTo("Factory Bean");
    }

}