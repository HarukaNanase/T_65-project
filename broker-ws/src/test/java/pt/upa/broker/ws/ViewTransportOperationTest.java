package pt.upa.broker.ws;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by lolstorm on 10/05/16.
 */
public class ViewTransportOperationTest extends ServerTest {
    //@Test
    public void testTransportStateTransition() throws Exception {
        List<TransportStateView> tS = new ArrayList<>();

        tS.add(TransportStateView.HEADING);
        tS.add(TransportStateView.ONGOING);
        tS.add(TransportStateView.COMPLETED);
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String rt = bp.requestTransport(CENTER_1, SOUTH_1, PRICE_SMALLEST_LIMIT);
        TransportView vt = bp.viewTransport(rt);
        assertEquals(vt.getState(), TransportStateView.BOOKED);

        for (int t = 0; t <= 3 * DELAY_UPPER || !tS.isEmpty(); t += TENTH_OF_SECOND) {
            Thread.sleep(TENTH_OF_SECOND);
            vt = bp.viewTransport(rt);
            if (tS.contains(vt.getState()))
                tS.remove(vt.getState());
        }
        assertEquals(0, tS.size());
    }

    @Test(expected = UnknownTransportFault_Exception.class)
    public void testViewInvalidTransport() throws Exception {
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.viewTransport(null);
    }

    @Test(expected = UnknownTransportFault_Exception.class)
    public void testViewNullTransport() throws Exception {
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        bp.viewTransport(EMPTY_STRING);
    }
}
