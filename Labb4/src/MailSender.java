package labb4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MailSender {
	private String mailUrl;
	private int mailPort;
	
	public MailSender() {
		mailUrl = "smtp.kth.se";
		mailPort = 587;
	}
	
	public void sendMail() {
		//System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk-15.0.1\\lib\\security\\cacerts");
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		//Initialize
		try(Socket regularSocket = new Socket(mailUrl, mailPort)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(regularSocket.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(regularSocket.getOutputStream())));
			String input;
			input = in.readLine();
			while(input.indexOf("220 ") == -1) {
				input += in.readLine();
			}
			System.out.println(input);
			
			//Initiating smtp comm
			out.println("EHLO " + mailUrl);
			out.flush();
			input = in.readLine();
			while(input.indexOf("250 ") == -1) {
				input += "\n" + in.readLine();
			}
			System.out.println(input);
			
			//Starting TLS and Upgrading socket
			out.println("STARTTLS");
			out.flush();
			input = in.readLine();
			while(input.indexOf("220 ") == -1) {
				input += "\n" + in.readLine();
			}
			System.out.println(input);
			SSLSocket socket = (SSLSocket) factory.createSocket(regularSocket, mailUrl, mailPort, true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			
			// Initializing smtp ssl comm
			out.println("EHLO " + mailUrl);
			out.flush();
			input = in.readLine();
			while(input.indexOf("250 ") == -1) {
				input += "\n" + in.readLine();
			}
			System.out.println(input);
			
			// Logging in
			String encodedUser = Base64.getEncoder().encodeToString(Test.decrypt(System.getenv("userHash"), 10).getBytes(StandardCharsets.UTF_8));
			String encodedPass = Base64.getEncoder().encodeToString(Test.decrypt(System.getenv("mailHash"), 10).getBytes(StandardCharsets.UTF_8));
			out.println("AUTH LOGIN");
			out.flush();
			input = in.readLine();
			System.out.println(input);
			out.println(encodedUser);
			out.flush();
			input = in.readLine();
			System.out.println(input);
			out.println(encodedPass);
			out.flush();
			input = in.readLine();
			System.out.println(input);
			
			//Send mail to myself
			out.println("MAIL FROM:<riwa@kth.se>");
			out.flush();
			input = in.readLine();
			System.out.println(input);
			out.println("RCPT TO:<riwa@kth.se>");
			out.flush();
			input = in.readLine();
			System.out.println(input);
			out.println("DATA");
			out.flush();
			input = in.readLine();
			System.out.println(input);
			out.println("Test mail from my own web client!");
			out.println(".");
			out.flush();
			input = in.readLine();
			System.out.println(input);
			
			//Quit
			out.println("QUIT");
			out.flush();
			input = in.readLine();
			System.out.println(input);
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public static void main(String[] args) {
		MailSender sender = new MailSender();
		sender.sendMail();

	}

}
