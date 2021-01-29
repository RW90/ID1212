package lecture1;
import java.io.*;
import java.net.Socket;

public class ServerProcess implements Runnable {
	private Socket socket;
	private BufferedReader in;
    private String text;
    private ChatObserver observer;
    private int id;
	
	public ServerProcess(Socket socket, int id, ChatObserver observer) {
		this.socket = socket;
		this.id = id;
		this.observer = observer;
		this.addObserver();
		this.text = "";
	}
	
	private void addObserver() {
		try {
			this.observer.addOutput(this.id, new PrintStream(socket.getOutputStream()));
		} catch(Exception e) {
			System.out.println(id + " failed to create outputstream.");
		}
		
	}
	
	private void removeObserver() {
		this.observer.removeOutput(this.id);
	}

	@Override
	public void run() {
		try {
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			while( (text = in.readLine()) != null){
				this.observer.notifyObserver(text, this.id);
            }
			this.socket.close();
		} catch(Exception e) {
			System.out.println(id + " failed");
		} finally {
			this.removeObserver();
			System.out.println(id + " shut down");
		}
	}
}
