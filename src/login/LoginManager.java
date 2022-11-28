package login;

import exeptions.LoginException;
import facades.AdminFacade;
import facades.ClientFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;



public class LoginManager {
    public static LoginManager instance = new LoginManager();

    public static LoginManager getInstance() {
        return instance;
    }

    /**
     * check if the client type and password and email is correct
     * @param email the client email
     * @param password the client password
     * @param clientType the client type
     * @return client facade
     */
    public ClientFacade login(String email,String password,ClientType clientType) throws LoginException {
        ClientFacade clientFacade;
        switch (clientType){
            case COMPANY:
                System.out.println("COMPANY");
                 clientFacade=new CompanyFacade();
                 if(clientFacade.login(email,password))
                     return clientFacade;
                break;
            case CUSTOMER:
                System.out.println("CUSTOMER");
                clientFacade=new CustomerFacade();
                if(clientFacade.login(email,password))
                    return clientFacade;
                break;
            case ADMINISTRATOR:
                System.out.println("ADMINISTRATOR");
                clientFacade=new AdminFacade();
                if(clientFacade.login(email,password))
                    return clientFacade;
                break;
        }
        throw new LoginException("your email or password or client type is not legal ");
    }
}
