package io.jeidiiy.outflearn.util;

import org.springframework.util.StringUtils;

public class MyStringUtils extends StringUtils {
	public static String extractEmailLocal(String email) {
		return email.substring(0, email.indexOf('@'));
	}
}
