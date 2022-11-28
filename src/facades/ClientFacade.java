package facades;

import connectionsAndDaos.CompaniesDBDAO;
import connectionsAndDaos.CouponsDBDAO;
import connectionsAndDaos.CustomersDBDAO;

public abstract class ClientFacade {
    protected CompaniesDBDAO companiesDBDAO=new CompaniesDBDAO();
    protected CustomersDBDAO customersDBDAO=new CustomersDBDAO();
    protected CouponsDBDAO couponsDBDAO=new CouponsDBDAO();

    public boolean login(String email,String password){
        return  false;
    }
}
