package lecture1;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		int socketId = 1;
		ServerSocket serverSocket = null;
		ChatObserver observer = new ChatObserver();
		
		try {
			serverSocket = new ServerSocket(1234);
		} catch(Exception e) {
			System.out.println("Misslyckades skapa serversocket.");
			System.exit(0);
		}
		
		while(true){
            Socket socket = serverSocket.accept();
            Runnable serverSession = new ServerProcess(socket, socketId++, observer);
            if(socketId == Integer.MAX_VALUE) {
            	socketId = Integer.MIN_VALUE;
            }
            Thread newSession = new Thread(serverSession);
            newSession.start();
        }

	}

}
