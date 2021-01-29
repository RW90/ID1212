package lecture1;

import java.util.HashMap;
import java.io.*;

public class ChatObserver {
	private HashMap<Integer, PrintStream> outputStreams;
	
	public ChatObserver() {
		this.outputStreams = new HashMap<Integer, PrintStream>();
	}
	
	public void addOutput(int id, PrintStream stream) {
		this.outputStreams.put(Integer.valueOf(id), stream);
	}
	
	public synchronized void notifyObserver(String message, int id) {
		for(Integer key : outputStreams.keySet()) {
			if(id != key.intValue()) {
				outputStreams.get(key).println(message);
			}
		}
	}
	
	public synchronized void removeOutput(int id) {
		outputStreams.remove(Integer.valueOf(id));
	}
}
