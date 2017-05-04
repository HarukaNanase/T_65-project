package pt.upa.transporter.ws;

import pt.upa.transporter.ws.domain.Manager;

import javax.jws.WebService;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static pt.upa.transporter.ws.JobStateView.*;


@WebService(
        endpointInterface="pt.upa.transporter.ws.TransporterPortType",
        wsdlLocation="transporter.1_0.wsdl",
        name="TransporterPortType",
        portName="TransporterPort",
        targetNamespace="http://ws.transporter.upa.pt/",
        serviceName="TransporterService"
)

public class TransporterPort implements TransporterPortType {
    private static final Set<String> tEven= new HashSet<>(Arrays.asList(new String [] {
            "Lisboa","Leiria", "Santarém","Castelo Branco","Coimbra","Aveiro", "Viseu", "Guarda",
            "Porto", "Braga","Viana do Castelo","Vila Real","Bragança"
    }));
    private static final Set<String> tOdd = new HashSet<>(Arrays.asList(new String [] {
            "Lisboa","Leiria", "Santarém","Castelo Branco","Coimbra","Aveiro", "Viseu", "Guarda",
            "Setúbal", "Évora","Portalegre","Beja","Faro"
    }));
    private String _name;
    private int _id;

    public TransporterPort() {
        super();
    }

    public TransporterPort(String name){
        Manager m = Manager.getInstance();
        _name = name;
        String id [] = name.split("UpaTransporter");
        _id = (Integer.parseInt(id[1]));
    }


    @Override
    public String ping(String name) {
        return name + "successfully Pinged";
    }

    @Override
    public JobView requestJob(String origin, String destination, int price) throws BadLocationFault_Exception, BadPriceFault_Exception {

        if (!(tEven.contains(origin) || tOdd.contains(origin)))
            throw new BadLocationFault_Exception("Origin is not valid", new BadLocationFault());

        else if (!(tEven.contains(destination) || tOdd.contains(destination)))
            throw new BadLocationFault_Exception("Destination is not valid", new BadLocationFault());

        else if(price < 0)
            throw new BadPriceFault_Exception("Negative price is not valid", new BadPriceFault());

        else{
            if(price > 100)
                return null;

            else if (price <= 10) {
                if (price != 0)
                    price /= ThreadLocalRandom.current().nextInt(2,10);
            }

            if (_id % 2 == 0){
                if (!((tEven.contains(origin)) && (tEven.contains(destination)))){
                    return null;
                }
                else if (price % 2 == 0)
                    price /= ThreadLocalRandom.current().nextInt(2,10);
                else
                    price *= ThreadLocalRandom.current().nextInt(2,10);
            }
            else {

                if (!((tOdd.contains(origin)) && (tOdd.contains(destination))))
                    return null;
                else if (price % 2 == 0)
                    price *= ThreadLocalRandom.current().nextInt(2,10);
                else
                    price /= ThreadLocalRandom.current().nextInt(2,10);
            }
        }

        JobView job = new JobView();
        job.setCompanyName(_name);
        job.setJobIdentifier(Manager.getInstance().getJobs().size() + "");
        job.setJobOrigin(origin);
        job.setJobDestination(destination);
        job.setJobPrice(price);
        job.setJobState(PROPOSED);

        Manager.getInstance().addJobView(job);
        return job;
    }

    @Override
    public JobView decideJob(String id, boolean accept) throws BadJobFault_Exception {
        JobView j = Manager.getInstance().getJobView(id);
        if (j == null || j.getJobState() != PROPOSED)
            throw new BadJobFault_Exception("JobView not found", new BadJobFault());
        if (accept) {
            j.setJobState(ACCEPTED);
            Manager.getInstance().simulateJob(j);
       }
        else
            j.setJobState(REJECTED);
        return j;

    }

    @Override
    public JobView jobStatus(String id) {
        return Manager.getInstance().getJobView(id);
    }

    @Override
    public List<JobView> listJobs() {
        return Manager.getInstance().getJobs();
    }

    @Override
    public void clearJobs() {
        Manager.getInstance().clearJobs();
    }
}
