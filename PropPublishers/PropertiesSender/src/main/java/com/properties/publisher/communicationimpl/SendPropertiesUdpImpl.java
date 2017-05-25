package com.properties.publisher.communicationimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.properties.publisher.communication.SendProperties;
import com.properties.publisher.communicationobjects.CommunicationVo;

public class SendPropertiesUdpImpl implements SendProperties, Runnable {

	private int socketPort;
	private int packetSize;
	private String hostName;
	private Properties properties;
	private CommunicationVo communicationVo;
	private String activePropertyFile;

	public SendPropertiesUdpImpl() {
	}

	@Override
	public void configureConnection(String connectionfilePath) throws IOException {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(connectionfilePath);
		properties = new Properties();
		properties.load(inputStream);
		socketPort = Integer.parseInt(properties.getProperty("publishPort"));
		packetSize = Integer.parseInt(properties.getProperty("publishPacketSize"));
		hostName = properties.getProperty("hostName");
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
		Thread sendProperties = new Thread(this);
		sendProperties.start();
	}

	@Override
	public void run() {
		DatagramSocket sock = null;
		List<DatagramPacket> packetList = new ArrayList<DatagramPacket>();
		DatagramPacket request;
		try {
			sock = new DatagramSocket();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(communicationVo);
			byte[] data = outputStream.toByteArray();
			InetAddress aHost = InetAddress.getByName(hostName);
			if (data.length > packetSize) {
				int batchSize = (data.length / packetSize) + 1;
				List<byte[]> listOfArrays = splitArray(data, batchSize);
				Iterator<byte[]> itr = listOfArrays.iterator();
				while (itr.hasNext()) {
					byte[] bs = (byte[]) itr.next();
					DatagramPacket temp = new DatagramPacket(bs, bs.length, aHost, socketPort);
					temp.setData(bs);
					packetList.add(temp);
				}
			} else {
				request = new DatagramPacket(data, data.length, aHost, socketPort);
				request.setData(data);
				packetList.add(request);
			}

			while (true) {
				Iterator<DatagramPacket> itr = packetList.iterator();
				while (itr.hasNext()) {
					DatagramPacket datagramPacket = (DatagramPacket) itr.next();
					sock.send(datagramPacket);
				}

			}
		}

		catch (IOException e) {
			System.err.println("IOException " + e);
		} finally {
			if (sock != null) {
				sock.close();
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
