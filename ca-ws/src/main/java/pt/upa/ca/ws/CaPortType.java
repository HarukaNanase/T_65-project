package pt.upa.ca.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by lolstorm on 11/05/16.
 */
@WebService
public interface CaPortType {
    @WebMethod
    public String ping(String name);

    @WebMethod
    public String requestCertificate(String entity);

}
