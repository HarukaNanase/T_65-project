package pt.upa.transporter.ws;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;

import javax.xml.registry.JAXRException;
import javax.xml.ws.Endpoint;

/**
 * Created by nucleardannyd on 12/04/16.
 */
public class TransporterWrapper {
    private String _uddiUrl;
    private String _name;
    private String _url;
    private TransporterPortType _port;
    private UDDINaming _uddiNaming;
    private Endpoint _endpoint;

    /**
     * Initiates the main constants of the server.
     * @param uddiUrl
     * @param name
     * @param url
     */
    public TransporterWrapper(String uddiUrl, String name, String url) {
        _uddiUrl = uddiUrl;
        _name = name;
        _url = url;
    }


    /**
     * Creates the endpoint instance and publish to Uddi.
     */
    public void createEndpoint() throws JAXRException {
        _port = new TransporterPort(_name);
        _endpoint = Endpoint.create(_port);
        System.out.printf("Starting %s%n", _url);
        _endpoint.publish(_url);
        _uddiNaming = new UDDINaming(_uddiUrl);
        rebind();
    }


    /**
     *  Handles shut down of the server. Stops the Web Service.
     */
    public void tearDown() {
        try {
            if (_endpoint != null) {
                _endpoint.stop();
                System.out.printf("Stopped %s%n", _url);
            }
            if (_uddiNaming != null) {
                unbind();
                System.out.printf("Deleted '%s' from UDDI%n", _name);
            }
        } catch (Exception e) {
            System.out.printf("Exception caught when deleting: %s%n", e);
        }
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
            createEndpoint();
            System.out.println("Awaiting connections");
            System.out.println("Press enter to shutdown");
            System.in.read();
        } catch (Exception e) {
            System.out.printf("Exception caught: %s%n", e);
        } finally {
            tearDown();
        }
        System.out.println("Server is shut down.");
    }

}
