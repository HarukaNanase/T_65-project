package pt.upa.broker.ws;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;

import javax.xml.registry.JAXRException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceException;
import java.util.*;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.broker.ws.exception.BrokerWSException;

/**
 * Created by lolstorm on 25/03/16.
 */
public class BrokerWrapper {
    private String _uddiUrl;
    private String _name;
    private String _url;
    private BrokerPortType _port;
    private UDDINaming _uddiNaming;
    private Endpoint _endpoint;
    private BrokerPortType port3;
    /**
     * Initiates the main constants of the server.
     * @param uddiUrl
     * @param name
     * @param url
     */
    public BrokerWrapper(String uddiUrl, String name, String url) {
        _uddiUrl = uddiUrl;
        _name = name;
        _url = url;
    }

    public void setNetUDDI(UDDINaming uddi) { this._uddiNaming = uddi;}
    public UDDINaming get_uddiNaming() { return this._uddiNaming;}
    /**
     * Creates the endpoint instance and publish to Uddi.
     */
    public void createEndpoint() throws JAXRException, Exception {

        _port = new BrokerPort(_uddiUrl, _name);
        _endpoint = Endpoint.create(_port);
        System.out.printf("Starting %s%n", _url);
        _endpoint.publish(_url);
        _uddiNaming = new UDDINaming(_uddiUrl);
        _uddiNaming.unbind("UpaBroker0");
        rebind();

    }

    /**
     *  Handles shut down of the server. Stops the Web Service.
     */
    public void tearDown() {
        try {
            if (_endpoint != null) {
                // stop endpoint
                _endpoint.stop();
                System.out.printf("Stopped %s%n", _url);
            }
            if (_uddiNaming != null) {
                // delete from UDDI
                unbind();
                System.out.printf("Deleted '%s' from UDDI%n", _name);
            }
        } catch (Exception e) {
            System.out.printf("Caught exception when deleting: %s%n", e);
        }
    }

    public BrokerPortType lookUp(String wsName) throws JAXRException {
        UDDINaming uddiNaming = new UDDINaming(_uddiUrl);
        System.out.printf("Looking for '%s'%n", wsName);
        String endpointAddress = uddiNaming.lookup(wsName);
        if (endpointAddress == null) {
            System.out.println("Not found!");
            return null;
        } else {
            System.out.printf("Found %s%n", endpointAddress);
        }

        System.out.println("Creating stub ...");
        BrokerService service = new BrokerService();
        BrokerPortType port3 = service.getBrokerPort();

        System.out.println("Setting endpoint address ...");
        BindingProvider bindingProvider = (BindingProvider) port3;
        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
        //Receive Timeout
        int connectionTimeout = 5000;
        final List<String> CONN_TIME_PROPS = new ArrayList<String>();
        CONN_TIME_PROPS.add("com.sun.xml.ws.connect.timeout");
        CONN_TIME_PROPS.add("com.sun.xml.internal.ws.connect.timeout");
        CONN_TIME_PROPS.add("javax.xml.ws.client.connectionTimeout");
        for (String propName : CONN_TIME_PROPS)
            requestContext.put(propName, connectionTimeout);

        //Receive Timeout
        int receiveTimeout = 5000;
        final List<String> RECV_TIME_PROPS = new ArrayList<String>();
        RECV_TIME_PROPS.add("com.sun.xml.ws.request.timeout");
        RECV_TIME_PROPS.add("com.sun.xml.internal.ws.request.timeout");
        RECV_TIME_PROPS.add("javax.xml.ws.client.receiveTimeout");
        for (String propName : RECV_TIME_PROPS)
            requestContext.put(propName, receiveTimeout);
        return port3;
    }

    public boolean alternativeLookUp() throws JAXRException {
        if(_name.equals("UpaBroker0")) {
            BrokerPortType b = lookUp("UpaBroker3");
            if (b != null)
                b.ping("Start Tasks");
            else
                return false;
        }
        return true;
    }

    /**
     * Calls the rebind method of UDDI.
     * @throws JAXRException
     */
    public void rebind() throws JAXRException {
        _uddiNaming.rebind(_name, _url);
    }

    /**
     * Calls the unbind method of UDDI.
     * @throws JAXRException
     */
    public void unbind() throws JAXRException {
        _uddiNaming.unbind(_name);
    }

    /**
     * Method that handles server setup, connection waiting and server shutdown.
     */
    public void run() {
        try {
            if(!alternativeLookUp()) {
                throw new BrokerWSException("Backup broker server is not running.");
            }
            createEndpoint();
            System.out.println("Awaiting connections");
            System.out.println("Press enter to shutdown");
            System.in.read();

        } catch (Exception e) {
            System.out.printf("Caught exception: %s%n", e.getMessage());
        }
        finally {
            tearDown();
        }
        System.out.println("Server is shut down.");
    }


}
