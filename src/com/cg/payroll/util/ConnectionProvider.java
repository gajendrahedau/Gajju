package com.cg.payroll.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {      
	public static Connection connection;
	public static Connection getdBConnection(){
			Properties dbProperties=new Properties();
			try {
				dbProperties.load(new FileInputStream(new File(".//resources//banking.properties")));
				String driver=dbProperties.getProperty("driver");
				String url=dbProperties.getProperty("url");
				String user=dbProperties.getProperty("user");
				String password=dbProperties.getProperty("password");
				Class.forName(driver);
				connection=DriverManager.getConnection(url, user, password);
				return connection;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
}
