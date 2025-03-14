package com.sath.action;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.sath.Encryption.Encryptionpassword;
import com.sath.Encryption.RSA;
import com.sath.Encryption.libencryption;
import com.sath.dbconnectin.CommonSqlSet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class signupback extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		String user1 = request.getParameter("username");
		String pass = request.getParameter("password");
		String role = request.getParameter("role");
		System.out.printf(user1, pass);
		 SecretKey secretKey = null;
		try {
			secretKey = libencryption.generateKey();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         // Encrypt the password using the AES key
         String encryptedPassword = null;
		try {
			encryptedPassword = libencryption.encrypt(pass, secretKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String query = "insert into manager_customer(name,password,role,securitykey) value(?,?,?,?);";
		try {
			int value = CommonSqlSet.insertquerygeneratedid(query, user1, encryptedPassword, role,libencryption.keyToString(secretKey));
			if (value > 0) {
				
				out.write("success");
			} else {
				out.write("failure");
			}
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
