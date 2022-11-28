package Tests;

import connectionsAndDaos.ConnectionPool;
import exeptions.LoginException;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;
import java_beans.Category;
import java_beans.Company;
import java_beans.Coupon;
import java_beans.Customer;
import login.ClientType;
import login.LoginManager;
import thred.CouponExpirationDailyJod;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class Test {
    private LoginManager loginManager = LoginManager.getInstance();
    /**
     * run all tests
     */
    public void testAll(){
        TestForConnectionPool testForConnectionPool=new TestForConnectionPool();
        adminTest();
        companyTest();
        customerTest();
        System.out.println("\n thread test \n");
        threadTest();
        System.out.println("\n ConnectionPool test \n");
        testForConnectionPool.test();
        ConnectionPool.getInstance().closeAllConnections();
    }



    public void adminTest() {
        System.out.println("\n*************** Admin test **************************");
        try {
            AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            Company company= new Company("LINOVE","linovo@gamil.com","25897");
            Company companyToUpdate= new Company("IBM","ddd.gmail.com","25897");
            companyToUpdate.setId(2);
            Customer customer=new Customer("moshe","cohen","m@gmail","124578");
            Customer customerToUpdate=new Customer("vladimer","putin","VVP@gmail","KBG");
            customerToUpdate.setId(4);
            System.out.println("---------------------COMPANIES TEST-----------------");
            adminFacade.addCompany(company);
            adminFacade.updateCompany(companyToUpdate);
            adminFacade.deleteCompany(3);
            System.out.println("---------------------ALL COMPANIES------------------");
            System.out.println(adminFacade.getAllCompanies());
            System.out.println("---------------------COMPANY 1 ---------------------");
            System.out.println(adminFacade.getOneCompany(1));

            System.out.println("---------------------CUSTOMERS TEST-----------------");
            adminFacade.addCustomer(customer);
            adminFacade.updateCustomer(customerToUpdate);
            adminFacade.deleteCustomer(3);
            System.out.println("---------------------ALL CUSTOMERS------------------");
            System.out.println(adminFacade.getAllCustomers());
            System.out.println("--------------------- CUSTOMER 2  ------------------");
            System.out.println(adminFacade.getOneCustomer(2));
        } catch (LoginException e) {
            throw new RuntimeException("test admin failed");
        }
    }




    public void companyTest(){
        System.out.println("\n*************** Company test **************************");
        try {
            CompanyFacade companyFacade = (CompanyFacade) loginManager.login("intel@gmail.com","1234", ClientType.COMPANY);
            Coupon coupon = new Coupon(1,Category.CAMPING,"tent","big tent",LocalDate.of(2022,11,25),LocalDate.of(2021,12,25),25,10,"IMG");
            Coupon couponToUpdate = new Coupon(1,Category.CAMPING,"tent","big tent",LocalDate.of(2022,11,25),LocalDate.of(2022,12,25),25,1000,"IMG");
            couponToUpdate.setId(4);
            companyFacade.addCoupon(coupon);
            companyFacade.updateCoupon(couponToUpdate);
            companyFacade.deleteCoupon(1);
            System.out.println("---------------ALL Coupons OF company: 1----------------");
            System.out.println(companyFacade.CompanyCoupons());
            System.out.println("---------------camping Coupons OF company: 1------------");
            System.out.println(companyFacade.CompanyCoupons(Category.CAMPING));
            System.out.println("---------------Coupons OF company: 1 under 150$---------");
            System.out.println(companyFacade.CompanyCoupons(150));
            System.out.println("--------------- company 1 details ----------------------");
            System.out.println(companyFacade.getCompanyDetails());
        } catch (LoginException e) {
            throw new RuntimeException("test company failed");
        }
    }



    public void customerTest(){
        System.out.println("\n*************** Customer test **************************");
        try {
            CustomerFacade customerFacade = (CustomerFacade) loginManager.login("rk@gmail.com","7777", ClientType.CUSTOMER);
            customerFacade.purchase(7);
            System.out.println("---------------ALL Coupons OF costumer: 1----------------");
            System.out.println(customerFacade.getCustomerCoupons());
            System.out.println("---------------Sport Coupons OF costumer: 1 -------------");
            System.out.println(customerFacade.getCustomerCoupons(Category.ELECTRICITY));
            System.out.println("---------------under 100 Coupons OF costumer: 1----------");
            System.out.println(customerFacade.getCustomerCoupons(100));
            System.out.println("--------------- costumer 1 details ----------------------");
            System.out.println(customerFacade.getCustomerDetails());
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

    }
    public void threadTest(){
        CouponExpirationDailyJod job=new CouponExpirationDailyJod();
        job.start();
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        job.stop();
    }
}
