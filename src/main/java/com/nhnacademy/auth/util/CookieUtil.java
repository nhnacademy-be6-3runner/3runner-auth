package com.nhnacademy.auth.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

	public static Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setHttpOnly(true);
		cookie.setPath("/");

		return cookie;
	}
}
