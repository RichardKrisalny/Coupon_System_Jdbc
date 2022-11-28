package facades;

import exeptions.ClientFacadeException;
import java_beans.Category;
import java_beans.Company;
import java_beans.Coupon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanyFacade extends ClientFacade{
    private int companyID;
    private List<Coupon>coupons=couponsDBDAO.getAll();

    /**
     * check if the company exist in database
     * @param email the company email
     * @param password the company password
     * @return true if company exist and false if not
     */
    @Override
    public boolean login(String email, String password) {
        companyID=companiesDBDAO.isExists(email, password);
        return (companyID!=0);
    }

    /**
     * add coupon to database and check if the company have same coupon
     * @param coupon the coupon to add
     */
    public void addCoupon(Coupon coupon) throws ClientFacadeException {
        if(coupon.getCompanyID()!=companyID)
            throw new ClientFacadeException("the company can't add coupons for another company");
        for (int i = 0; i <coupons.size() ; i++) {
            if(coupons.get(i).getCompanyID()==companyID&& Objects.equals(coupons.get(i).getTitle(), coupon.getTitle()))
            throw new ClientFacadeException("the company have coupon with this description ");
        }
        couponsDBDAO.add(coupon);
        coupons=couponsDBDAO.getAll();
    }

    /**
     * update coupon
     * @param coupon the coupon to update
     */
    public void updateCoupon(Coupon coupon) throws ClientFacadeException {
                if(getCoupon(coupon.getId()).getCompanyID()!=coupon.getCompanyID()||coupon.getId()!=getCoupon(coupon.getId()).getId()){
                    throw new ClientFacadeException("can't update company id or coupon id");
            }
        couponsDBDAO.update(coupon);
        coupons=couponsDBDAO.getAll();
    }

    /**
     * delete coupon from database
     * @param couponID coupon id to delete
     */
    public void deleteCoupon(int couponID) throws ClientFacadeException {
        if(getCoupon(couponID)==null||getCoupon(couponID).getCompanyID()!=companyID)
            throw new ClientFacadeException("can't delete coupon ");
        couponsDBDAO.delete(couponID);
        coupons=couponsDBDAO.getAll();
    }

    /**
     * get all company coupons
     * @return list of coupons
     */
    public List<Coupon> CompanyCoupons(){
        List<Coupon> couponsToReturn=new ArrayList<>();
        for (int i = 0; i < coupons.size(); i++) {
            if(coupons.get(i).getCompanyID()==companyID)
                couponsToReturn.add(coupons.get(i));
        }
        return couponsToReturn;
    }

    /**
     * get all company coupons with specific category
     * @param category the category
     * @return list of coupons
     */
    public List<Coupon> CompanyCoupons(Category category){
        List<Coupon> couponsToReturn=new ArrayList<>();
        for (int i = 0; i < coupons.size(); i++) {
            if(coupons.get(i).getCategory()==category&&coupons.get(i).getCompanyID()==companyID)
                couponsToReturn.add(coupons.get(i));
        }
        return couponsToReturn;
    }

    /**
     * get all company coupons under specific price
     * @param maxPrice the maximum price
     * @return list of coupons
     */
    public List<Coupon> CompanyCoupons(double maxPrice){
        List<Coupon> couponsToReturn=new ArrayList<>();
        for (int i = 0; i < coupons.size(); i++) {
            if(coupons.get(i).getPrice()<=maxPrice&&coupons.get(i).getCompanyID()==companyID)
                couponsToReturn.add(coupons.get(i));
        }
        return couponsToReturn;
    }

    /**
     * get the company details
     * @return company
     */
    public Company getCompanyDetails(){
        return companiesDBDAO.getOne(companyID);
    }

    /**
     * find coupon by id
     * @param couponID coupon id
     * @return coupon
     */
    private  Coupon getCoupon(int couponID) throws ClientFacadeException {
        for (int i = 0; i <coupons.size(); i++) {
            if(coupons.get(i).getId()==couponID){
                return coupons.get(i);
            }
        }
throw new ClientFacadeException("can't find the coupon");
    }
}
