package pt.upa.broker.ws;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.transporter.ws.JobStateView;
import pt.upa.transporter.ws.JobView;
import pt.upa.transporter.ws.cli.TransporterClient;

import static org.junit.Assert.assertEquals;

/**
 * Created by lolstorm on 10/05/16.
 */

@RunWith(JMockit.class)
public class ClearTransportOperationTest extends ServerTest {
    @Test(expected = UnavailableTransportFault_Exception.class)
    public void testClearTransports(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(anyString);
                uddiNaming.list(anyString);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView j = new JobView();
                j.setJobIdentifier(anyString);
                j.setJobOrigin(anyString);
                j.setJobDestination(anyString);
                j.setCompanyName(anyString);
                j.setJobPrice(anyInt);
                j.setJobState(JobStateView.PROPOSED);
                result = j;
                tp.decideJob(anyString, anyBoolean);
                result = "";
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        String rt = bp.requestTransport(CENTER_1, SOUTH_1, PRICE_SMALLEST_LIMIT);
        bp.clearTransports();
        assertEquals(0, bp.listTransports().size());
        bp.viewTransport(rt);
    }
}
