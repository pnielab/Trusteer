/**
 * 
 */
package com.trusteer.pniel.service.email.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trusteer.pniel.service.email.EmailSenderService;
import com.trusteer.pniel.service.email.EmailService;
import com.trusteer.pniel.service.email.model.Email;

/**
 * @author Pniel Abramovich
 *
 */
@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private EmailSenderService sender;

	@Override
	public void sendFilesChanged(List<String> files, List<String> emails) {
		if (emails != null) {
			Email email = createEmail(files, emails);
			try {
				sender.sendEmail(email.getTo(), email.getSubject(), email.getBody());
			}
			catch (Exception e) {
				logger.error("unable to send email to email list: {} with changed files: {}", emails, files, e);
			}
		}
	}

	/**
	 * 
	 * @param files, List of files to be sent to users that these files where changed
	 * @param emails, send to address
	 * @return Email object
	 */
	private Email createEmail(List<String> files, List<String> emails) {
		Email email = new Email();
		email.setBody(getBody(files));
		email.setSubject("files changes message");
		email.setTo(String.join(";", emails));
		return email;
	}

	/**
	 * @param files
	 * @return body.
	 */
	private String getBody(List<String> files) {
		StringBuilder sb = new StringBuilder();
		sb.append("given files have changed!").append("\n");
		for (String file : files) {
			sb.append(file).append("\n");
		}
		return sb.toString();
	}
}
