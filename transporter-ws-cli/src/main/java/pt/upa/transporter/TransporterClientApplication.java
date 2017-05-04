package pt.upa.transporter;


import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.transporter.ws.cli.TransporterClient;

public class TransporterClientApplication {

	public static void main(String[] args) throws Exception {
		System.out.println(TransporterClientApplication.class.getSimpleName() + " starting...");



		if (args.length < 2){
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s uddiURL name%n", TransporterClientApplication.class.getName());
			return;
		}
		String URL = args[0];
		String Nome = args[1];

		UDDINaming uddi = new UDDINaming(URL);
		String endpointAddress = uddi.lookup(Nome);
		if (endpointAddress == null) {
			System.out.println("Not found!");
			return;
		} else {
			System.out.printf("Found %s%n", endpointAddress);
		}

		TransporterClient tc = new TransporterClient(endpointAddress);
		String a = tc.ping("ola");
		System.out.println(a);

	}
}
