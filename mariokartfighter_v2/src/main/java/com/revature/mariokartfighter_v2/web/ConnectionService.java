package com.revature.mariokartfighter_v2.web;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionService {
	private static Connection connection;
	
	public static void initialize() {
		try  {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(MyProps.hostname, MyProps.username, MyProps.password);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		if (connection == null) {
			initialize();
		}
		return connection;
	}
	
	@Override
	public void finalize() {
		try {
			connection.close();
		} catch(Exception e) {
			
		}
	}
	
}
