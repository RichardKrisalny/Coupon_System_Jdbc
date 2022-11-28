package Tests.thredsForConnectionPoolTest;

import connectionsAndDaos.ConnectionPool;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class Thred9 extends Thread{
    @Override
    public void run() {
        Connection c= ConnectionPool.getInstance().getConnection();
        System.out.println("9 get connection");
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ConnectionPool.getInstance().restoreConnection(c);
        System.out.println("9 restore connection");
    }
}
