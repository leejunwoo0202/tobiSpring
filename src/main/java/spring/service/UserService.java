package spring.service;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import spring.dao.UserDao;
import spring.domain.Level;
import spring.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserService {

    UserDao userDao;

    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }



    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }



    protected void upgradeLevel(User user){
        user.upgradeLevel();
        userDao.update(user);

    }



    public void canUpgradeLevels() throws SQLException {



        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try{
            List<User> users = userDao.getAll();

            for(User user : users){
                if(canUpgradeLevel(user))
                {
                    upgradeLevel(user);
                }
            }

            this.transactionManager.commit(status);
        }catch(Exception e){
            this.transactionManager.rollback(status);
            throw e;
        }




    }

    public boolean canUpgradeLevel(User user){
        Level currentLevel = user.getLevel();

        switch(currentLevel){
            case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER : return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD : return false;
            default : throw new IllegalArgumentException("Unknown Level : " + currentLevel);
        }
    }



    public void add(User user){
        if(user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);

    }




}
