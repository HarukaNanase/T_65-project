package pt.upa.broker.ws;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.junit.*;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.broker.ws.domain.Manager;
import pt.upa.transporter.ws.JobStateView;
import pt.upa.transporter.ws.JobView;
import pt.upa.transporter.ws.TransporterPort;
import pt.upa.transporter.ws.TransporterWrapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *  Unit Test example
 *  
 *  Invoked by Maven in the "test" life-cycle phase
 *  If necessary, should invoke "mock" remote servers 
 */
public class ServerTest {

    protected static int PRICE_UPPER_LIMIT = 100;
    protected static int PRICE_SMALLEST_LIMIT = 10;

    protected static int INVALID_PRICE = -1;
    protected static int ZERO_PRICE = 0;
    protected static int UNITARY_PRICE = 1;

    protected static int ODD_INCREMENT = 1;
    protected static int EVEN_INCREMENT = 2;

    protected static final String SOUTH_1 = "Beja";
    protected static final String SOUTH_2 = "Portalegre";

    protected static final String CENTER_1 = "Lisboa";
    protected static final String CENTER_2 = "Coimbra";

    protected static final String NORTH_1 = "Porto";
    protected static final String NORTH_2 = "Braga";

    protected static final String EMPTY_STRING = "";

    protected static final String _uddiURL = "http://localhost:9090";
    protected static final String _wsName = "UpaTransporter%";
    protected static final String _wsNameBroker = "UpaBroker0";
    protected static final String _URL = "http://localhost:8080/broker-ws/endpoint";
    protected static final String _bwsURL1 = "http://localhost:8081/transporter-ws/endpoint";
    protected static final String _bwsURL2 = "http://localhost:8082/transporter-ws/endpoint";
    protected static final List<String> _endpoints = new ArrayList<String>();
    protected static final int DELAY_LOWER = 1000; // = 1 second
    protected static final int DELAY_UPPER = 5000; // = 5 seconds
    protected static final int TENTH_OF_SECOND = 100;

    @Mocked Manager manager;

    public void mockedBackUpServer() {

    }

    @BeforeClass
    public static void setUpOneTime() throws Exception {
        _endpoints.add(_bwsURL1);
        _endpoints.add(_bwsURL2);
        //_even = new TransporterWrapper(_uddiURL, "UpaTransporter1", _bwsURL1);
        //_odd = new TransporterWrapper(_uddiURL, "UpaTransporter2", _bwsURL2);


        //_service = new BrokerPort(_uddiURL);

    }

    public JobView createJobView(String jobIdentifier, String jobOrigin, String jobDestination, String companyName,
                                 int jobPrice, JobStateView jsv) {
        JobView j = new JobView();
        j.setJobIdentifier(jobIdentifier);
        j.setJobOrigin(jobOrigin);
        j.setJobDestination(jobDestination);
        j.setCompanyName(companyName);
        j.setJobPrice(jobPrice);
        j.setJobState(jsv);
        return j;
    }

    @AfterClass
    public static void tearDownOneTime() {
        _endpoints.clear();

    }

    @Before
    public void superSetUp() {
    }

    @After
    public void tearDown() {
        //bp.clearTransports();
        //_even.tearDown();
        //_odd.tearDown();
    }

}
