package com.tmp.email;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class EmailService {

	private final static Logger LOGGER = Logger.getLogger(EmailService.class.getName());
	private static int size;
	private static String requirements;

	public static int getListSize(String string) {
		ArrayList<String> value = new ArrayList<String>();
		if (null != string) {
			StringTokenizer token = new StringTokenizer(string, ",");
			while (token.hasMoreTokens()) {
				/* System.out.println("Tokens " +token.nextToken()); */
				value.add(token.nextToken());
			}
		}
		System.out.println("Size of a list " + value.size());

		return value.size();
	}

	public String templateToHtml(ByteArrayOutputStream xmlSource, String templateName) throws TransformerException {

		InputStream in = EmailService.class.getResourceAsStream("/" + templateName);

		Source transformSource = new StreamSource(in);

		StreamSource source = new StreamSource(new ByteArrayInputStream(xmlSource.toByteArray()));

		Transformer xslTransformer;

		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);

		TransformerFactory transfact = TransformerFactory.newInstance();

		xslTransformer = transfact.newTransformer(transformSource);

		Result result = new StreamResult(out);

		xslTransformer.transform(source, result);

		return writer.toString();
	}

	public void sendMail(String to, String cc, String from, String fromName, String subject, String htmlContent)
			throws AddressException, MessagingException, UnsupportedEncodingException {
		LOGGER.log(Level.INFO, "Inside EmailService:sendMail");
		// Get system properties
		Properties properties = System.getProperties();
		String host = "CHNEXCHMBX001.Techmahindra.com";
		LOGGER.log(Level.INFO, "Email host: " + host);
		properties.setProperty("mail.smtp.host", host);
		Session session = null;
		String port = "25";
		properties.setProperty("mail.smtp.port", port);
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		// Get the default Session object.
		session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				// System.out.println(" -- " +
				// NVOProperties.getInstance().getEmailFromFordPassword());
				return new PasswordAuthentication("TD0T14086", "TD@0T14086");// change accordingly
			}
		});

		// try{
		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		// message.setFrom(new
		// InternetAddress(NVOProperties.getInstance().getEmailFrom()));
		message.setFrom(new InternetAddress(from, fromName));
		// setting reply to for nvo help link 1469

		// Set To: header field of the header.
		LOGGER.log(Level.INFO, "Email to: " + to);
		/*
		 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		 */
		message.addRecipients(Message.RecipientType.TO, to);

		// Set CC
		if (cc != null && !cc.isEmpty()) {
			LOGGER.log(Level.INFO, "Email cc: " + cc);
			/*
			 * message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			 */
			message.addRecipients(Message.RecipientType.CC, cc);
			/*
			 * if(to==Constants.NVO_HELP_EMAIL_TO){ message.setReplyTo(new
			 * javax.mail.Address[] {new InternetAddress(cc, fromName)}); }
			 */

		}

		// Set Subject: header field
		LOGGER.log(Level.INFO, "Email subject: " + subject);
		message.setSubject(subject);

		// create message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Now set the actual message
		messageBodyPart.setContent(htmlContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();

		/*
		 * if(pdfSource != null) { messageBodyPart.setDataHandler(new
		 * DataHandler(pdfSource)); messageBodyPart.setFileName(pdfName);
		 * multipart.addBodyPart(messageBodyPart); }
		 */

		LOGGER.log(Level.INFO, "Set multipart");
		message.setContent(multipart);

		// Send message
		Transport.send(message);
		LOGGER.log(Level.INFO, "Message successfully sent");

		// }catch (MessagingException mex) {
		// LOGGER.log(Level.SEVERE, "Message failed to send");
		// mex.printStackTrace();
		// }
	}

	public void sendMailWithAttachment(String to, String cc, String from, String fromName, String subject,
			String htmlContent, Map<String, DataSource> attachmentFile)
			throws AddressException, MessagingException, UnsupportedEncodingException {
		LOGGER.log(Level.INFO, "Inside EmailService:sendMail");
		// Get system properties
		Properties properties = System.getProperties();
		String host = TMPProperties.getInstance().getSmtpHost();
		LOGGER.log(Level.INFO, "Email host: " + host);
		// properties.setProperty("mail.smtp.host", host);
		// Get the default Session object.
		// Session session = Session.getDefaultInstance(properties);*/

		Session session = null;
		if (TMPProperties.getInstance().useTestEmail()) {
			String port = TMPProperties.getInstance().getSmtpPort();
			properties.setProperty("mail.smtp.port", port);
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.socketFactory.port", "465");
			properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.setProperty("mail.smtp.host", host);
			from = "techmnvo@gmail.com";
			// Get the default Session object.
			session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					// System.out.println(" -- " +
					// NVOProperties.getInstance().getEmailFromFordPassword());
					return new PasswordAuthentication("techmnvo@gmail.com", "Welcome123$");// change accordingly
				}
			});
		} else {
			session = Session.getDefaultInstance(properties);
		}

		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		message.setFrom(new InternetAddress(from, fromName));

		// Set To: header field of the header.
		LOGGER.log(Level.INFO, "Email to: " + to);
		/*
		 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		 */
		message.addRecipients(Message.RecipientType.TO, to);

		// Set CC
		if (cc != null && !cc.isEmpty()) {
			LOGGER.log(Level.INFO, "Email cc: " + cc);
			/*
			 * message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
			 */
			message.addRecipients(Message.RecipientType.CC, cc);
		}

		// Set Subject: header field
		LOGGER.log(Level.INFO, "Email subject: " + subject);
		message.setSubject(subject);

		// create message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Now set the actual message
		messageBodyPart.setContent(htmlContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// messageBodyPart = new MimeBodyPart();

		for (Map.Entry<String, DataSource> entry : attachmentFile.entrySet()) {
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(entry.getValue()));
			messageBodyPart.setFileName(entry.getKey());
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
		}

		LOGGER.log(Level.INFO, "Set multipart");
		// message.setContent(multipart);

		// Send message
		Transport.send(message);
		LOGGER.log(Level.INFO, "Message successfully sent");

		// }catch (MessagingException mex) {
		// LOGGER.log(Level.SEVERE, "Message failed to send");
		// mex.printStackTrace();
		// }
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		EmailService.size = size;
	}

	public static String getRequirements() {
		return requirements;
	}

	public static void setRequirements(String requirements) {
		EmailService.requirements = requirements;
	}
}
