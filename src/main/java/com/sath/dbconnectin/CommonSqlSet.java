package com.sath.dbconnectin;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CommonSqlSet {
	final static String url = "jdbc:mysql://localhost:3306/servletfoodapp";
	final static String user = "root";
	final static String answer = "Sathish@123";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("MySQL Driver not found", e);
		}
	}

	public static Connection getconnection() throws SQLException {
		// TODO Auto-generated method stub

		return DriverManager.getConnection(url, user, answer);
	}

	

	public static JsonArray selectquery(String query, Object... params) {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		JsonArray jsonArray = new JsonArray();
		try {
			conn = getconnection();
			ps = conn.prepareStatement(query);
			if (params != null)
			{
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			}
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				JsonObject jsonObject = new JsonObject();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					jsonObject.addProperty(columnName, rs.getString(i));
				}

				jsonArray.add(jsonObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
				System.out.println("connection closed.........");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return jsonArray;
	}

	public static int deletequery(String query, Object... params) {
		// TODO Auto-generated method stub

		int rows = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getconnection();
			ps = conn.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			rows = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
				System.out.println("connection closed.........");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rows;
	}

	public static int insertquery(String query, Object... params) {
		// TODO Auto-generated method stub

		int rows = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getconnection();
			ps = conn.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			rows = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			
			return 0;
		}finally {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			System.out.println("connection closed.........");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		return rows;
	}

	public static int updatequery(String query, Object... params) {
		// TODO Auto-generated method stub

		int rows = 0;
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn = getconnection();
			 ps = conn.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			rows = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
				System.out.println("connection closed.........");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		return rows;
	}
	public static int insertquerygeneratedid(String query,Object... params) {
		// TODO Auto-generated method stub

		int rows = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int generated_id=0;
		try {
			conn = getconnection();
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			rows = ps.executeUpdate();
			 if (rows > 0) {
		            rs = ps.getGeneratedKeys();
		            if (rs.next()) {
		                generated_id = rs.getInt(1); 
		            }
		        }
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
			if(rs!= null)
				rs.close();
			System.out.println("connection closed.........");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		return generated_id;
	}



	
}
