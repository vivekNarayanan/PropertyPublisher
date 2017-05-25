package com.properties.receiver.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

import com.properties.logger.Logger;
import com.properties.publisher.communicationobjects.CommunicationVo;
import com.properties.publisher.enumconstants.Protocol;
import com.properties.receiver.annotation.SetProperty;
import com.properties.receiver.exception.PropertyNotFoundException;
import com.properties.receiver.exception.ProtocolImplementationException;

/**
 * 
 * @author vivenara
 *
 */
@SupportedAnnotationTypes({ "com.properties.receiver.anntation.ReceiveProperty" })
public final class SetPropertyProcessor extends AbstractProcessor {

	private CommunicationVo communicationVo;
	private Properties properties;
	private String connectionfilePath = "connection.Properties";
	private Protocol protocol;

	public SetPropertyProcessor(Object obj) throws IOException, ClassNotFoundException, IllegalArgumentException,
			IllegalAccessException, ProtocolImplementationException, PropertyNotFoundException {
		Class<? extends Object> classOp = obj.getClass();
		getDataFromServer();
		for (Field f : classOp.getDeclaredFields()) {
			Annotation[] annotations = f.getAnnotationsByType(SetProperty.class);
			for (Annotation a : annotations) {
				SetProperty publishProp = (SetProperty) a;
				String propertyName = publishProp.property();
				String propertyObject = null;
				switch (protocol) {
				case TCP:
					propertyObject = communicationVo.getProperties().getProperty(propertyName);
					break;
				case HTTP:
					propertyObject = getPropertyFromHTTP(propertyName);

					break;

				default:
					throw new ProtocolImplementationException(
							"Invalid Protocol: Please check the connection Properties and configure proper Protocol.");
				}
				f.setAccessible(true);
				f.set(obj, propertyObject);
			}
		}
	}

	private String getPropertyFromHTTP(String propertyName) throws PropertyNotFoundException {
		String result = null;
		String httpConfigURL = properties.getProperty("httpConfigURL");
		String webPage = httpConfigURL + "prop?propKey=" + propertyName;
		String name = properties.getProperty("httpConfigUserName");
		String password = properties.getProperty("httpConfigPassword");
		String authString = name + ":" + password;
		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		try{
		URL url = new URL(webPage);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		InputStream is = urlConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuffer sb = new StringBuffer();
		while ((numCharsRead = isr.read(charArray)) > 0) {
			sb.append(charArray, 0, numCharsRead);
		}
		result = sb.toString();
		}catch(Exception e){
			throw new PropertyNotFoundException(propertyName+" Property not Found");
		}

		return result;
	}

	private void getDataFromServer() throws IOException, ClassNotFoundException, ProtocolImplementationException {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(connectionfilePath);
		properties = new Properties();
		properties.load(inputStream);
		int socketPort = Integer.parseInt(properties.getProperty("publishPort"));
		String hostName = properties.getProperty("hostName");
		String protocolStr = properties.getProperty("protocol");

		if ("TCP".equals(protocolStr.trim().toUpperCase())) {
			protocol = Protocol.TCP;
		} else if ("HTTP".equals(protocolStr.trim().toUpperCase())) {
			protocol = Protocol.HTTP;
		}
		switch (protocol) {
		case TCP:
			Socket clientSocket = new Socket(hostName, socketPort);
			ObjectInputStream intStream = new ObjectInputStream(clientSocket.getInputStream());
			communicationVo = (CommunicationVo) intStream.readObject();
			clientSocket.close();
			break;
		case HTTP:
			Logger.log("Getting Data through HTTP:");
			Logger.debug("Connection to hostName" + hostName);
			break;

		default:
			throw new ProtocolImplementationException(
					"Invalid Protocol: Please check the connection Properties and configure proper Protocol.");
		}

	}

	public static Annotation[] findClassAnnotation(Class<?> clazz) {
		return clazz.getAnnotations();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		return false;
	}

}