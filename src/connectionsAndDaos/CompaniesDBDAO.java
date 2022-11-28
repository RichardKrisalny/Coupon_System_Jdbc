package connectionsAndDaos;

import exeptions.DAOException;
import java_beans.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompaniesDBDAO implements MainDAO<Company> {
    private PreparedStatement statement;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * check if the company exists in a database
     *
     * @param email    the company email
     * @param password the company password
     * @return the id of company if exist or 0 if not exist
     */
    @Override
    public int isExists(String email, String password) throws DAOException {
        Connection connection = connectionPool.getConnection();
        ResultSet rs;
        String sql = "select ID from companies where EMAIL=? AND PASSWORD=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            rs = statement.executeQuery();
            if (rs.next())
                return rs.getInt("ID");
        } catch (SQLException e) {
            throw new DAOException("in method isExists ", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return 0;
    }

    /**
     * add company in to database
     *
     * @param company the company to add
     */
    @Override
    public void add(Company company) throws DAOException {
        Connection connection = connectionPool.getConnection();
        String sql = "insert into COMPANIES values(0,?,?,?);";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't add company", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * update company in the database
     *
     * @param company the company to update
     */
    @Override
    public void update(Company company) throws DAOException {
        Connection connection = connectionPool.getConnection();
        String sql = "update COMPANIES set NAME=?,EMAIL=?,PASSWORD=? where ID=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.setInt(4, company.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't update company", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * delete company from database
     *
     * @param companyID the company id to delete
     */
    @Override
    public void delete(int companyID) throws DAOException {
        Connection connection = connectionPool.getConnection();
        String sql = "delete from COMPANIES where ID=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, companyID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't delete company", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * get all companies in database
     *
     * @return a list of companies in database
     */
    @Override
    public ArrayList<Company> getAll() throws DAOException {
        Connection connection = connectionPool.getConnection();
        ResultSet rs;
        ArrayList<Company> companiesToReturn = new ArrayList<>();
        String sql = "select * from COMPANIES";
        try {
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("ID"));
                company.setEmail(rs.getString("EMAIL"));
                company.setName(rs.getString("NAME"));
                company.setPassword(rs.getString("PASSWORD"));
                companiesToReturn.add(company);
            }
        } catch (SQLException e) {
            throw new DAOException("can't get all companies", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return companiesToReturn;
    }

    /**
     * get specific company from database
     *
     * @param companyID the company id
     * @return the company from database
     */
    @Override
    public Company getOne(int companyID) throws DAOException {
        Connection connection = connectionPool.getConnection();
        Company company = new Company();
        ResultSet rs;
        String sql = "select * from COMPANIES where ID=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, companyID);
            rs = statement.executeQuery();
            rs.next();
            company.setId(rs.getInt("ID"));
            company.setEmail(rs.getString("EMAIL"));
            company.setName(rs.getString("NAME"));
            company.setPassword(rs.getString("PASSWORD"));
            return company;
        } catch (SQLException e) {
            throw new DAOException("can't get company", e);
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }
}
