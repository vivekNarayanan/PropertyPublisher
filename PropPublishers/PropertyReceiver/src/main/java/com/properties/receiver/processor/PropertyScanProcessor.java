package com.properties.receiver.processor;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.annotation.processing.SupportedAnnotationTypes;

import com.properties.logger.Logger;
import com.properties.receiver.annotation.PropertyScan;
import com.properties.receiver.exception.PropertyNotFoundException;
import com.properties.receiver.exception.ProtocolImplementationException;

/**
 * 
 * @author vivenara
 *
 */
@SupportedAnnotationTypes({ "com.properties.receiver.annotation.PropertyScan" })
public class PropertyScanProcessor {
	/**
	 * This Constructor creates objects for SetPropertyProcessor and assign the
	 * beans that are annotated with @PropertyScan
	 * 
	 * @param basePackageName
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ProtocolImplementationException
	 * @throws PropertyNotFoundException
	 */
	public PropertyScanProcessor(Object... basePackageName)
			throws IOException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException,
			ProtocolImplementationException, PropertyNotFoundException {
		createProcessorObjects(basePackageName);
	}
	
	
	/**
	 * To Create Individual Objects for Set Property Processor. 
	 * @param basePackageName
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws ProtocolImplementationException
	 * @throws PropertyNotFoundException
	 */
	private void createProcessorObjects(Object... basePackageName)
			throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException,
			ProtocolImplementationException, PropertyNotFoundException {
		for (int i = 0; i < basePackageName.length; i++) {
			Class<? extends Object> classOp = basePackageName[i].getClass();
			Annotation an = (Annotation) classOp.getAnnotation(PropertyScan.class);
			if (an instanceof PropertyScan) {
				Logger.debug("Creating Objects !...");
				new SetPropertyProcessor(basePackageName[i]);
			}
		}
	}

}
