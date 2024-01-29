package spring.service;

import spring.dao.UserDao;
import spring.domain.Level;
import spring.domain.User;

import java.util.List;

public class UserService {

    UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    private void upgradeLevel(User user){
        user.upgradeLevel();
        userDao.update(user);
    }

    public void canUpgradeLevels(){
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
            case BASIC : return (user.getLogin() >= 50);
            case SILVER : return (user.getRecommend() >= 30);
            case GOLD : return false;
            default : throw new IllegalArgumentException("Unknown Level : " + currentLevel);
        }
    }



    public void add(User user){
        if(user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);

    }




}
