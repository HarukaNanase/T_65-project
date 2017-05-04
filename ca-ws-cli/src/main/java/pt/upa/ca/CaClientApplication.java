package pt.upa.ca;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.ca.ws.CaClient;

/**
 * Created by lolstorm on 11/05/16.
 */
public class CaClientApplication {
    public static void main(String[] args) throws Exception {
        // Check arguments
        if (args.length < 2) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL wsName wsURL%n", CaClient.class.getName());
            return;
        }
        String URL = args[0];
        String Nome = args[1];

        UDDINaming uddi = new UDDINaming(URL);
        String endpointAddress = uddi.lookup(Nome);
        if (endpointAddress == null){
            System.out.println("Not found!");
            return;
        }
        else{
            System.out.printf("Found %s%n", endpointAddress);
        }

        CaClient cac = new CaClient(endpointAddress);
        String a = cac.ping("Ola");
        System.out.println(a);

    }
}
