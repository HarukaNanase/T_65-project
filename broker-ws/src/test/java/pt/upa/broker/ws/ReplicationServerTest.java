package pt.upa.broker.ws;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.transporter.ws.BadJobFault;
import pt.upa.transporter.ws.BadJobFault_Exception;

import pt.upa.transporter.ws.cli.TransporterClient;

/**
 * Created by lolstorm on 10/05/16.
 */
public class ReplicationServerTest extends ServerTest {
//    @Test
    public void success(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new Expectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString,anyString, 10);

            }
        };
        BrokerPort bpo = new BrokerPort(_uddiURL, _wsNameBroker);
        bpo.clearTransports();
        //bp.requestTransport("Lisboa", "Porto", 10);
        try {
            String id = bpo.requestTransport("Lisboa", "Porto", 10);
            throw new BadJobFault_Exception("", new BadJobFault());
        } catch (BadJobFault_Exception e) {
            String id = bpo.requestTransport("Lisboa", "Porto", 10);
        }
    }
}
