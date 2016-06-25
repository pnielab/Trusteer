/**
 * 
 */
package com.trusteer.pniel.service.resolver;

import java.util.List;

import com.trusteer.pniel.model.FileHolder;

/**
 * @author Pniel Abramovich
 *
 */
public interface FileResolver {

	/**
	 * resolve files hash.
	 * 
	 * @return list of all files and there hash
	 */
	List<FileHolder> resolve();

	/**
	 * Save file holder list and return the new or updated files.
	 * 
	 * @param files: List of FileHolder
	 * @return, updated or file list. that hash code was changed
	 */
	List<FileHolder> persist(List<FileHolder> files);

}
