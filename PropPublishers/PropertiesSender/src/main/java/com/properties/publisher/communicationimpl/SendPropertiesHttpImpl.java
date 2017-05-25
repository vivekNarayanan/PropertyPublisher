package com.properties.publisher.communicationimpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Properties;

import com.properties.publisher.auth.PropertiesAuthenticator;
import com.properties.publisher.communication.SendProperties;
import com.properties.publisher.communicationobjects.CommunicationVo;
import com.properties.publisher.httphandler.InfoHandler;
import com.properties.publisher.httphandler.PropertiesHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class SendPropertiesHttpImpl implements SendProperties {
	private int socketPort;
	@SuppressWarnings("unused")
	private int packetSize;
	private Properties properties;
	private CommunicationVo communicationVo;
	private String activePropertyFile;

	public SendPropertiesHttpImpl() {
		super();
	}

	@Override
	public void configureConnection(String connectionfilePath) throws IOException {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(connectionfilePath);
		properties = new Properties();
		properties.load(inputStream);
		socketPort = Integer.parseInt(properties.getProperty("publishPort"));
		packetSize = Integer.parseInt(properties.getProperty("publishPacketSize"));
		activePropertyFile = properties.getProperty("activePropertyFile");
		communicationVo = getCommunicationVo();
		inputStream.close();

		HttpServer server = null;
		server = HttpServer.create(new InetSocketAddress(socketPort), 0);
		HttpContext hc1 = server.createContext("/prop", new PropertiesHandler(communicationVo));
		HttpContext hc2 = server.createContext("/info", new InfoHandler(communicationVo));
		PropertiesAuthenticator propAuthenticator = new PropertiesAuthenticator("get", connectionfilePath);
		hc1.setAuthenticator(propAuthenticator);
		hc2.setAuthenticator(propAuthenticator);
		server.setExecutor(null); // creates a default executor
		server.start();

	}

	private CommunicationVo getCommunicationVo() throws IOException {
		communicationVo = new CommunicationVo();
		Properties prop = new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(activePropertyFile);
		prop.load(inputStream);
		communicationVo.setProperties(prop);
		inputStream.close();
		return communicationVo;
	}

	@Override
	public void publishProperties(String connectionfilePath) {
	}

}
