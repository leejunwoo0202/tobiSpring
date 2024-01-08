package tobiSpring.tobiSpring.dao;


import org.mariadb.jdbc.Connection;

public interface ConnectionMaker {

    public Connection makeConnection();
}
