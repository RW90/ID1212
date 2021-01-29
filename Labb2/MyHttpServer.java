package lecture2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyHttpServer {
	
	private SessionHandler sessions;
	private int count;
	
	public MyHttpServer() {
		sessions = new SessionHandler();
		count = 1;
	}
	
	public void start() {
		Socket socket;
		Thread process;
		try(ServerSocket serverSocket = new ServerSocket(1234)) {
			while(true) {
				socket = serverSocket.accept();
				process = new Thread(new ServerProcess(socket, sessions, count++));
				process.start();
				if(count == Integer.MAX_VALUE) {
					count = 1;
				}
			}
		} catch (Exception e) {
			System.out.println("Serversocket shutting down");
		}
	}
	
	private class ServerProcess implements Runnable {
		private Socket socket;
		private ResponseCreator res;
		private RequestParser req;
		private SessionHandler sessions;
		private int sessionId;
		
		public ServerProcess(Socket socket, SessionHandler sessions, int generatedId) {
			this.socket = socket;
			this.sessions = sessions;
			this.sessionId = generatedId;
		}
		
		@Override
		public void run() {
			try {
				res = new ResponseCreator(socket.getOutputStream());
				req = new RequestParser(socket.getInputStream());
			} catch(IOException e) {
				System.out.println("Failure to create io");
			}
			try {
				req.read();
			} catch(IOException e) {
				System.out.println("Failure to read request");
			}
			System.out.println("Read request");
			if(!req.getMethod().equals("GET") || req.isFavIconRequest()) {
				res.initiateAndSend404Response();
				System.out.println("Sending 404 res");
				return;
			}
			res.initiateOkResponse();
			if(!req.hasCookie()) {
				sessions.newSession(sessionId);
				res.setCookie(sessionId);
			} else {
				sessionId = req.getSessionId();
			}
			if(req.reqNewGame()) {
				sessions.newSession(sessionId);
			}
			System.out.println("Checked cookies");
			if(req.hasGuess()) {
				sessions.sessionGuess(sessionId, req.getGuess());
			}
			System.out.println("Checked and parsed guess");
			res.generateBody(sessions.getSessionGameState(sessionId));
			System.out.println("Generated body");
			try {
				System.out.println("Sending...");
				res.send();
				System.out.println("Sent");
			} catch (IOException e) {
				System.out.println("Failure to send response");
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		MyHttpServer server = new MyHttpServer();
		server.start();
	}
}
