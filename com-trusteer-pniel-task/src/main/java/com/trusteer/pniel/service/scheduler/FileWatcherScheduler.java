/**
 * 
 */
package com.trusteer.pniel.service.scheduler;

/**
 * @author Pniel Abramovich
 *
 */
public interface FileWatcherScheduler {

	/**
	 * heck if files changed in a given rate,
	 * <p>
	 * Persist the changed files and send a message to recipient with the changed files.
	 */
	void run();

}
