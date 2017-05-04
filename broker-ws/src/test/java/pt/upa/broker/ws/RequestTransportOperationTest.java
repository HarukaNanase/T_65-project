package pt.upa.broker.ws;

    import mockit.Expectations;
    import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.*;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.transporter.ws.*;
import pt.upa.transporter.ws.cli.TransporterClient;

    import static javafx.beans.binding.Bindings.when;
    import static org.junit.Assert.assertEquals;
    import static org.junit.Assert.assertTrue;


/**
 * Created by nucleardannyd on 13/04/16.
 */
public class RequestTransportOperationTest extends ServerTest {

    private BrokerPortType _port;
    private static String origin = "Lisboa";
    private static String destination = "Porto";

    // tests


    // ----------- Zero Price --------------

    @Test
    // not tested for evaluation as stated in project Q&A:
    // http://disciplinas.tecnico.ulisboa.pt/leic-sod/2015-2016/labs/proj/faq.html
    public void testRequestTransportSouthZeroPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(0);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(SOUTH_1, CENTER_1, ZERO_PRICE);
        TransportView tv = bp.viewTransport(id);
        assertEquals(ZERO_PRICE, tv.getPrice().intValue());
    }

    @Test
    // not tested for evaluation as stated in project Q&A:
    // http://disciplinas.tecnico.ulisboa.pt/leic-sod/2015-2016/labs/proj/faq.html
    public void testRequestTransportCenterZeroPrice2(@Mocked final UDDINaming uddiNaming, @Mocked final
    TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(0);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(CENTER_1, CENTER_2, ZERO_PRICE);
        TransportView tv = bp.viewTransport(id);
        assertEquals(ZERO_PRICE, tv.getPrice().intValue());
    }

    @Test
    // not tested for evaluation as stated in project Q&A:
    // http://disciplinas.tecnico.ulisboa.pt/leic-sod/2015-2016/labs/proj/faq.html
    public void testRequestTransportNorthZeroPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(0);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(CENTER_1, NORTH_1, ZERO_PRICE);
        TransportView tv = bp.viewTransport(id);
        assertEquals(ZERO_PRICE, tv.getPrice().intValue());
    }

    // ----------- Unitary Price --------------

    @Test
    // not tested for evaluation as stated in project Q&A:
    // http://disciplinas.tecnico.ulisboa.pt/leic-sod/2015-2016/labs/proj/faq.html
    public void testRequestTransportSouthUnitaryPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(0);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(SOUTH_1, CENTER_1, UNITARY_PRICE);
        TransportView tv = bp.viewTransport(id);
        assertEquals(ZERO_PRICE, tv.getPrice().intValue());
    }

    @Test
    // not tested for evaluation as stated in project Q&A:
    // http://disciplinas.tecnico.ulisboa.pt/leic-sod/2015-2016/labs/proj/faq.html
    public void testRequestTransportCenterUnitaryPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(0);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(CENTER_1, CENTER_2, UNITARY_PRICE);
        TransportView tv = bp.viewTransport(id);
        assertEquals(ZERO_PRICE, tv.getPrice().intValue());
    }

    @Test
    // not tested for evaluation as stated in project Q&A:
    // http://disciplinas.tecnico.ulisboa.pt/leic-sod/2015-2016/labs/proj/faq.html
    public void testRequestTransportNorthUnitaryPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(0);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(CENTER_1, NORTH_1, UNITARY_PRICE);
        TransportView tv = bp.viewTransport(id);
        assertEquals(ZERO_PRICE, tv.getPrice().intValue());
    }

    // ----------- Above upper limit price (> 100) --------------

    // No proposals expected

    @Test(expected = UnavailableTransportFault_Exception.class)
    public void testRequestTransportSouthAboveUpperLimitPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, PRICE_UPPER_LIMIT + ODD_INCREMENT);
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.requestTransport(SOUTH_1, CENTER_1, PRICE_UPPER_LIMIT + ODD_INCREMENT);
    }

    @Test(expected = UnavailableTransportFault_Exception.class)
    public void testRequestTransportCenterAboveUpperLimitPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, PRICE_UPPER_LIMIT + ODD_INCREMENT);
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.requestTransport(CENTER_1, CENTER_2, PRICE_UPPER_LIMIT + ODD_INCREMENT);
    }

    @Test(expected = UnavailableTransportFault_Exception.class)
    public void testRequestTransportNorthAboveUpperLimitPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, PRICE_UPPER_LIMIT + ODD_INCREMENT);
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.requestTransport(CENTER_1, NORTH_1, PRICE_UPPER_LIMIT + ODD_INCREMENT);
    }


    // ----------- Below Smallest price (<=10) --------------

    // Odd Transporters operate in South and Center (e.g. UpaTransporter1)
    // Even Transporters operate in Center and North (e.g. UpaTransporter2)

    // If a request is issued in the South, only odd transporters respond
    // (e.g. UpaTransporter1 ONLY)

    // If a request is issued in the Center, ALL transporters should respond
    // (e.g. UpaTransporter1 AND UpaTransporter2)

    // If a request is issued in the North, only even transporters respond
    // (e.g. UpaTransporter2 ONLY)

    @Test
    public void testRequestTransportSouthBelowSmallestPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(3);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(SOUTH_1, CENTER_1, PRICE_SMALLEST_LIMIT);
        TransportView tv = bp.viewTransport(id);
        final int price = tv.getPrice().intValue();
        assertTrue(price >= ZERO_PRICE && price < PRICE_SMALLEST_LIMIT);
    }

    @Test
    public void testRequestTransportCenterBelowSmallestPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(3);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(CENTER_1, CENTER_2, PRICE_SMALLEST_LIMIT);
        TransportView tv = bp.viewTransport(id);
        final int price = tv.getPrice().intValue();
        assertTrue(price >= ZERO_PRICE && price < PRICE_SMALLEST_LIMIT);
    }

    @Test
    public void testRequestTransportNorthBelowSmallestPrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(3);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(CENTER_1, NORTH_1, PRICE_SMALLEST_LIMIT);
        TransportView tv = bp.viewTransport(id);
        final int price = tv.getPrice().intValue();
        assertTrue(price >= ZERO_PRICE && price < PRICE_SMALLEST_LIMIT);
    }


    // ----------- Below upper limit price (>10 && <=100) --------------

    @Test
    public void testRequestTransportSouthOddPriceBelowUpperLimit(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(3);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(SOUTH_1, CENTER_1, PRICE_SMALLEST_LIMIT + ODD_INCREMENT);
        TransportView tv = bp.viewTransport(id);
        final int price = tv.getPrice().intValue();
        assertTrue(price >= ZERO_PRICE && price < PRICE_SMALLEST_LIMIT + ODD_INCREMENT);
        // should also check that it is an odd transporter (e.g. UpaTransporter1)
    }

    //@Test(expected = UnavailableTransportPriceFault_Exception.class)
    public void testRequestTransportSouthEvenPriceBelowUpperLimit(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                result = null;
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(SOUTH_1, CENTER_1, PRICE_SMALLEST_LIMIT + EVEN_INCREMENT);
    }

    @Test
    public void testRequestTransportCenterOddPriceBelowUpperLimit(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(3);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(CENTER_1, CENTER_2, PRICE_SMALLEST_LIMIT + ODD_INCREMENT);
        TransportView tv = bp.viewTransport(id);
        final int price = tv.getPrice().intValue();
        System.out.println(price + "---------------------------------");
        assertTrue(price >= ZERO_PRICE && price < PRICE_SMALLEST_LIMIT + ODD_INCREMENT);
        // should also check that it is an odd transporter (e.g. UpaTransporter1)
    }

    //@Test
    public void testRequestTransportCenterEvenPriceBelowUpperLimit(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier("1");
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(3);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String id = bp.requestTransport(CENTER_1, CENTER_2, PRICE_SMALLEST_LIMIT + EVEN_INCREMENT);
        TransportView tv = bp.viewTransport(id);
        final int price = tv.getPrice().intValue();
        assertTrue(price >= ZERO_PRICE && price < PRICE_SMALLEST_LIMIT + EVEN_INCREMENT);
        // should also check that it is an even transporter (e.g. UpaTransporter2)
    }

    //@Test(expected = UnavailableTransportPriceFault_Exception.class)
    public void testRequestTransportNorthOddPriceBelowUpperLimit(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new Expectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, 33);
                result = createJobView(anyString, anyString, anyString, anyString, 33, JobStateView.PROPOSED);
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(CENTER_1, NORTH_1, (PRICE_SMALLEST_LIMIT + ODD_INCREMENT));
    }

    @Test(expected = UnknownLocationFault_Exception.class)
    public void testRequestTransportInvalidOrigin(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {

        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                result = new BadLocationFault_Exception("Testing", new BadLocationFault());
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(EMPTY_STRING, SOUTH_1, PRICE_SMALLEST_LIMIT);
    }

    @Test(expected = UnknownLocationFault_Exception.class)
    public void testRequestTransportNullOrigin(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {

        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                result = new BadLocationFault_Exception("Testing", new BadLocationFault());
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(null, CENTER_1, PRICE_SMALLEST_LIMIT);
    }

    @Test(expected = UnknownLocationFault_Exception.class)
    public void testRequestTransportInvalidDestination(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {

        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                result = new BadLocationFault_Exception("Testing", new BadLocationFault());
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(NORTH_1, EMPTY_STRING, PRICE_SMALLEST_LIMIT);
    }

    @Test(expected = UnknownLocationFault_Exception.class)
    public void testRequestTransportNullDestination(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {

        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                result = new BadLocationFault_Exception("Testing", new BadLocationFault());
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(SOUTH_2, null, PRICE_SMALLEST_LIMIT);
    }

    @Test(expected = UnknownLocationFault_Exception.class)
    public void testRequestTransportInvalidOD(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {

        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                result = new BadLocationFault_Exception("Testing", new BadLocationFault());
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(EMPTY_STRING, EMPTY_STRING, PRICE_SMALLEST_LIMIT);
    }

    @Test(expected = UnknownLocationFault_Exception.class)
    public void testRequestTransportNullOD(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {

        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                result = new BadLocationFault_Exception("Testing", new BadLocationFault());
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(null, null, PRICE_SMALLEST_LIMIT);
    }

    @Test(expected = InvalidPriceFault_Exception.class)
    public void testRequestTransportNegativePrice(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {

        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                result = new BadPriceFault_Exception("Testing", new BadPriceFault());
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(CENTER_1, CENTER_2, INVALID_PRICE);
    }

    @Test(expected = UnavailableTransportFault_Exception.class)
    public void testRequestUnavailableLocation(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                result = null;
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.requestTransport(SOUTH_1, NORTH_1, PRICE_SMALLEST_LIMIT);
    }

}
