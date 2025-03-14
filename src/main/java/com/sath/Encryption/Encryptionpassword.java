package com.sath.Encryption;

public class Encryptionpassword {

	public static String encryption(String password) {
		// TODO Auto-generated method stub
		//String password="Sathish123";
		return encrypting(password);
	}
	public static String decryption(String password) {
		// TODO Auto-generated method stub
		//String password="Sathish123";
		return decrypting(password);
	}

	private static String encrypting(String password) {
		StringBuilder stringbuilder=new StringBuilder();
		for(char ch:password.toCharArray())
		{
			
			if (Character.isUpperCase(ch)) {  
                char uppercase=((char) (ch + 1)); 
                if(uppercase>'Z')
                {
                	uppercase='A';
                }
                stringbuilder.append(uppercase);
            } else if (Character.isLowerCase(ch)) {  
                char lowercase = (char) (ch - 1);
                if (lowercase < 'a') {
                    lowercase = 'z'; 
                }
                stringbuilder.append(lowercase);
                
            } else if (Character.isDigit(ch)) {  
                char digits = (char) (ch + 1);
                if (digits > '9') {
                	digits = '0';
                    }
                stringbuilder.append(digits);
            } else {  
                stringbuilder.append(ch); 
            }
		}
		return stringbuilder.toString();
	}
	private static String decrypting(String password) {
		StringBuilder stringbuilder=new StringBuilder();
		for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                char uppercase = (char) (ch - 1);
                if (uppercase < 'A') 
                {
                	uppercase = 'Z'; 
                }
                stringbuilder.append(uppercase);
            } else if (Character.isLowerCase(ch)) {
                char lowercase = (char) (ch + 1);
                if (lowercase > 'z') 
                {
                	lowercase = 'a'; 
                }
                stringbuilder.append(lowercase);
            } else if (Character.isDigit(ch)) {
                char digits = (char) (ch - 1);
                if (digits < '0') 
                {
                	digits = '9'; 
                }
                stringbuilder.append(digits);
            } else {
                stringbuilder.append(ch);
            }
        }
		return stringbuilder.toString();
	}
}
