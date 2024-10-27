package com.xsis.master.crud.xsis_master_crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XsisMasterCrudApplication implements CommandLineRunner {

	@Autowired
	private ApplicationProperties properties;

	public static void main(String[] args) {
		SpringApplication.run(XsisMasterCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(properties.getUrl());
	}
}
