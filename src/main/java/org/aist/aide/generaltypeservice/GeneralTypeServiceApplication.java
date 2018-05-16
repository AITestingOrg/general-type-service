package org.aist.aide.generaltypeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GeneralTypeServiceApplication {
	public static void main(String[] args) {
	    SpringApplication.run(GeneralTypeServiceApplication.class, args);
	}
}
