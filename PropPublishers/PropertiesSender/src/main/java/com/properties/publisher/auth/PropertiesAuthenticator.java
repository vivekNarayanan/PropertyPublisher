package com.properties.publisher.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sun.net.httpserver.BasicAuthenticator;

public class PropertiesAuthenticator extends BasicAuthenticator {
	private Properties properties;
	private String connectionfilePath;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getConnectionfilePath() {
		return connectionfilePath;
	}

	public void setConnectionfilePath(String connectionfilePath) {
		this.connectionfilePath = connectionfilePath;
	}

	public PropertiesAuthenticator(String method, String connectionfilePath) throws IOException {
		super(method);
		this.connectionfilePath = connectionfilePath;
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(connectionfilePath);
		properties = new Properties();
		properties.load(inputStream);
	}

	@Override
	public boolean checkCredentials(String user, String password) {
		String userArray = properties.getProperty("UserDtls");
		return userArray.contains(user.trim()+":"+password.trim());
	}

}