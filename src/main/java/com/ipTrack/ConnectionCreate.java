package com.ipTrack;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.PropertyResourceBundle;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class ConnectionCreate {
	public Connection getConnection() throws Exception {
		Connection connection = null;
		BasicDataSource dataSource = null;
		dataSource = getDataSource();
		connection = dataSource.getConnection();
		return connection;
	}

	// Make for connection
	public static BasicDataSource getDataSource() throws Exception {
		String baseDir = System.getenv("NB_HOME");
		String fileSep = System.getProperty("file.separator");
		StringBuffer databasePropFilepath = new StringBuffer(baseDir);
		databasePropFilepath.append(fileSep);
		databasePropFilepath.append("database.properties");
		PropertyResourceBundle database = new PropertyResourceBundle(
				new FileInputStream(databasePropFilepath.toString()));
		String DriverClassName = database.getString("dataobject.jdbc.driverClassName");
		String url = database.getString("dataobject.jdbc.url");
		String username = database.getString("jdbc.username");
		String password = database.getString("jdbc.password");
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(DriverClassName);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		basicDataSource.setMaxActive(2);
		return basicDataSource;
	}
}
