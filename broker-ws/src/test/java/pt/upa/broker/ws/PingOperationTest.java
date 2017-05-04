package pt.upa.broker.ws;

import org.junit.Before;
import org.junit.Test;
import pt.upa.broker.ws.domain.TransportView;

import static org.junit.Assert.assertNotNull;

/**
 * Created by nucleardannyd on 15/04/16.
 */
public class PingOperationTest extends ServerTest {
    @Test
    public void pingTest() throws Exception {
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        assertNotNull(bp.ping("test"));
    }
}
