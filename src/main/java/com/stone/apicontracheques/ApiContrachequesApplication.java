package com.stone.apicontracheques;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiContrachequesApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(ApiContrachequesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

	@PostConstruct
    public void init(){
      TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
