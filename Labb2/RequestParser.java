package lecture2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {
	
	private BufferedReader in; 
	private String httpMethod;
	private boolean hasCookie;
	private int sessionId;
	private boolean hasGuess;
	private int guess;
	private boolean isFavIconReq;
	private boolean requestingNewGame;
	
	public RequestParser(InputStream in) {
		this.in = new BufferedReader(new InputStreamReader(in));
		hasGuess = false;
		hasCookie = false;
		requestingNewGame = false;
	}
	
	private void parseRequest(String request) {
		String[] requestLine = request.split(" ");
		httpMethod = requestLine[0];
		isFavIconReq = requestLine[1].contains("favicon");
		hasGuess = requestLine[1].contains("guess");
		if(hasGuess) {
			guess = Integer.parseInt(requestLine[1].split("=")[1]);
		} else {
			requestingNewGame = requestLine[1].contains("new");
		}
	}
	
	private void parseCookie(String inputLine) {
		if(inputLine.contains("Cookie")) {
			hasCookie = true;
			sessionId = Integer.parseInt(inputLine.split("=")[1]);
		}
	}
	
	public void read() throws IOException {
		
		String inputLine = in.readLine();
		parseRequest(inputLine);
		while((inputLine = in.readLine()) != null && inputLine.length() > 0){
				//System.out.println(inputLine);
				parseCookie(inputLine);
				if(hasCookie) {
					break;
				}
		}
	}
	
	public String getMethod() {
		return httpMethod;
	}
	
	public boolean isFavIconRequest() {
		return isFavIconReq;
	}
	
	public boolean hasCookie() {
		return hasCookie;
	}
	
	public int getSessionId() {
		if(hasCookie) {
			return sessionId;
		}
		return -1;
	}
	
	public boolean hasGuess() {
		return hasGuess;
	}
	
	public int getGuess() {
		if(hasGuess) {
			return guess;
		}
		return -1;
	}
	
	public boolean reqNewGame() {
		return requestingNewGame;
	}
}
