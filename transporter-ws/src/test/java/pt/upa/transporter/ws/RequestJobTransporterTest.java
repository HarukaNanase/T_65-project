package pt.upa.transporter.ws;

import org.junit.*;

import static org.junit.Assert.*;
import static pt.upa.transporter.ws.JobStateView.PROPOSED;


/**
 * Created by HarukaNanase on 15-04-2016.
 */
public class RequestJobTransporterTest extends ServerTest {

    @Test(expected = BadLocationFault_Exception.class)
    public void InvalidOriginOdd() throws Exception {
        getOddService().requestJob("Odaiba", "Lisboa", 10);
    }

    @Test(expected = BadLocationFault_Exception.class)
    public void InvalidDestinationOdd() throws Exception {
        getOddService().requestJob("Lisboa", "Digital World", 10);
    }

    @Test(expected = BadPriceFault_Exception.class)
    public void InvalidPriceOdd() throws Exception {
        getOddService().requestJob("Porto", "Lisboa", -1);
    }

    @Test
    public void PriceOver100Odd() {
        try {
            JobView a = getOddService().requestJob("Lisboa", "Porto", 9000);
            assertNull(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PriceZero() {
        try {
            JobView a = getOddService().requestJob("Beja", "Lisboa", 0);
            assertEquals(a.getJobPrice(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void outOfPlaceOriginOdd() throws Exception{
        try{
            assertNull(this.getOddService().requestJob("Porto","Lisboa",15));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void outOfPlaceDestinationOdd() throws Exception{
        try{
            assertNull(getOddService().requestJob("Lisboa","Porto",15));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void outOfPlaceOriginEven() throws Exception{
        try{
            assertNull(getEvenService().requestJob("Faro","Lisboa",15));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void outOfPlaceDestinationEven() throws Exception{
        try{
            assertNull(getEvenService().requestJob("Lisboa","Faro",15));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void SuccessOddTransporter() throws Exception {
        try {
            JobView a = getOddService().requestJob("Lisboa", "Leiria", 16);
            assertTrue(a != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void correctOriginOddTransporter() throws Exception {
        try {
            JobView a = getOddService().requestJob("Lisboa", "Leiria", 15);
            assertTrue(a.getJobOrigin().equals("Lisboa"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void correctDestinationOddTransporter() throws Exception {
        try {
            JobView a = getOddService().requestJob("Lisboa", "Leiria", 15);
            assertTrue(a.getJobDestination().equals("Leiria"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void oddTransporterOddPrice() throws Exception {
        try {
            JobView a = getOddService().requestJob("Lisboa", "Leiria", 15);
            assertTrue(a.getJobPrice() < 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void correctStateOdd() throws Exception{
        try{
            JobView a = getOddService().requestJob("Lisboa", "Leiria", 15);
            assertTrue(a.getJobState().equals(PROPOSED));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void doubleCorrectIdOdd() throws Exception{
        try{
            JobView a = getOddService().requestJob("Lisboa", "Leiria", 15);
            JobView b = getOddService().requestJob("Lisboa", "Leiria", 15);
            assertNotSame(a.getJobIdentifier(),b.getJobIdentifier());
            assertTrue(Integer.parseInt(a.getJobIdentifier()) < Integer.parseInt(b.getJobIdentifier()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void PriceLowerOrEqualTo10() throws Exception{
        try{
            JobView a = getOddService().requestJob("Lisboa", "Leiria", 10);
            assertTrue("Price equals " + a.getJobPrice() + " instead of 10", a.getJobPrice() < 10);
            JobView b = getOddService().requestJob("Lisboa", "Leiria", 5);
            assertTrue("Price equals " + b.getJobPrice() + " instead of 5",b.getJobPrice() < 5);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Test(expected = BadLocationFault_Exception.class)
    public void InvalidOriginEven() throws Exception {
        getEvenService().requestJob("Odaiba", "Lisboa", 10);
    }

    @Test(expected = BadLocationFault_Exception.class)
    public void InvalidDestinationEven() throws Exception {
        getEvenService().requestJob("Lisboa", "Digital World", 10);
    }

    @Test(expected = BadPriceFault_Exception.class)
    public void InvalidPriceEven() throws Exception {
        getEvenService().requestJob("Lisboa", "Leiria", -1);
    }

    @Test
    public void PriceOver100Even() throws Exception{
       try{
           assertNull(getEvenService().requestJob("Lisboa", "Leiria", 1000));
       }catch(Exception e){
           e.printStackTrace();
        }
    }

    @Test
    public void JobSuccessEven() throws Exception {
        try {
            assertNotNull(getEvenService().requestJob("Lisboa","Leiria",15));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void correctOriginEven() throws Exception {
        try {
            assertEquals("Lisboa", getEvenService().requestJob("Lisboa", "Leiria", 11).getJobOrigin());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void correctDestinationEven() throws Exception {
        try {
            assertEquals("Leiria", getEvenService().requestJob("Lisboa", "Leiria", 16).getJobDestination());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void correctEvenPriceEven() throws Exception {
        try {
            JobView a = getEvenService().requestJob("Lisboa", "Leiria", 16);
            assertTrue(a.getJobPrice() < 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void correctOddPriceEven() throws Exception {
        try {
            JobView a = getEvenService().requestJob("Lisboa", "Leiria", 15);
            assertTrue(a.getJobPrice() > 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void correctStateEven() throws Exception {
        try {
            assertSame(PROPOSED, getEvenService().requestJob("Lisboa", "Leiria", 16).getJobState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PriceLowerOrEqual10Even() throws Exception {
        try {
            assertTrue(getEvenService().requestJob("Lisboa", "Leiria", 10).getJobPrice() < 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void portoFaroTryEven() throws Exception{
        try{
            JobView job = getEvenService().requestJob("Faro","Porto",15);
            assertNull(job);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void bejaPortoTryEven() throws Exception{
        try{
            JobView job = getEvenService().requestJob("Beja","Porto",10);
            assertNull(job);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void portoFaroTryOdd() throws Exception{
        try{
            JobView job = getOddService().requestJob("Faro","Porto",15);
            assertNull(job);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}



