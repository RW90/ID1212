package se.kth.id1212.labb5.server;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class MailReader implements Reader{
	private String mailUrl;
	private int mailPort;
	
	public MailReader() {
		mailUrl = "webmail.kth.se";
		mailPort = 993;
	}
	
	private String constructString(Message mail) {
		StringBuilder message = new StringBuilder();
		try {
			message.append("From: \n");
			message.append(mail.getFrom()[0] + "\n");
			message.append("Subject: \n");
			message.append(mail.getSubject() + "\n");
			message.append("Content: \n");
			message.append(mail.getContent() + "\n");
		} catch(Exception e) {
			System.err.println(e);
			message.append("Not able to retrieve message");
		}
		return message.toString();
	}
	
	public String fetchMail() {
		String message = "";
		Session session = Session.getDefaultInstance(new Properties());
		try(Store store = session.getStore("imaps")) {
			store.connect(mailUrl, mailPort, Test.decrypt(System.getenv("userHash"), 10), Test.decrypt(System.getenv("mailHash"), 10));
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			Message mail = inbox.getMessage(1);
			message = constructString(mail);
		} catch(Exception e) {
			System.err.println(e);
		}
		return message;
	}

	public static void main(String[] args) {
		MailReader reader = new MailReader();
		System.out.println(reader.fetchMail());

	}

}
