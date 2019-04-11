package com.capgemini.bankapp.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbUtil {
	private static String driverClassName = "com.mysql.jdbc.Driver";
	private static String dburl = "jdbc:mysql://localhost:3306/bankappdb";
	private static String username = "root";
	private static String password = "root";
	
	//private static String dburl ;
	//private static String username ;
	//private static String password ;
	
	static Connection connection;
	

	public static Connection getConnection() {
		
		try {
			Class.forName(driverClassName);
			if(connection == null) {
			connection = DriverManager.getConnection(dburl, username, password);
			connection.setAutoCommit(false);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Driver class not found");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static void commit() {
		try {
			if(connection != null)
				connection.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void rollback() {
		try {
			if(connection != null)
				connection.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

//	static {
//		try {
//			File propertiesFile = new File("dbConfig.properties");
//			FileReader reader = new FileReader(propertiesFile);
//
//			Properties properties = new Properties();
//			properties.load(reader);
//			reader.close();
//			
//			driverClassName = properties.getProperty("driverClassName");
//			dburl = properties.getProperty("dburl");
//			username = properties.getProperty("username");
//			password = properties.getProperty("password");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
