package com.revature.mariokartfighter_v2.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionService {
	private Connection connection;
	
	public ConnectionService() {
		try {
			if (connection == null || connection.isClosed()) {
	            try {
	                FileInputStream fis = new FileInputStream("C:\\Users\\jorla\\Desktop\\Revature Training\\MarioKartFighter_v2\\mariokartfighter_v2\\connection.prop");
	                Properties p = new Properties();
	                p.load(fis);
	                connection = DriverManager.getConnection(p.getProperty("hostname"), p.getProperty("username"), p.getProperty("password"));
	            }catch (IOException e) {
	                System.out.println("Could not get connection!");
	                System.out.println(e.getMessage());
	            }
	        }
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	@Override
	public void finalize() {
		try {
			connection.close();
		} catch (Exception e) {
			
		}
	}
}
