package pt.upa.broker;

import pt.upa.broker.ws.BrokerPort;
import pt.upa.broker.ws.BrokerWrapper;
import pt.upa.broker.ws.domain.Manager;

import java.net.BindException;

public class BrokerApplication {

    public static void main(String[] args) throws Exception {
        // Check arguments
        if (args.length < 3) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL wsName wsURL%n", BrokerPort.class.getName());
            return;
        }
        String uddiURL = args[0];
        String name = args[1];
        String url = args[2];
        String keyName = args[3];
        Manager.getInstance().setKeyName(keyName);
        BrokerWrapper server = new BrokerWrapper(uddiURL,name,url);
        server.run();
        //server.run();
    }

}
