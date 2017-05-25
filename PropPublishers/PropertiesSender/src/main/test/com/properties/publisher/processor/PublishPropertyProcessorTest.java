package com.properties.publisher.processor;

import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Test;

import com.properties.publisher.annotation.PublishProperty;
import com.properties.publisher.enumconstants.Protocol;
import com.properties.publisher.exception.ProtocolImplementationException;

@PublishProperty(filePath = "propertyContext.properties", connectionProtocol = Protocol.TCP)
public class PublishPropertyProcessorTest {

	
	@Test
	public void testPublishPropertyProcessor() throws IOException, ProtocolImplementationException {
		PublishPropertyProcessorTest test = new PublishPropertyProcessorTest();
		PublishPropertyProcessor prop = null;
		prop = new PublishPropertyProcessor(test);
		assertFalse("Annotation Success", prop.process(null, null));
		
	}

	
	

}
