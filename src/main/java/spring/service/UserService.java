package spring.service;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import spring.dao.UserDao;
import spring.domain.Level;
import spring.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    UserDao userDao;

    javax.sql.DataSource dataSource;

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    protected void upgradeLevel(User user){
        user.upgradeLevel();
        userDao.update(user);
    }

    public void canUpgradeLevels() throws SQLException {

        TransactionSynchronizationManager.initSynchronization();
        Connection c = DataSourceUtils.getConnection((javax.sql.DataSource) dataSource);
        c.setAutoCommit(false);

        try{
            List<User> users = userDao.getAll();

            for(User user : users){
                if(canUpgradeLevel(user))
                {
                    upgradeLevel(user);
                }
            }

            c.commit();
        }catch(Exception e){
            c.rollback();
            throw e;
        }finally{
            DataSourceUtils.releaseConnection(c, (javax.sql.DataSource) dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
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