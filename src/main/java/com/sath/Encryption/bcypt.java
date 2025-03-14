package com.sath.Encryption;
import org.mindrot.jbcrypt.BCrypt;
public class bcypt {
	    public static String hashPassword(String password) {
	        return BCrypt.hashpw(password, BCrypt.gensalt(12)); 
	    }

	    public static boolean checkPassword(String plainPassword, String hashedPassword) {
	        return BCrypt.checkpw(plainPassword, hashedPassword);
	    }
	

}
