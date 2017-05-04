package pt.upa.transporter.ws;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by HarukaNanase on 15-04-2016.
 */
public class JobStatusTransporterTest extends ServerTest{
    JobView a;
    @Before
    public void startUp(){
        try {
            a = getEvenService().requestJob("Lisboa", "Leiria", 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jobStatusComplete(){
        assertEquals(a,getEvenService().jobStatus(a.getJobIdentifier()));

    }
}
