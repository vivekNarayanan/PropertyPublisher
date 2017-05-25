package com.properties.main;

import java.io.IOException;

import com.properties.publisher.annotation.PublishProperty;
import com.properties.publisher.enumconstants.Protocol;
import com.properties.publisher.exception.ProtocolImplementationException;
import com.properties.publisher.processloader.PropertyProcessLoader;

@PublishProperty(filePath = "propertyContext.properties", connectionProtocol = Protocol.HTTP)
public class Main {
	public static void main(String[] args) throws IOException, ProtocolImplementationException {
		Main m = new Main();
		PropertyProcessLoader.publish(m);
	}

}
