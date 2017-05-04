package pt.upa.broker.ws.domain;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.broker.ws.BrokerPortType;
import pt.upa.broker.ws.BrokerWrapper;
import pt.upa.broker.ws.TransportView;

import java.util.*;

import pt.upa.broker.ws.exception.BrokerWSException;
import pt.upa.transporter.ws.cli.TransporterClient;
import pt.upa.transporter.ws.exception.TransporterClientException;

import javax.xml.registry.JAXRException;

/**
 * Created by lolstorm on 24/03/16.
 */
public class Manager {

    private String _keyName;
    private static Manager instance;
    public BrokerPortType alternativeBroker;
    private ArrayList <TransporterClient> _transportersClient;
    private ArrayList<TransportView> _transportViews;
    public Boolean _primaryService = false;
    public String _uddiURL;
    public String _wsName;

    private static final Set<String> NORTH= new HashSet<>(Arrays.asList(new String [] {"Porto", "Braga", "Viana do " +
            "Castelo", "Vila Real", "Bragança"}));
    private static final Set<String> CENTER= new HashSet<>(Arrays.asList(new String [] {"Lisboa", "Leiria", "Santarem",
            "Castelo Branco", "Coimbra", "Aveiro",
            "Viseu", "Guarda"}));
    private static final Set<String> SOUTH= new HashSet<>(Arrays.asList(new String [] {"Setubal", "Evora",
            "Portalegre", "Beja", "Faro"}));

    /**
     * Creates the singleton Manager.
     */
    private Manager() {
        _transportViews = new ArrayList<TransportView>();
        _transportersClient = new ArrayList<TransporterClient>();
    }

    public void setKeyName(String keyName) {
        _keyName = keyName;
    }

    public String getKeyName() {
        return _keyName;
    }


    /**
     * Gets the instance of the current Manager.
     * @return returns an instance of the application Manager.
     */
    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public List<String> initUddi(String uddiUrl, String wsName) throws BrokerWSException {
        try {
            _uddiURL = uddiUrl;
            List<String> endpointAddress = new ArrayList<String>();
            UDDINaming uddiNaming = new UDDINaming(uddiUrl);
            endpointAddress.addAll(uddiNaming.list(wsName));
            return endpointAddress;
        }
        catch (JAXRException e) {
            String msg = String.format("Failed to lookup on UDDI at %s!", uddiUrl);
            throw new BrokerWSException(msg, e);
        }
    }

    public void initlookup(String uddiUrl, String wsName) throws BrokerWSException {

    }

    public void initUddiServices(String uddiUrl, String wsName) throws BrokerWSException {
        try {

            List<String> endpointAddress = new ArrayList<String>();
            UDDINaming uddiNaming = new UDDINaming(uddiUrl);
            endpointAddress.addAll(uddiNaming.list(wsName));

            for (String s : endpointAddress) {
                TransporterClient temp = new TransporterClient(s);
                Manager.getInstance().addTransporterClient(temp);
            }
            _uddiURL = uddiUrl;
        } catch (TransporterClientException e) {
            String msg = String.format("Error at Transporter Client at %s!", uddiUrl);
            throw new BrokerWSException(msg, e);
        }
        catch (JAXRException e) {
            String msg = String.format("Failed to lookup on UDDI at %s!", uddiUrl);
            throw new BrokerWSException(msg, e);
        }
    }

    public void initAlternativeService(String uddiUrl, String wsName) {
        BrokerWrapper bw = new BrokerWrapper(uddiUrl, wsName, "http://localhost:9090");
        try {
            alternativeBroker = bw.lookUp(wsName);
        } catch (JAXRException e) {
            System.out.printf("Caught exception: %s%n", e);
            e.printStackTrace();
        }
    }
    public void initBrokerAlternative() {
        if (alternativeBroker == null) {
            if (_wsName.equals("UpaBroker0")) {
                initAlternativeService("http://localhost:8083/broker-ws/endpoint", "UpaBroker3");
            }
            else
                initAlternativeService("http://localhost:8080/broker-ws/endpoint", "UpaBroker0");
        }
    }

    /**
     * It returns a list of transport views
     * @return ArrayList<TransportView>
     */
    public ArrayList<TransportView> getTransportViews () {
        return _transportViews;
    }

    /**
     * It returns a list of transport views
     * @return ArrayList<TransportView>
     */
    public ArrayList <TransporterClient> getTransportsClient () {
        return _transportersClient;
    }

    /**
     * Gets a transportView by origin and destination.
     * @param id String represents the id of the transport.
     * @return TransportView returns the transport if exists.
     */
    public TransportView getTransportView(String id) {
        if (id == null)
            return null;
        for (TransportView t: _transportViews) {
            if (t.getId().equals(id))
                return t;
        }
        return null;
    }

    public void addTransporterClient(TransporterClient tc) {
        _transportersClient.add(tc);
    }

    public void addTransporterView(TransportView tv) {
        _transportViews.add(tv);
    }

    /**
     * Removes the TransportView from application
     * @param id represents the identification of the transport.
     */
    public void removeTransportView(String id) {
        TransportView t = getTransportView(id);
        if (t == null)
            _transportViews.remove(t);
    }

    public void clearTransportViews() {
        _transportViews.clear();
    }

    public void clearTransportClient() {
        _transportersClient.clear();
    }



}
