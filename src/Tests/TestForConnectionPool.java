package Tests;

import Tests.thredsForConnectionPoolTest.*;

public class TestForConnectionPool {
    /**
     * run 12 threads to test the connection pool
     */
    public void test() {
        Thred1 thred1 = new Thred1();
        Thred2 thred2 = new Thred2();
        Thred3 thred3 = new Thred3();
        Thred4 thred4 = new Thred4();
        Thred5 thred5 = new Thred5();
        Thred6 thred6 = new Thred6();
        Thred7 thred7 = new Thred7();
        Thred8 thred8 = new Thred8();
        Thred9 thred9 = new Thred9();
        Thred10 thred10 = new Thred10();
        Thred11 thred11 = new Thred11();
        Thred12 thred12 = new Thred12();
        thred1.start();
        thred2.start();
        thred3.start();
        thred4.start();
        thred5.start();
        thred6.start();
        thred7.start();
        thred8.start();
        thred9.start();
        thred10.start();
        thred11.start();
        thred12.start();
    }
}
