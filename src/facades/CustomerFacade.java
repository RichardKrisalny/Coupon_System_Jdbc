package facades;

import exeptions.ClientFacadeException;
import java_beans.Category;
import java_beans.Coupon;
import java_beans.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerFacade extends ClientFacade{
    private int customerID;
    private List<Coupon> coupons= couponsDBDAO.getAll();

    /**
     * check if the customer exist
     * @param email customer email
     * @param password customer password
     * @return true if customer exist , else return false
     */
    public boolean login(String email, String password) {
        customerID=customersDBDAO.isExists(email, password);
        return (customerID!=0);
    }

    /**
     * buy coupon
     * @param couponID coupon id
     */
    public void purchase(int couponID) throws ClientFacadeException {
        List<Coupon> customerCoupons=couponsDBDAO.getAllForCustomer(customerID);
        Coupon coupon=new Coupon();
        for (int i = 0; i < coupons.size(); i++) {
            if(couponID==coupons.get(i).getId())
                coupon=coupons.get(i);
        }
        if (coupon!=null) {
            LocalDate now = LocalDate.now();
            for (int i = 0; i < customerCoupons.size(); i++) {
                if (customerCoupons.get(i).getId() == couponID)
                    throw new ClientFacadeException("you have this coupon");
            } if(coupon.getAmount() > 0 && coupon.getEndDate().isAfter(now)) {
                couponsDBDAO.addCouponPurchase(customerID, couponID);
                coupon.setAmount(coupon.getAmount() - 1);
                couponsDBDAO.update(coupon);
                coupons=couponsDBDAO.getAll();
            }else
                throw new ClientFacadeException("can't buy the coupon, or amount is 0 or the coupon dead line");
        }else
            throw new ClientFacadeException("can't find the coupon");
    }

    /**
     * get all coupons for customer
     * @return list of coupons
     */
    public List<Coupon>getCustomerCoupons(){
        List<Coupon> customerCoupons=couponsDBDAO.getAllForCustomer(customerID);
        return customerCoupons;
    }

    /**
     * get all coupon with specific category
     * @param category the category
     * @return list of coupons
     */
    public List<Coupon>getCustomerCoupons(Category category){
        List<Coupon>coupons=getCustomerCoupons();
        ArrayList<Coupon>couponsToReturn=new ArrayList<>();
        for (int i = 0; i < coupons.size(); i++) {
            if(coupons.get(i).getCategory()==category)
                couponsToReturn.add(coupons.get(i));
        }
        return couponsToReturn;
    }

    /**
     * get all customer coupons under some price
     * @param maxPrice the maximum price
     * @return list of coupons
     */
    public List<Coupon>getCustomerCoupons(double maxPrice){
        List<Coupon>coupons=getCustomerCoupons();
        ArrayList<Coupon>couponsToReturn=new ArrayList<>();
        for (int i = 0; i < coupons.size(); i++) {
            if(coupons.get(i).getPrice()<=maxPrice)
                couponsToReturn.add(coupons.get(i));
        }
        return couponsToReturn;
    }

    /**
     * get the customer details
     * @return customer
     */
    public Customer getCustomerDetails(){
       return customersDBDAO.getOne(customerID);
    }


}
