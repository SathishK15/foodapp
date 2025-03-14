package com.sath.Encryption;


	import java.util.Base64;

	public class base {

	    public static String encode(String password) {
	        return Base64.getEncoder().encodeToString(password.getBytes());
	    }

	    
	    public static String decode(String encodedPassword) {
	        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
	        return new String(decodedBytes);
	    }
	}

