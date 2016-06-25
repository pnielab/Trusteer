/**
 * 
 */
package com.trusteer.pniel.service.schedular;

/**
 * @author Pniel Abramovich
 *
 */
public interface FileWatcherSchedular {

	/**
	 * heck if files changed in a given rate,
	 * <p>
	 * Persist the changed files and send a message to recipient with the changed files.
	 */
	void run();

}
