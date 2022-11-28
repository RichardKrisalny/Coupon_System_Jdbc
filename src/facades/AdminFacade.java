package facades;

import exeptions.ClientFacadeException;
import java_beans.Company;
import java_beans.Customer;

import java.util.List;
import java.util.Objects;

public class AdminFacade extends ClientFacade{
   private List<Company>companies=companiesDBDAO.getAll();
   private List<Customer>customers=customersDBDAO.getAll();
    public static final String EMAIL="admin@admin.com";
    public static final String PASSWORD="admin";

    /**
     * check if the admin email and password is correct
     * @param email the admin email
     * @param password the admin password
     * @return true if the email and password correct else returned false
     */
    @Override
    public boolean login(String email, String password) {
        return (EMAIL.equals(email) && PASSWORD.equals(password));
    }

    /**
     * check if company email or name exist in database.
     * if the not exist ,add the company
     * @param company the company to add
     */
    public void addCompany(Company company) throws ClientFacadeException {
        for (int i = 0; i < companies.size(); i++) {
            if(Objects.equals(companies.get(i).getEmail(), company.getEmail()) ||
                    Objects.equals(companies.get(i).getName(), company.getName())){
                throw new ClientFacadeException("the company email or name is in the date base");
            }
        }
        companiesDBDAO.add(company);
        companies=companiesDBDAO.getAll();
    }

    /**
     * find the company in database and update it
     * @param company the company to update
     */
    public void updateCompany(Company company) throws ClientFacadeException {
        if (Objects.equals(getCompany(company.getId()).getName(), company.getName())) {
            companiesDBDAO.update(company);
            companies=companiesDBDAO.getAll();
        }
        else
            throw new ClientFacadeException("the company name and id should be same. or the company id not found");
    }

    /**
     * delete company from database
     * @param companyID the company to delete
     */
    public void deleteCompany(int companyID){
            if(getCompany(companyID)!=null)
                companiesDBDAO.delete(companyID);
        companies=companiesDBDAO.getAll();
        }

    /**
     * get all companies from database
     * @return list of companies
     */
    public List<Company> getAllCompanies(){
        List<Company>companiesToReturn=companiesDBDAO.getAll();
        for (int i = 0; i < companiesToReturn.size(); i++) {
            companiesToReturn.get(i).setCoupons(couponsDBDAO.getAllForCompany(companiesToReturn.get(i).getId()));
        }
        return companiesToReturn;
    }

    /**
     * get specific company
     * @param companyID company id
     * @return the company from database (with company coupon's)
     */
    public Company getOneCompany(int companyID){
        Company company =getCompany(companyID);
        company.setCoupons(couponsDBDAO.getAllForCompany(companyID));
        return company;
    }

    /**
     * add customer to the database
     * @param customer the customer to add
     */
    public void addCustomer(Customer customer) throws ClientFacadeException {
            for (int i = 0; i < customers.size(); i++) {
                if(Objects.equals(customers.get(i).getEmail(), customer.getEmail())){
                    throw new ClientFacadeException("the customer email is in the date base");
                }
            }
        customersDBDAO.add(customer);
        customers=customersDBDAO.getAll();
    }

    /**
     * update customer in database
     * @param customer the customer to update
     */
    public void updateCustomer(Customer customer) throws ClientFacadeException {
        if(getCustomer(customer.getId()).getId()==customer.getId()) {
            customersDBDAO.update(customer);
            customers=customersDBDAO.getAll();
        }
        else
        throw new ClientFacadeException("the customer id can't changed");
    }

    /**
     * delete customer from database
     * @param customerID the customer id
     */
    public void deleteCustomer(int customerID){
        if(getCustomer(customerID)!=null)
        customersDBDAO.delete(customerID);
        customers=customersDBDAO.getAll();
    }

    /**
     * get all customers from database
     * @return list of customers
     */
    public List<Customer> getAllCustomers(){
        List<Customer>customersToReturn=customersDBDAO.getAll();
        for (int i = 0; i < customersToReturn.size(); i++) {
            customersToReturn.get(i).setCoupons(couponsDBDAO.getAllForCustomer(customersToReturn.get(i).getId()));
        }
        return customersToReturn;
    }

    /**
     * get specific customer from database
     * @param customerID the customer id
     * @return the specific customer
     */
    public Customer getOneCustomer(int customerID){
        Customer customer=getCustomer(customerID);
        customer.setCoupons(couponsDBDAO.getAllForCustomer(customerID));
        return customer;
    }


    /**
     * find company in database
     * @param companyID the company id
     * @return company
     */
    private  Company getCompany(int companyID) throws ClientFacadeException {
        for (int i = 0; i <companies.size(); i++) {
            if(companies.get(i).getId()==companyID){
                return companies.get(i);
            }
          }
        throw new ClientFacadeException("can't find the company");
    }
    /**
     * find customer in database
     * @param customerID the customer id
     * @return customer
     */
    private  Customer getCustomer(int customerID) throws ClientFacadeException {
        for (int i = 0; i <customers.size(); i++) {
            if(customers.get(i).getId()==customerID){
                return customers.get(i);
            }
        }
        throw new ClientFacadeException("can't find the customer");
    }
}
