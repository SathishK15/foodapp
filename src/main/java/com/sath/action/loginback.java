package com.sath.action;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sath.Encryption.Encryptionpassword;
import com.sath.Encryption.RSA;
import com.sath.Encryption.libencryption;
import com.sath.dbconnectin.CommonSqlSet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class loginback
 */

public class loginback extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		JsonArray jsonArray = new JsonArray();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		response.setContentType("text/plain");
		String user1 = request.getParameter("username");
		String pass = request.getParameter("password");

		// String encryptpass=Encryptionpassword.decryption(pass);
		// System.out.println("edecryption"+encryptpass);
		String query = "SELECT id, name, password, role,securitykey FROM manager_customer WHERE name=? ";

		try {
			jsonArray = CommonSqlSet.selectquery(query, user1);
			if (jsonArray.size() > 0) {
				JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
				System.out.println("befir" + jsonObject.get("password").getAsString());
				// pasword deecryption method
				String password = jsonObject.get("password").getAsString();
				String secretKeyString = jsonObject.get("securitykey").getAsString();

				// Convert the string secret key back to a SecretKey object
				SecretKey secretKey = libencryption.stringToKey(secretKeyString);

				// Decrypt the stored password using the AES key
				String decryptedPassword = libencryption.decrypt(password, secretKey);
				System.out.println(decryptedPassword);
				if (pass.equals(decryptedPassword)) {
					session.setAttribute("generatedId", jsonObject.get("id").getAsInt());
					session.setAttribute("username", jsonObject.get("name").getAsString());
					session.setAttribute("password", jsonObject.get("password").getAsString());
					session.setAttribute("role", jsonObject.get("role").getAsString());
					String roles = jsonObject.get("role").getAsString();
					System.out.println(roles);
					out.write(roles);
				} else {
					out.write("failure");
				}
			} else {
				out.write("failure");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("error");

		}

	}

}
