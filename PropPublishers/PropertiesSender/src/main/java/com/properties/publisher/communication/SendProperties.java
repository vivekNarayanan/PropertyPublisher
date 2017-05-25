package com.properties.publisher.communication;

import java.io.IOException;

public interface SendProperties {

	void configureConnection(String connectionfilePath) throws IOException;

	void publishProperties(String connectionfilePath);

}
