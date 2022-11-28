package Tests.thredsForConnectionPoolTest;

import connectionsAndDaos.ConnectionPool;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class Thred12 extends Thread{
    @Override
    public void run() {
        Connection c= ConnectionPool.getInstance().getConnection();
        if (c==null){
            System.out.println("nullllllllllllll");
        }
        System.out.println("12 get connection");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ConnectionPool.getInstance().restoreConnection(c);
        System.out.println("12 restore connection");
    }
}
