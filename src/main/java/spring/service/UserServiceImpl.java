package spring.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import spring.dao.UserDao;
import spring.domain.Level;
import spring.domain.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{

    UserDao userDao;

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

        List<User> users = userDao.getAll();

        for(User user : users){
            if(canUpgradeLevel(user))
            {
                upgradeLevel(user);
            }
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
