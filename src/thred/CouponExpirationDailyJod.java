package thred;

import connectionsAndDaos.CouponsDBDAO;
import java_beans.Coupon;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CouponExpirationDailyJod implements Runnable{
    private Thread thread=new Thread(this,"daily job");
    private CouponsDBDAO couponsDBDAO=new CouponsDBDAO();
    private List<Coupon> coupons;

    /**
     * check every 5 seconds if database have expired coupons and delete them
     */
    @Override
    public void run() {
        LocalDate now=LocalDate.now();
        while (true) {
            coupons=couponsDBDAO.getAll();
            System.out.println("*");
            for (int i = 0; i < coupons.size(); i++) {
                if (coupons.get(i).getEndDate().isBefore(now)) {
                    System.out.println("delete: "+coupons.get(i).getId());
                    couponsDBDAO.delete(coupons.get(i).getId());
                    break;
                }
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /**
     * start the job
     */
    public void start(){
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * stop the job
     */
    public void stop(){
        thread.interrupt();
    }
}
