package labb4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MailReader {
	private String mailUrl;
	private int mailPort;
	
	public MailReader() {
		mailUrl = "webmail.kth.se";
		mailPort = 993;
	}
	
	public void fetchMail() {
		//System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk-15.0.1\\lib\\security\\cacerts");
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try(SSLSocket socket = (SSLSocket) factory.createSocket(mailUrl, mailPort)) {
			//socket.startHandshake(); // Implicit
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			String input;
			
			//Check that connection worked
			input = in.readLine();
			System.out.println(input);
			
			//Check capabilities
			out.println("tag1 CAPABILITY");
			out.flush();
			input = in.readLine();
			while(input.indexOf("tag1") == -1) {
				input += "\n" + in.readLine();
			}
			System.out.println(input);
			
			//Login
			out.println("tag2 LOGIN " + Test.decrypt(System.getenv("userHash"), 10) + " " + Test.decrypt(System.getenv("mailHash"), 10));
			out.flush();
			input = in.readLine();
			while(input.indexOf("tag2") == -1) {
				input += "\n" + in.readLine();
			}
			System.out.println(input);
			
			//List options
			out.println("tag3 LIST \"\" \"*\"");
			out.flush();
			input = in.readLine();
			while(input.indexOf("tag3") == -1) {
				input += "\n" + in.readLine();
			}
			System.out.println(input);
			
			// Öppnar inkorgen
			out.println("tag4 SELECT \"INBOX\"");
			out.flush();
			input = in.readLine();
			while(input.indexOf("tag4") == -1) {
				input += "\n" + in.readLine();
			}
			System.out.println(input);
			
			// Hämtar ett mail
			out.println("tag5 fetch 1 RFC822");
			out.flush();
			input = in.readLine();
			while(input.indexOf("tag5") == -1) {
				input += "\n" + in.readLine();
			}
			System.out.println(input);

		} catch(Exception e) {
			System.err.println(e);
		}
	}
	public static void main(String[] args) {
		MailReader reader = new MailReader();
		reader.fetchMail();

	}

}
