package se.kth.id1212.labb5.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import se.kth.id1212.labb5.server.Reader;

public class RMIClient {

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			Reader reader = (Reader) registry.lookup("MailReader");
			String message = reader.fetchMail();
			System.out.println(message);
		} catch(Exception e) {
			System.err.println(e);
		}
	}
}
