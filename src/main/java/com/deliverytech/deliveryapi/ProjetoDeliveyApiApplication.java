package com.deliverytech.deliveryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoDeliveyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoDeliveyApiApplication.class, args);
        SpringApplication app = new SpringApplication(ProjetoDeliveyApiApplication.class);
        app.setAdditionalProfiles("dev");
        app.run(args);
	}

}
