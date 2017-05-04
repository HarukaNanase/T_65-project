package pt.upa.broker.ws;

import mockit.Expectations;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Verifications;
import org.junit.Test;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.broker.ws.exception.BrokerWSException;

import javax.xml.registry.JAXRException;

import static org.junit.Assert.fail;

/**
 * Created by lolstorm on 26/04/16.
 */
public class ServerConnectionRequiredTest extends ServerTest {

    @Test
    public void uddiOk(@Mocked final UDDINaming uddiNaming)
            throws Exception {

        new Expectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
            }
        };

        BrokerWrapper bw = new BrokerWrapper(_uddiURL, "UpaBroker", _URL);
        bw.createEndpoint();
        bw.tearDown();
        new Verifications() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                maxTimes = 1;
                uddiNaming.unbind(anyString);
                maxTimes = 1;
                uddiNaming.bind(anyString, anyString);
                maxTimes = 0;
                uddiNaming.rebind(anyString, anyString);
                maxTimes = 1;
            }
        };
    }

    @Test
    public void uddiServerNotFound(@Mocked final UDDINaming uddiNaming)
            throws Exception {

        new Expectations() {
            {
                new UDDINaming(_uddiURL);
                //uddiNaming.list(_wsName);
                result = new BrokerWSException();
            }
        };
        BrokerWrapper bw = new BrokerWrapper(_uddiURL, _wsNameBroker, _URL);
        try {
            bw.createEndpoint();
            fail();
        } catch (BrokerWSException e) {
        }
        finally {
            bw.tearDown();
        }
        new Verifications() {
            {
                new UDDINaming(_uddiURL);
                maxTimes = 1;
                uddiNaming.list(_wsName);
                maxTimes = 0;
                uddiNaming.unbind(anyString);
                maxTimes = 0;
                uddiNaming.bind(anyString, anyString);
                maxTimes = 0;
                uddiNaming.rebind(anyString, anyString);
                maxTimes = 0;
            }
        };
    }

    @Test
    public void uddiServerLookUpError(@Mocked final UDDINaming uddiNaming)
            throws Exception {

        new Expectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = new BrokerWSException();
            }
        };
        BrokerWrapper bw = new BrokerWrapper(_uddiURL, _wsNameBroker, _URL);
        try {
            bw.createEndpoint();
            fail();
        } catch (BrokerWSException e) {
        }
        finally {
            bw.tearDown();
        }
        new Verifications() {
            {
                new UDDINaming(_uddiURL);
                maxTimes = 1;
                uddiNaming.list(_wsName);
                maxTimes = 1;
                uddiNaming.unbind(anyString);
                maxTimes = 0;
                uddiNaming.bind(anyString, anyString);
                maxTimes = 0;
                uddiNaming.rebind(anyString, anyString);
                maxTimes = 0;
            }
        };
    }

}
