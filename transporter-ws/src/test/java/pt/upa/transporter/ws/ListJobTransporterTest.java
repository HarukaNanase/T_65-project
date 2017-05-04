package pt.upa.transporter.ws;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by HarukaNanase on 15-04-2016.
 */
public class ListJobTransporterTest extends ServerTest {

    @Before
    public void startUp() throws Exception{
        for(int i = 0; i < 5; i++){
            getEvenService().requestJob("Lisboa","Leiria",15);
        }
    }
    @Test
    public void listJobsTest(){
        assertTrue((!(getEvenService().listJobs().isEmpty())));
    }

    @Test
    public void listJobsTestReturn(){
        for(int i = 0; i<5; i++){
            assertEquals(JobView.class, getEvenService().listJobs().get(i).getClass());
        }
    }

    @Test
    public void listJobsTestOrigin(){
        for(int i = 0; i<5; i++){
            assertTrue(getEvenService().listJobs().get(i).getJobOrigin().equals("Lisboa"));
        }
    }

    @Test
    public void listJobsTestDestination(){
        for(int i=0; i<5; i++){
            assertTrue(getEvenService().listJobs().get(i).getJobDestination().equals("Leiria"));
        }
    }

    @Test
    public void listJobsTestPrice(){
        for(int i=0; i<5; i++){
            assertTrue(getEvenService().listJobs().get(i).getJobPrice() > 15);
        }
    }

}

