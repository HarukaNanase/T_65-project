package pt.upa.transporter;

import pt.upa.transporter.ws.TransporterPort;
import pt.upa.transporter.ws.TransporterWrapper;
import pt.upa.transporter.ws.domain.Manager;

public class TransporterApplication {

	public static void main(String[] args) throws Exception {
		// Check arguments
		if (args.length < 3) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s uddiURL wsName wsURL%n", TransporterPort.class.getName());
			return;
		}

		String uddiURL = args[0];
		String name = args[1];
		String url = args[2];
		String keyName = args[3];
		Manager.getInstance().setKeyName(keyName);
		TransporterWrapper server = new TransporterWrapper(uddiURL,name,url);
		server.run();
	}

}
