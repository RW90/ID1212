package se.kth.id1212.labb5.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {

	public void run() {
		try {
			MailReader mailReader = new MailReader();
			Reader stub = (Reader) UnicastRemoteObject.exportObject(mailReader, 0);
			Registry rmiRegistry = LocateRegistry.createRegistry(1099);
			rmiRegistry.bind("MailReader", stub);
			System.out.println("Server started successfully");
		} catch(Exception e) {
			System.err.println(e);
		}
		
	}
	public static void main(String[] args) {
		RMIServer server = new RMIServer();
		server.run();
	}

}
