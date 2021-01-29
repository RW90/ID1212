package se.kth.id1212.labb5.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Reader extends Remote {
	public String fetchMail() throws RemoteException;
}
