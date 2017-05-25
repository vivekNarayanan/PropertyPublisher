package com.properties.publisher.processor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

import com.properties.publisher.annotation.PublishProperty;
import com.properties.publisher.communication.SendProperties;
import com.properties.publisher.communicationimpl.SendPropertiesHttpImpl;
import com.properties.publisher.communicationimpl.SendPropertiesTcpImpl;
import com.properties.publisher.enumconstants.Protocol;
import com.properties.publisher.exception.ProtocolImplementationException;

@SupportedAnnotationTypes({ "com.properties.publisher.annotation.PublishProperty" })
public final class PublishPropertyProcessor extends AbstractProcessor {

	public PublishPropertyProcessor(Object obj) throws IOException, ProtocolImplementationException {
		Class<? extends Object> classOp = obj.getClass();
		Annotation an = (Annotation) classOp.getAnnotation(PublishProperty.class);
		if (an instanceof PublishProperty) {

			PublishProperty publishProp = (PublishProperty) an;
			Protocol protocol = publishProp.connectionProtocol();
			String connectionfilePath = publishProp.filePath();
			SendProperties sendProps = null;
			switch (protocol) {
			case TCP:
				sendProps = new SendPropertiesTcpImpl();
				sendProps.configureConnection(connectionfilePath);
				sendProps.publishProperties(connectionfilePath);
				break;
			case UDP:
				sendProps = new SendPropertiesTcpImpl();
				sendProps.configureConnection(connectionfilePath);
				sendProps.publishProperties(connectionfilePath);
				break;
			case HTTP:
				sendProps = new SendPropertiesHttpImpl();
				sendProps.configureConnection(connectionfilePath);
				sendProps.publishProperties(connectionfilePath);
				break;
			default:
				throw new ProtocolImplementationException("Invalid Protocol Exception");

			}

		}
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		return false;
	}

}