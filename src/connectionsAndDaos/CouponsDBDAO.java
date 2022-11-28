package connectionsAndDaos;

import exeptions.DAOException;
import java_beans.Category;
import java_beans.Coupon;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CouponsDBDAO implements CouponsDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private PreparedStatement statement;

    /**
     * add line to table Coupons-vs-costumers (buy a coupon)
     * @param customerID the customer id who bought the coupon
     * @param couponID the coupon id
     */
    @Override
    public void addCouponPurchase(int customerID, int couponID) throws DAOException {
        Connection connection = connectionPool.getConnection();
        String sql = "insert into customers_vs_coupons values(?,?);";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't add coupon to purchase", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * delete line from table Coupons-vs-costumers
     * @param customerID customer id
     * @param couponID coupon id
     */
    @Override
    public void deleteCouponPurchase(int customerID, int couponID) throws DAOException {
        Connection connection = connectionPool.getConnection();
        String sql = "delete from customers_vs_coupons where CUSTOMER_ID=? ,COUPON_ID=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't delete coupon from purchase", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public int isExists(String email, String password) {
        return 0;
    }

    /**
     * add coupon to the database
     * @param coupon the coupon to add
     */
    @Override
    public void add(Coupon coupon) throws DAOException {
        Connection connection = connectionPool.getConnection();
        String sql = "insert into coupons values(0,?,?,?,?,?,?,?,?,?);";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, coupon.getCompanyID());
            statement.setString(2, coupon.getCategory().toString());
            statement.setString(3, coupon.getTitle());
            statement.setString(4, coupon.getDescription());
            statement.setDate(5, Date.valueOf(coupon.startDate));
            statement.setDate(6, Date.valueOf(coupon.getEndDate()));
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't add coupon", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * update coupon in database
     * @param coupon the coupon to update
     */
    @Override
    public void update(Coupon coupon) throws DAOException {
        Connection connection = connectionPool.getConnection();
        String sql = "update COUPONS set COMPANY_ID=?,CATEGORY=?,TITLE=? ,DESCRIPTION=?,START_DATE=?,END_DATE=?,AMOUNT=?,PRICE=?,IMAGE=? where ID=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, coupon.getCompanyID());
            statement.setString(2, coupon.getCategory().toString());
            statement.setString(3, coupon.getTitle());
            statement.setString(4, coupon.getDescription());
            statement.setDate(5, Date.valueOf(coupon.startDate));
            statement.setDate(6, Date.valueOf(coupon.getEndDate()));
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.setInt(10, coupon.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't update coupon", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * delete coupon from database
     * @param couponID the coupon id to delete
     */
    @Override
    public void delete(int couponID) throws DAOException {
        Connection connection = connectionPool.getConnection();
        String sql = "delete from coupons where ID=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, couponID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't delete coupon", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * get all coupons in database
     * @return list of all coupons
     */
    @Override
    public List<Coupon> getAll() throws DAOException {
        Connection connection = connectionPool.getConnection();
        ResultSet rs;
        List<Coupon> couponArrayList = new ArrayList<>();
        String sql = "select * from coupons";
        try {
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon();
                coupon.setId(rs.getInt("ID"));
                coupon.setCompanyID(rs.getInt("COMPANY_ID"));
                coupon.setCategory(Category.valueOf(rs.getString("CATEGORY")));
                coupon.setTitle(rs.getString("TITLE"));
                coupon.setDescription(rs.getString("DESCRIPTION"));
                coupon.setStartDate((rs.getDate("START_DATE").toLocalDate()));
                coupon.setEndDate(rs.getDate("END_DATE").toLocalDate());
                coupon.setAmount(rs.getInt("AMOUNT"));
                coupon.setPrice(rs.getInt("PRICE"));
                coupon.setImage(rs.getString("IMAGE"));
                couponArrayList.add(coupon);
            }
        } catch (SQLException e) {
            throw new DAOException("can't getAll coupon", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return couponArrayList;
    }

    /**
     * get all coupons for specific customer
     * @param customerID the customer id
     * @return list of customers coupons
     */
    public List<Coupon> getAllForCustomer(int customerID) throws DAOException {
        LocalDate ld;
        Connection connection = connectionPool.getConnection();
        ResultSet rs;
        List<Coupon> couponArrayList = new ArrayList<>();
        String sql = "select * from customers_vs_coupons a,coupons b where a.COUPON_ID = b.ID AND a.CUSTOMER_ID = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerID);
            rs = statement.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon();
                coupon.setId(rs.getInt("ID"));
                coupon.setCompanyID(rs.getInt("COMPANY_ID"));
                coupon.setCategory(Category.valueOf(rs.getString("CATEGORY")));
                coupon.setTitle(rs.getString("TITLE"));
                coupon.setDescription(rs.getString("DESCRIPTION"));
                coupon.setStartDate((rs.getDate("START_DATE").toLocalDate()));
                coupon.setEndDate(rs.getDate("END_DATE").toLocalDate());
                coupon.setAmount(rs.getInt("AMOUNT"));
                coupon.setPrice(rs.getInt("PRICE"));
                coupon.setImage(rs.getString("IMAGE"));
                couponArrayList.add(coupon);

            }
        } catch (SQLException e) {
            throw new DAOException("can't getAll coupon", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return couponArrayList;
    }

    /**
     * get all coupons for specific company
     * @param companyID the company id
     * @return list of company coupons
     */
    public List<Coupon> getAllForCompany(int companyID) throws DAOException {
        LocalDate ld;
        Connection connection = connectionPool.getConnection();
        ResultSet rs;
        List<Coupon> couponArrayList = new ArrayList<>();
        String sql = "select * from coupons a where COMPANY_ID = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, companyID);
            rs = statement.executeQuery();
            while (rs.next()) {
                Coupon coupon = new Coupon();
                coupon.setId(rs.getInt("ID"));
                coupon.setCompanyID(rs.getInt("COMPANY_ID"));
                coupon.setCategory(Category.valueOf(rs.getString("CATEGORY")));
                coupon.setTitle(rs.getString("TITLE"));
                coupon.setDescription(rs.getString("DESCRIPTION"));
                coupon.setStartDate((rs.getDate("START_DATE").toLocalDate()));
                coupon.setEndDate(rs.getDate("END_DATE").toLocalDate());
                coupon.setAmount(rs.getInt("AMOUNT"));
                coupon.setPrice(rs.getInt("PRICE"));
                coupon.setImage(rs.getString("IMAGE"));
                couponArrayList.add(coupon);
            }
        } catch (SQLException e) {
            throw new DAOException("can't getAll coupon", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return couponArrayList;
    }

    /**
     * get specific coupon
     * @param couponID the coupon id
     * @return coupon
     */
    @Override
    public Coupon getOne(int couponID) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Coupon coupon = new Coupon();
        ResultSet rs;
        String sql = "select * from COUPONS where ID=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, couponID);
            rs = statement.executeQuery();
            rs.next();
            coupon.setId(rs.getInt("ID"));
            coupon.setCompanyID(rs.getInt("COMPANY_ID"));
            coupon.setCategory(Category.valueOf(rs.getString("CATEGORY")));
            coupon.setTitle(rs.getString("TITLE"));
            coupon.setDescription(rs.getString("DESCRIPTION"));
            coupon.setStartDate(rs.getDate("START_DATE").toLocalDate());
            coupon.setEndDate(rs.getDate("END_DATE").toLocalDate());
            coupon.setAmount(rs.getInt("AMOUNT"));
            coupon.setPrice(rs.getInt("PRICE"));
            coupon.setImage(rs.getString("IMAGE"));
            return coupon;
        } catch (SQLException e) {
            throw new DAOException("can't get coupon", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }
}
