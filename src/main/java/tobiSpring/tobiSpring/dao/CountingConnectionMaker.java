package tobiSpring.tobiSpring.dao;

import org.mariadb.jdbc.Connection;

import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker{

    int counter = 0;

    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker)
    {
        this.realConnectionMaker = realConnectionMaker;
    }

    public Connection makeConnection() {
        this.counter++;

        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return this.counter;
    }
}
