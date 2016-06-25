/**
 * 
 */
package com.trusteer.pniel.model;

import java.io.Serializable;

/**
 * @author Pniel Abramovich Holds url and calculated hash1.
 */
public class FileHolder implements Serializable {

	private static final long serialVersionUID = -5414750792590760776L;
	private String url;
	private String hash1;

	public FileHolder(String url, String hash1) {
		super();
		this.url = url;
		this.hash1 = hash1;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHash1() {
		return hash1;
	}

	public void setHash1(String hash1) {
		this.hash1 = hash1;
	}

}
