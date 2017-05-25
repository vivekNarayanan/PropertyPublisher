package com.properties.publisher.communicationobjects;

import java.io.Serializable;
import java.util.Properties;

public class CommunicationVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getSize() {
		return properties.size();
	}
}
