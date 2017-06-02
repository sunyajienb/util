package com.yajie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author admin
 * Ð£ÑéÓÊÏä¸ñÊ½
 * 
 */
public class CheckEmail {
	static final String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+"
			+ "[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	
	public static boolean checkIfEmail(String email) {
		if(email == null || email.trim().equals(""))
			return false;
		
		Pattern pattern = Pattern.compile(check);
		Matcher matcher = pattern.matcher(email);
		boolean is = matcher.matches();
		
		return is;
	}
}
