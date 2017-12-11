package com.tyroguide.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestWebService {

	public static void main(String[] args) {
		SpringApplication.run(RestWebService.class, args);
	}

}
