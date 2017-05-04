package pt.upa.transporter.ws;

import org.junit.*;
import pt.upa.transporter.ws.domain.Manager;

import static org.junit.Assert.*;

/**
 *  Unit Test example
 *  
 *  Invoked by Maven in the "test" life-cycle phase
 *  If necessary, should invoke "mock" remote servers 
 */
public class ServerTest {

    private static TransporterPortType _serviceEven;
    private static TransporterPortType _serviceOdd;

    protected TransporterPortType getEvenService() {
        return ServerTest._serviceEven;
    }



    protected TransporterPortType getOddService() {
        return ServerTest._serviceOdd;
    }

    @BeforeClass
    public static void setUpOneTime() {
        _serviceEven = new TransporterPort("UpaTransporter2");
        _serviceOdd = new TransporterPort("UpaTransporter1");
    }

    @AfterClass
    public static void tearDownOneTime() {
        _serviceEven = null;
        _serviceOdd = null;
    }

    @Before
    public void superSetUp(){
    }

    @After
    public void tearDown() {
        Manager.getInstance().clearJobs();
        //Manager.getInstance().getUserList().clear();
    }
    @Test
    public void pingSuccess() throws Exception{
        _serviceEven.ping("yaay");
        _serviceOdd.ping("yaay");
    }

}