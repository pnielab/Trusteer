/**
 * 
 */
package com.trusteer.pniel.service.email;

/**
 * @author Pniel Abramovich
 *
 */
public interface EmailSenderService {

	public boolean sendEmail(String address, String subject, String body) throws Exception;

}
