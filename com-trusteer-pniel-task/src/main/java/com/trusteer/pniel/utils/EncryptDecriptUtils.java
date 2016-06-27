/**
 * 
 */
package com.trusteer.pniel.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Pniel Abramovich
 *
 */
public class EncryptDecriptUtils {

	private static MessageDigest md;

	static {
		try {
			md = MessageDigest.getInstance("SHA-1");
		}
		catch (NoSuchAlgorithmException e) {
			// can not happen
		}
	}

	public static String encrypt(String value) {
		return new String(md.digest(value.getBytes()));
	}

	public static String decrypt(String value) {
		throw new RuntimeException("not implemented");
	}
}
