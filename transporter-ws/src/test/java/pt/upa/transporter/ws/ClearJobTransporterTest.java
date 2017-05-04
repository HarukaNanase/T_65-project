package pt.upa.transporter.ws;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Created by HarukaNanase on 15-04-2016.
 */
public class ClearJobTransporterTest extends ServerTest {

        @Test
        public void ClearJobTest() throws Exception {
            for(int i= 0 ; i < 5 ; i ++){
                getEvenService().requestJob("Lisboa","Leiria",15);
            }

            getEvenService().clearJobs();
            assertTrue(getEvenService().listJobs().isEmpty());


    }







}
