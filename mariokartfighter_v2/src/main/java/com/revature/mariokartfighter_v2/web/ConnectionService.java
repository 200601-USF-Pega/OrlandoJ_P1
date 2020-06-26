package com.revature.mariokartfighter_v2.web;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionService {
	private static final Logger logger = LogManager.getLogger(ConnectionService.class);
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
			logger.info("connection initialized");
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
