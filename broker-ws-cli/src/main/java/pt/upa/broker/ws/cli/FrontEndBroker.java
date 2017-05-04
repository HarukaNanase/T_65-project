package pt.upa.broker.ws.cli;
import pt.upa.broker.ws.*;
import pt.upa.broker.ws.exception.BrokerClientException;
import javax.xml.ws.WebServiceException;
import java.util.List;

/**
 * Created by lolstorm on 10/05/16.
 */

public class FrontEndBroker {
    BrokerClient client = null;

    public boolean isVerbose() {
        return  client.isVerbose();
    }

    public void setVerbose(boolean verbose) {
        client.setVerbose(verbose);
    }

    /**
     * constructor with provided web service URL
     */
    public FrontEndBroker(String wsURL) throws BrokerClientException {
        client = new BrokerClient(wsURL);
    }

    /**
     * constructor with provided UDDI location and name
     */
    public FrontEndBroker(String uddiURL, String wsName) throws BrokerClientException {
        client = new BrokerClient(uddiURL, wsName);
    }

    public String ping(String name) {
        try{
            return client.ping(name);
        }
        catch(WebServiceException e) {
            System.out.println("Primary Server got an error: trying to reconnect to backup");
            reconnect();
            return client.ping(name);
        }
    }

    public String requestTransport(String origin, String destination, int price)
            throws InvalidPriceFault_Exception, UnavailableTransportFault_Exception,
            UnavailableTransportPriceFault_Exception, UnknownLocationFault_Exception {
        try{
            return client.requestTransport(origin, destination, price);
        }
        catch(WebServiceException e) {
            System.out.println("Primary Server got an error: trying to reconnect to backup");
            reconnect();
            return client.requestTransport(origin, destination, price);
        }
    }

    public TransportView viewTransport(String id) throws UnknownTransportFault_Exception {
        try {
            return client.viewTransport(id);
        } catch (WebServiceException e) {
            System.out.println("Primary Server got an error: trying to reconnect to backup");
            reconnect();
            return client.viewTransport(id);
        }
    }

    public List<TransportView> listTransports() {
        try {
            return client.listTransports();
        }
        catch (WebServiceException e) {
            System.out.println("Primary Server got an error: trying to reconnect to backup");
            reconnect();
            return client.listTransports();
        }
    }

    public void addReplicatedTransport(TransportView transport) {
        try {
            client.addReplicatedTransport(transport);
        }catch(WebServiceException e) {
            System.out.println("Primary Server got an error: trying to reconnect to backup");
            reconnect();
            client.addReplicatedTransport(transport);
        }
    }


    public void clearTransports() {
        try {
            client.clearTransports();
        } catch (WebServiceException e) {
            System.out.println("Primary Server got an error: trying to reconnect to backup");
            reconnect();
            client.clearTransports();
        }
    }

    public void reconnect() {
        client.reconnect();
    }
}

