package pt.upa.broker.ws;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.broker.ws.domain.Manager;
import pt.upa.broker.ws.exception.BrokerWSException;
import pt.upa.broker.ws.handler.BrokerHandler;
import pt.upa.transporter.ws.*;
import pt.upa.transporter.ws.cli.TransporterClient;

import javax.annotation.Resource;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.xml.registry.JAXRException;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


@WebService(
        endpointInterface="pt.upa.broker.ws.BrokerPortType",
        wsdlLocation= "broker.2_0.wsdl",
        name="BrokerPortType",
        portName="BrokerPort",
        targetNamespace="http://ws.broker.upa.pt/",
        serviceName="BrokerService"
)
public class BrokerPort implements BrokerPortType {

    public BrokerPort() {
        super();
        Manager.getInstance();
    }

    public BrokerPort(String uddiURL, String wsName) throws BrokerWSException {
        this();

        Manager m = Manager.getInstance();
        m._wsName = wsName;
        if (wsName.equals("UpaBroker0"))
            m._primaryService = true;
        else {
            System.out.println("Back up: Server " + wsName + " running!");
        }
        Manager.getInstance().initUddiServices(uddiURL, "UpaTransporter%");

    }


    /**
     * Represents broker ping operation.
     * @param name
     * @return
     */
    @Override
    public String ping(String name) {
        Manager m = Manager.getInstance();
        System.out.println(name);
        if(name.equals("Start Tasks")) {
            Timer timer = new Timer();
            TimerTask AreYouAlive = new TimerTask() {
                public void run() {
                    try {
                        m.initBrokerAlternative();
                        String result = m.alternativeBroker.ping("friend");
                    } catch (WebServiceException wse) {
                        //System.out.println("Caught: " + wse);
                        //Throwable cause = wse.getCause();

                        System.out.println("Replace Broker server");
                        try {
                            UDDINaming uddiNaming2 = new UDDINaming(m._uddiURL);
                            uddiNaming2.unbind("UpaBroker3");
                            uddiNaming2.rebind("UpaBroker0", "http://localhost:8083/broker-ws/endpoint");
                            System.out.println("Changing broker port to: " +
                                    "http://localhost:8083/broker-ws/endpoint");

                            timer.cancel();
                            timer.purge();
                        } catch (JAXRException e) {
                            System.out.printf("Caught exception: %s%n", e);
                            e.printStackTrace();

                        }
                    }
                }
            };

            timer.schedule(AreYouAlive, 10000, 10000);
        }
        return "I'm " + name;
    }


  //  public void addReplicatedTransport(TransportView transport){
   //     ;
   // }

    /**
     * Represents a broker request transport operation.
     * @param origin String represents from where you begin the transport.
     * @param destination String represents the destination of the transport.
     * @param price int represents the price of the transport.
     * @return String id representation of the transport.
     * @throws InvalidPriceFault_Exception
     * @throws UnavailableTransportFault_Exception
     * @throws UnavailableTransportPriceFault_Exception
     * @throws UnknownLocationFault_Exception
     */
    @Override
    public String requestTransport(String origin, String destination, int price) throws InvalidPriceFault_Exception, UnavailableTransportFault_Exception, UnavailableTransportPriceFault_Exception, UnknownLocationFault_Exception {
        Manager m = Manager.getInstance();
        List<TransportView> transports = new ArrayList<TransportView>();
        TransportView transport = new TransportView();
        TransporterClient bestTransportC = null;
        JobView job;

        TransportView bookedT = null;
        int bestPrice = price;
        int currentPrice = 0;
        int i = 0;

        if (price > 100) {
            UnavailableTransportFault p = new UnavailableTransportFault();
            p.setOrigin(origin);
            p.setDestination(destination);
            throw new UnavailableTransportFault_Exception("Transports not available", p);
        }

        try {
            for (TransporterClient t: m.getTransportsClient()) {
                transports.add(new TransportView());
                transports.get(i).setOrigin(origin);
                transports.get(i).setDestination(destination);
                transports.get(i).setState(TransportStateView.REQUESTED);
                job = t.requestJob(origin, destination, price);
                if (job == null) {
                    i++;
                    continue;
                }
                if(transports.get(i).getState().equals(TransportStateView.REQUESTED)){
                    currentPrice = job.getJobPrice();
                    transports.get(i).setState(TransportStateView.BUDGETED);
                    transports.get(i).setId(job.getJobIdentifier());
                    transports.get(i).setTransporterCompany(job.getCompanyName());
                    transports.get(i).setOrigin(job.getJobOrigin());
                    transports.get(i).setDestination(job.getJobDestination());
                    transports.get(i).setPrice(currentPrice);
                }

                if (currentPrice <= bestPrice) {
                    bestPrice = currentPrice;
                    transports.get(i).setState(TransportStateView.BOOKED);
                    if(bookedT != null) {
                        //t.decideJob(transports.get(i).getId(), false);
                        bookedT.setState(TransportStateView.FAILED);
                    }
                    bookedT = transports.get(i);
                    bestTransportC = t;
                }
                else {
                    t.decideJob(transports.get(i).getId(), false);
                    transports.get(i).setState(TransportStateView.FAILED);
                }
                i++;
            }

            if (bookedT != null) {
                if (bookedT.getState() == TransportStateView.BOOKED) {
                    bestTransportC.decideJob(bookedT.getId(), true);
                    m.addTransporterView(bookedT);
                    //m.addAllTransportViews(transports);

                    if (m._primaryService) {
                        m.initBrokerAlternative();
                        m.alternativeBroker.addReplicatedTransport(bookedT);
                    }
                    //System.out.println(m.getTransportViews().size());
                    return bookedT.getId();
                }
            }
        }
        catch (BadLocationFault_Exception e){
            UnknownLocationFault l = new UnknownLocationFault();
            l.setLocation(e.getFaultInfo().getLocation());
            throw new UnknownLocationFault_Exception(e.getMessage(), l);
        }

        catch (BadPriceFault_Exception e){
            InvalidPriceFault p = new InvalidPriceFault();
            p.setPrice(e.getFaultInfo().getPrice());
            throw new InvalidPriceFault_Exception(e.getMessage(), p);
        }
        catch (BadJobFault_Exception e) {
            throw new WebServiceException(e.getMessage());
        }
        catch (Exception e) {
            throw new WebServiceException(e.getMessage());
        }
        if(currentPrice > bestPrice) {
            UnavailableTransportPriceFault p = new UnavailableTransportPriceFault();
            p.setBestPriceFound(price);
            throw new UnavailableTransportPriceFault_Exception("No transport for price given", p);
        }

        UnavailableTransportFault p = new UnavailableTransportFault();
        p.setOrigin(origin);
        p.setDestination(destination);
        throw new UnavailableTransportFault_Exception("Transports not available", p);
    }

    /**
     * Represents broker view transport operation.
     * @param id identification of transport.
     * @throws UnknownTransportFault_Exception
     * @return TransportView returns the transport.
     */
    @Override
    public TransportView viewTransport(String id) throws UnknownTransportFault_Exception {
        Manager m = Manager.getInstance();
        TransportView v = m.getTransportView(id);
        if (v != null) {
            for(TransporterClient t: m.getTransportsClient()) {
                JobView job = t.jobStatus(id);
                if(job != null) {
                    v.setId(job.getJobIdentifier());
                    v.setOrigin(job.getJobOrigin());
                    v.setDestination(job.getJobDestination());
                    v.setTransporterCompany(job.getCompanyName());
                    v.setPrice(job.getJobPrice());
                    if(job.getJobState().equals(JobStateView.HEADING))
                        v.setState(TransportStateView.HEADING);
                    else if(job.getJobState().equals(JobStateView.ONGOING))
                        v.setState(TransportStateView.ONGOING);
                    else if(job.getJobState().equals(JobStateView.COMPLETED))
                        v.setState(TransportStateView.COMPLETED);
                    return v;
                }
            }
        }
        UnknownTransportFault t = new UnknownTransportFault();
        t.setId(id);
        throw new UnknownTransportFault_Exception("Doesn't match any transport.",t);
    }

    /**
     * Represents broker list transports operation.
     * @return List<TransportView> returns a list of transports.
     */
    @Override
    public List<TransportView> listTransports() {
        return Manager.getInstance().getTransportViews();
    }

    @Override
    @Oneway
    public void addReplicatedTransport(TransportView transport) {
        Manager m = Manager.getInstance();
        m.getTransportViews().add(transport);
        System.out.println(m.getTransportViews().size()+ "<--");
    }

    /**
     * Represents broker clear transports operation.
     */
    @Override
    public void clearTransports() {
        Manager m = Manager.getInstance();
        if(m._primaryService) {
            m.initBrokerAlternative();
            m.alternativeBroker.clearTransports();
        }
        m.clearTransportViews();

    }

    @Resource
    private WebServiceContext webServiceContext;

    public void setSOAPHeader(String value){
        MessageContext messageContext = webServiceContext.getMessageContext();
        messageContext.put(BrokerHandler.TAG_HEADER, value);
    }

    public void setAckHeader(){
        MessageContext messageContext = webServiceContext.getMessageContext();
        messageContext.put(BrokerHandler.ACK_PROPERTY, BrokerHandler.ACK);
    }
    /*
    public Tag getSOAPHeader(){
        MessageContext messageContext = webServiceContext.getMessageContext();
        String tag = (String) messageContext.get(BrokerHandler.TAG_HEADER);
        return new Tag(tag);
    }
    */
}
