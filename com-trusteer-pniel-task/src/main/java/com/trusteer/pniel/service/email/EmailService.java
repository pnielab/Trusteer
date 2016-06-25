/**
 * 
 */
package com.trusteer.pniel.service.email;

import java.util.List;

/**
 * @author Pniel Abramovich
 *
 */
public interface EmailService {

	void sendFilesChanged(List<String> files, List<String> emails);
}
