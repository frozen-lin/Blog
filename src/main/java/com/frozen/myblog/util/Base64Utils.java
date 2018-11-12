package com.frozen.myblog.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Utils {
	public static String encode(String str) {
		try {
			return Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String decode(String b64) {
		try {
			return new String(Base64.getDecoder().decode(b64),"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
