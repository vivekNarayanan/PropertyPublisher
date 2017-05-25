package com.properties.publisher.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.properties.publisher.enumconstants.Protocol;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface PublishProperty {
	String filePath();
	String fileName()  default "";
	String environemt()  default "";
	Protocol connectionProtocol()  default Protocol.HTTP;
}

