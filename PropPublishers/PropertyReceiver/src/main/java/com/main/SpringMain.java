package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class SpringMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringMain.class, args);
	}

}
