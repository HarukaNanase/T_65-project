package pt.upa.ca;

import pt.upa.ca.ws.CaPort;
import pt.upa.ca.ws.CaWrapper;

/**
 * Created by lolstorm on 11/05/16.
 */
public class CaApplication {
    public static void main(String[] args) throws Exception {
        // Check arguments
        if (args.length < 3) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL wsName wsURL%n", CaPort.class.getName());
            return;
        }

        String uddiURL = args[0];
        String name = args[1];
        String url = args[2];


        CaWrapper server = new CaWrapper(uddiURL,name,url);
        server.run();



    }
}
