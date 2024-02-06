package spring.service;

import com.sun.jna.Platform;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import spring.domain.User;

import java.sql.SQLException;

public class UserServiceTx implements UserService{

    UserService userService;

    PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }


    @Override
    public void add(User user) {
        userService.add(user);
    }

    @Override
    public void canUpgradeLevels() throws SQLException {

        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
            userService.canUpgradeLevels();

            this.transactionManager.commit(status);
        }catch(RuntimeException e){

            this.transactionManager.rollback(status);
            throw e;
        }


    }
}
