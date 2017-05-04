package pt.upa.broker;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.upa.broker.ws.TransportView;
import pt.upa.broker.ws.cli.BrokerClient;

import java.util.ArrayList;

public class BrokerClientApplication {

	public static void main(String[] args) throws Exception {
		System.out.println(BrokerClientApplication.class.getSimpleName() + " starting...");

		if (args.length < 2){
			System.out.println("Falta os args");
			return;
		}

		String URL = args[0];
		String Nome = args[1];


		UDDINaming uddi = new UDDINaming(URL);
		String endpointAddress = uddi.lookup(Nome);

		if(endpointAddress == null){
			System.out.println("Not Found!");
			return;
		} else{
			System.out.printf("Found %s%n", endpointAddress);
		}

		BrokerClient bc = new BrokerClient(endpointAddress);

		while(true){
			System.out.println("Introduza um comando:");
			String command = System.console().readLine();
			switch (command) {
				case "request":
					System.out.println("Introduza a origem:");
					String origin = System.console().readLine();
					System.out.println("Introduza o destino:");
					String destination = System.console().readLine();
					System.out.println("Introduza o preço:");
					String price = System.console().readLine();
					int preco = Integer.parseInt(price);
					try {
						bc.requestTransport(origin, destination, preco);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Erro nos argumentos. Tente novamente.");
						continue;
					}


				case "viewJob":

					System.out.println("Introduza o ID da sua viagem.");
					String id = System.console().readLine();
					try{
						TransportView a = bc.viewTransport(id);
						System.out.println(a.getState());

					}catch(Exception e){
						System.out.println("O seu id nao foi encontrado ou foi encontrado um problema.");
						continue;
					}

				case "clearList":
					System.out.println("Clearing lists...");
					bc.clearTransports();
					continue;

				case "listJobs":
					System.out.println("Now listing jobs...");
					ArrayList<TransportView> a = new ArrayList<>();
					int i = 0;
					while(i < a.size()){
						System.out.println("Transporte" + a.get(i).getId() + "Estado:" + a.get(i).getState());
					}
					System.out.println("Listed all jobs.");

				case "ping":
					System.out.println("Introduza a mensagem a enviar:");
					String ping = System.console().readLine();
					try {
						String b = bc.ping(ping);
						System.out.println("Received:" + b);
					}catch(Exception e){
						System.out.println("Algo correu mal...");
						continue;
					}


			}



		}



	}

}
