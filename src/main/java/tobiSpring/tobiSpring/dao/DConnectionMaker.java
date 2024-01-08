package tobiSpring.tobiSpring.dao;

import org.mariadb.jdbc.Connection;

public class DConnectionMaker implements ConnectionMaker{

    // connection 메소드
    public Connection makeConnection()
    {
        System.out.println("dconnection메소드 오버라이딩");

        return null;
    }


}
