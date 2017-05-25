package com.properties.publisher.communicationimpl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.properties.publisher.communication.SendProperties;
import com.properties.publisher.communicationobjects.CommunicationVo;
import com.properties.publisher.logs.Logger;

public class SendPropertiesTcpImpl implements SendProperties, Runnable {

	private int socketPort;
	@SuppressWarnings("unused")
	private int packetSize;
	private Properties properties;
	private CommunicationVo communicationVo;
	private String activePropertyFile;

	public SendPropertiesTcpImpl() {

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
		// Thread sendProperties = new Thread(this);
		// sendProperties.start();
		ExecutorService executor = Executors.newFixedThreadPool(100);
		executor.execute(this);

	}

	@Override
	public void run() {
		ServerSocket welcomeSocket = null;
		try {
			Logger.log("Server Started");
			welcomeSocket = new ServerSocket(socketPort);
			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(outputStream);
				os.writeObject(communicationVo);
				byte[] data = outputStream.toByteArray();
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				outToClient.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (welcomeSocket != null) {
				try {
					welcomeSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	public List<byte[]> splitArray(byte[] data, int maxSubArraySize) {
		List<byte[]> result = new ArrayList<byte[]>();
		if (data == null || data.length == 0) {
			return result;
		}

		int from = 0;
		int to = 0;
		int slicedItems = 0;
		while (slicedItems < data.length) {
			to = from + Math.min(maxSubArraySize, data.length - to);
			byte[] slice = Arrays.copyOfRange(data, from, to);
			result.add(slice);
			slicedItems += slice.length;
			from = to;
		}
		return result;
	}
}
