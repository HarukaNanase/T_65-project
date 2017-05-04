package pt.upa.broker.ws;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Test;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.transporter.ws.JobStateView;
import pt.upa.transporter.ws.JobView;
import pt.upa.transporter.ws.cli.TransporterClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by lolstorm on 10/05/16.
 */
public class ListTransportsTest extends ServerTest {
    @Test
    public void testListTransports(@Mocked final UDDINaming uddiNaming, @Mocked final TransporterClient tp) throws Exception {
        new NonStrictExpectations() {
            {
                new UDDINaming(_uddiURL);
                uddiNaming.list(_wsName);
                result = _endpoints;
                tp.requestJob(anyString, anyString, anyInt);
                JobView job0 = createJobView("1", SOUTH_1, CENTER_1, anyString, PRICE_SMALLEST_LIMIT, JobStateView
                        .PROPOSED);
                JobView job1 = createJobView("2", NORTH_1, CENTER_1, anyString, PRICE_SMALLEST_LIMIT, JobStateView.PROPOSED);
                JobView job2 = createJobView("3", CENTER_1, CENTER_2, anyString, PRICE_SMALLEST_LIMIT, JobStateView.PROPOSED);
                returns(job0, job1, job2);
                tp.decideJob(anyString, anyBoolean);
                result = "";
                tp.jobStatus(anyString);
                JobView job3 = createJobView("1", anyString, anyString, anyString, anyInt, JobStateView.HEADING);
                JobView job4 = createJobView("2", anyString, anyString, anyString, anyInt, JobStateView.ONGOING);
                JobView job5 = createJobView("3", anyString, anyString, anyString, anyInt, JobStateView.COMPLETED);
                returns(job3, job4, job5);
            }
        };
        BrokerPort bp = new BrokerPort(_uddiURL, _wsNameBroker);
        bp.clearTransports();
        String j1 = bp.requestTransport(SOUTH_1, CENTER_1, PRICE_SMALLEST_LIMIT);
        String j2 = bp.requestTransport(NORTH_1, CENTER_1, PRICE_SMALLEST_LIMIT);
        String j3 = bp.requestTransport(CENTER_1, CENTER_2, PRICE_SMALLEST_LIMIT);
        List<TransportView> jtvs = new ArrayList<>();
        jtvs.add(bp.viewTransport(j1));
        jtvs.add(bp.viewTransport(j2));
        bp.viewTransport(j3);

        List<TransportView> tList = bp.listTransports();
        assertEquals(3, tList.size());
        int counter = 0;

        for (TransportView tv : tList)
            for (TransportView jtv : jtvs)
                if ((jtv.getId().equals(tv.getId())) && jtv.getOrigin().equals(tv.getOrigin()) && jtv.getDestination()
                        .equals(tv.getDestination()) && jtv.getPrice() == tv.getPrice()
                        && jtv.getState().toString().equals(tv.getState().toString()))
                    counter++;
        assertEquals(2, counter);
    }
}
