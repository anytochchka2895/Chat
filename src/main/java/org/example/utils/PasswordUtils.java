package org.example.utils;

import java.util.Base64;

public class PasswordUtils {

	public static String encodePassword(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}

}
