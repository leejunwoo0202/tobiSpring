package spring.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    User user;

    @BeforeEach
    public void setUp(){
        user = new User();
    }

    @Test
    public void upgradeLevel(){

        Level[] levels = Level.values();

        for(Level level : levels){
            if(level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            Assertions.assertThat(user.getLevel()).isEqualTo(level.nextLevel());
        }
    }

    @Test
    public void cannotUpgradeLevel(){
        Level[] levels = Level.values();

        for(Level level : levels){
            if(level.nextLevel() != null) continue;
            user.setLevel(level);
            org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
                    () -> {
                user.upgradeLevel();
            }
            );
        }
    }

    // Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
    //            userDao.get("unknown_id");




}