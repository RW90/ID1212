package lecture1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientServerListener implements Runnable{

	private BufferedReader in;
	private String message;
	
	public ClientServerListener(Socket socket) {
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch(Exception e) {
			System.out.println("Client misslyckades skapa indatastream från servern.");
		}
		message = "";
	}
	@Override
	public void run() {
		try {
			while( (message = in.readLine()) != null){
	            System.out.println("> " + message);
	            System.out.print("> ");
	        }
		} catch(Exception e) {
			System.out.println("Client terminated.");
		} finally {
			System.exit(0);
		}
	}
}
