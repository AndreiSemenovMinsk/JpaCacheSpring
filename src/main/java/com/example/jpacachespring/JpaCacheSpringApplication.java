package com.example.jpacachespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
public class JpaCacheSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaCacheSpringApplication.class, args);
	}

}
