package pt.upa.transporter.ws;

import org.apache.commons.collections.functors.ExceptionTransformer;
import org.junit.*;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import static pt.upa.transporter.ws.JobStateView.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by HarukaNanase on 15-04-2016.
 */
public class DecideJobTransporterTest extends ServerTest {
    JobView a;
    @Before
    public void setup(){
        try{
            a = getEvenService().requestJob("Lisboa","Leiria",15);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test(expected=BadJobFault_Exception.class)
    public void badJob() throws Exception{
        getEvenService().decideJob("Digital World", true);
    }

    @Test
    public void rejectedJob() throws Exception{
        try{
            getEvenService().decideJob(a.getJobIdentifier(), false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test(expected=BadJobFault_Exception.class)
    public void proposedJob() throws Exception{
            a.setJobState(COMPLETED);
            getEvenService().decideJob(a.getJobIdentifier(),true);

    }

    @Test
    public void acceptJob() throws Exception{
        try{
            JobView j = getEvenService().decideJob(a.getJobIdentifier(), true);
            assertEquals(ACCEPTED, j.getJobState());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void headingJob() throws Exception{
        try{
            int time = 0;
            getEvenService().decideJob(a.getJobIdentifier(), true);
            while(a.getJobState() != JobStateView.HEADING){
                Thread.currentThread().sleep(500);
                time += 1;
                if (time > 10)
                    break;
            }
            time *= 0.5;
            assertTrue("time got was " + time, (time >= 1 && time <=5));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void ongoingJob() throws Exception {
        try {
            int time = 0;
            getEvenService().decideJob(a.getJobIdentifier(), true);
            getEvenService().listJobs().get(0).setJobState(HEADING);
            while (a.getJobState() != ONGOING) {
                Thread.currentThread().sleep(500);
                time += 1;
                if (time > 20)
                    break;
            }
            time *= 0.5;
            assertTrue("time got was " + time, (time >= 2 && time <= 10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void completedJob() throws Exception {
        try {

            int time3 = 0;
            getEvenService().decideJob(a.getJobIdentifier(), true);
            getEvenService().listJobs().get(0).setJobState(ONGOING);
            while (a.getJobState() != COMPLETED){
                Thread.currentThread().sleep(500);
                time3 += 1;
                if (time3 > 30)
                    break;
            }
            time3 *= 0.5;
            assertTrue("time got was " + time3,(time3 >= 1 && time3 <= 15));

        }catch(Exception e){
            e.printStackTrace();
    }
}














}
