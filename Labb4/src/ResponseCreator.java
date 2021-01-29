package labb4;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import labb4.SessionHandler.GameMsg;

public class ResponseCreator {
	
	private OutputStreamWriter out;
	private LinkedList<String> response;
	
	public ResponseCreator(OutputStream out) {
		try {
			this.out = new OutputStreamWriter(new BufferedOutputStream(out), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.out = new OutputStreamWriter(new BufferedOutputStream(out));
		}
	}
	
	public void initiateAndSend404Response() {
		response = new LinkedList<>();
		response.add("HTTP/1.1 404 Not Found\r\n");
		response.add("Server: Rilles hembyggda\r\n");
		response.add("Content-Length: 0");
		try {
			send();
		} catch (IOException e) {
			System.out.println("Unable to send 404 message.");
		}
		
	}
	
	public void initiateOkResponse() {
		response = new LinkedList<>();
		response.add("HTTP/1.1 200 OK\r\n");
		response.add("Server: Rilles hembyggda\r\n");
		response.add("Content-type: text/html\r\n");
	}
	
	public void setCookie(int sessionId) {
		response.add(1, "Set-cookie: sessionId=" + sessionId + "\r\n");
	}
	
	public void generateBody(GameStateDTO gameState) {
		GameMsg msg = gameState.getMsg();
		StringBuilder body = new StringBuilder();
		body.append("<html><body>");
		if(msg == GameMsg.NEW) {
			body.append("New game! I'm thinking of a number from 1 - 100. Try to guess what it is! ");
		} else  {
			body.append("You have guessed " + gameState.getNoOfGuesses() + " times. ");
			if(msg == GameMsg.HIGHER) {
				body.append("Guess higher!");
			} else if(msg == GameMsg.LOWER) {
				body.append("Guess lower!");
			} else {
				body.append("You guessed correctly! You win! Play again!");
			}
		}
		if(msg == GameMsg.WIN) {
			body.append("<br><form action=\"/new\" method=\"GET\">");
			body.append("<input type=\"submit\" value=\"New game\">");
		} else {
			body.append("<br><form action=\"/\" method=\"GET\">");
			body.append("<input type=\"number\" id=\"guess\" name=\"guess\" value=\"Your guess\">");
			body.append("<input type=\"submit\" value=\"Send\">");
		}
		
		
		body.append("</form>");
		body.append("</body></hml>");
		String bodyString = body.toString();
		int resLength = bodyString.getBytes().length;
		response.add("Content-Length: " + resLength + "\r\n");
		response.add("\r\n\r\n");
		response.add(bodyString);
	}
	
	public void send() throws IOException {
		for(String line : response) {
			out.write(line);
		}
		out.flush();
	}
}
