package connectionsAndDaos;
import exeptions.DAOException;
import java_beans.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersDBDAO implements MainDAO<Customer>{
    private ConnectionPool connectionPool=ConnectionPool.getInstance();
    private PreparedStatement statement;

    /**
     * check if the customer exist in database
     * @param email the customer email
     * @param password the customer password
     * @return customer id if exist or 0 if not exist
     */
    @Override
    public int isExists(String email, String password) throws DAOException {
        Connection connection= connectionPool.getConnection();
        ResultSet rs;
        String sql="select ID from customers where EMAIL=? AND PASSWORD=?";
        try {
            statement=connection.prepareStatement(sql);
            statement.setString(1,email);
            statement.setString(2,password);
            rs=statement.executeQuery();
            if(rs.next())
                return rs.getInt("ID");
        } catch (SQLException e) {
            throw new DAOException("problem in method isExists",e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return 0;
    }

    /**
     * add a customer to database
     * @param customer the customer to add
     */
    @Override
    public void add(Customer customer) throws DAOException {
        Connection connection= connectionPool.getConnection();
        String sql="insert into customers values(0,?,?,?,?);";
        try {
            statement=connection.prepareStatement(sql);
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            statement.setString(3,customer.getEmail());
            statement.setString(4,customer.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't add customer",e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * update customer to the database
     * @param object the customer to update
     */
    @Override
    public void update(Customer object) throws DAOException {
        Connection connection= connectionPool.getConnection();
        String sql="update customers set FIRST_NAME=?,LAST_NAME=?,EMAIL=?,PASSWORD=? where ID=?;";
        try {
            statement=connection.prepareStatement(sql);
            statement.setString(1,object.getFirstName());
            statement.setString(2,object.getLastName());
            statement.setString(3,object.getEmail());
            statement.setString(4,object.getPassword());
            statement.setInt(5,object.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't update customer",e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * delete customer from database
     * @param customerID the customer id
     */
    @Override
    public void delete(int customerID) throws DAOException {
        Connection connection= connectionPool.getConnection();
        String sql="delete from customers where ID=?;";
        try {
            statement=connection.prepareStatement(sql);
            statement.setInt(1,customerID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't delete customer",e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * get all customers from database
     * @return list of customers
     */
    @Override
    public List<Customer> getAll() throws DAOException {
        Connection connection= connectionPool.getConnection();
        ResultSet rs;
        List<Customer>customerToReturn=new ArrayList<>();
        String sql="select * from customers;";
        try {
            statement=connection.prepareStatement(sql);
            rs=statement.executeQuery();
            while (rs.next()){
                Customer customer=new Customer();
                customer.setId(rs.getInt("ID"));
                customer.setEmail(rs.getString("EMAIL"));
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setLastName(rs.getString("LAST_NAME"));
                customer.setPassword(rs.getString("PASSWORD"));
                customerToReturn.add(customer);
            }
        } catch (SQLException e) {
            throw new DAOException("can't get all customer",e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return customerToReturn;
    }

    /**
     * get specific customer from database
     * @param custometID the customer id
     * @return customer
     */
    @Override
    public Customer getOne(int custometID) throws DAOException {
        Connection connection= connectionPool.getConnection();
        Customer customer=new Customer();
        ResultSet rs;
        String sql="select * from customers where ID=?";
        try {
            statement=connection.prepareStatement(sql);
            statement.setInt(1,custometID);
            rs=statement.executeQuery();
            rs.next();
            customer.setId(rs.getInt("ID"));
            customer.setEmail(rs.getString("EMAIL"));
            customer.setLastName(rs.getString("LAST_NAME"));
            customer.setFirstName(rs.getString("FIRST_NAME"));
            customer.setPassword(rs.getString("PASSWORD"));
            return customer;
        } catch (SQLException e) {
            throw new DAOException("can't get customer",e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }
}
