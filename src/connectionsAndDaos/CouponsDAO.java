package connectionsAndDaos;

import exeptions.DAOException;
import java_beans.Coupon;

public interface CouponsDAO extends MainDAO<Coupon>{
    public void addCouponPurchase(int customerID, int couponID) throws DAOException;
    public void deleteCouponPurchase(int customerID,int couponID) throws DAOException;
}
