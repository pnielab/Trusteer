/**
 * 
 */
package com.trusteer.pniel.service.email.impl;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.trusteer.pniel.service.email.EmailSenderService;

/**
 * @author Pniel Abramovich
 *
 */
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

	private static Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

	private String smtpHost = "smtp.sendgrid.net";
	private int smtpPort = 587;
	private String smtpAuthUser = "trusteer";
	private String smtpAuthPwd = "trusteerAa123456";
	private String from = "support@trusteer.com";
	private String backupMail = "backupmail@trusteer.com";

	private Session mailSession;

	private class SmtpAuthenticator extends javax.mail.Authenticator {

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(smtpAuthUser, smtpAuthPwd);
		}
	}

	@PostConstruct
	public void init() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp"); // mail.transport.protocol
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort); // 587
		props.put("mail.smtp.auth", "true");
		Authenticator authenticator = new SmtpAuthenticator();
		mailSession = Session.getDefaultInstance(props, authenticator);
	}

	@Override
	public boolean sendEmail(String address, String subject, String body) throws Exception {
		MimeMessage email = composeEmail(address, subject, body);
		boolean sent = sendEmailViaTransport(email);
		return sent;
	}

	private MimeMessage composeEmail(String to, String subject, String body) throws AddressException, MessagingException {

		MimeMessage message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(this.from));
		message.addRecipient(Message.RecipientType.BCC, new InternetAddress(backupMail));
		message.setSubject(subject);
		message.setText(body);

		// Support multiple email in TO field, currently separator is ;
		if (to != null) {
			if (to.contains(";")) {
				String[] toAddress = to.split(";");
				Address[] toAddressArray = new Address[toAddress.length];

				int i = 0;
				for (String emailAddress : toAddress) {
					toAddressArray[i] = new InternetAddress(emailAddress);
					i++;
				}
				message.addRecipients(Message.RecipientType.TO, toAddressArray);
			}

			else if (!StringUtils.isEmpty(to)) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
		}
		message.setContent(body, "text/html");
		return message;
	}

	private boolean sendEmailViaTransport(MimeMessage message) throws NoSuchProviderException {
		Transport transport = mailSession.getTransport();
		try {
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			return true;
		}
		catch (Throwable th) {
			logger.error("Unexpected error while sending message: {}", message, th);
		}
		finally {
			try {
				transport.close();
			}
			catch (Throwable th) {
				// Nothing to do here.
			}
		}
		return false;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public void setSmtpAuthUser(String smtpAuthUser) {
		this.smtpAuthUser = smtpAuthUser;
	}

	public void setSmtpAuthPwd(String smtpAuthPwd) {
		this.smtpAuthPwd = smtpAuthPwd;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setBackupMail(String backupMail) {
		this.backupMail = backupMail;
	}

}
