/**
 * 
 */
package com.trusteer.pniel.service.email.model;

import java.io.Serializable;

/**
 * @author Pniel Abramovich
 *
 */
public class Email implements Serializable {

	private static final long serialVersionUID = 1386099960984677147L;
	private String subject;
	private String body;
	private String to;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
