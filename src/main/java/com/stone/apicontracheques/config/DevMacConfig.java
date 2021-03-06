package com.stone.apicontracheques.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.stone.apicontracheques.services.DbService;

@Configuration
@Profile("devMac")
public class DevMacConfig {
	@Autowired
	private DbService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if ("create".equals(strategy)) {
			dbService.instantiateTestDatabase();
			return true;
		}
		return false;
	}
	
}
