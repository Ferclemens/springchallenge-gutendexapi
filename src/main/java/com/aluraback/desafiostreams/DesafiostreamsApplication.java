package com.aluraback.desafiostreams;

import com.aluraback.desafiostreams.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafiostreamsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafiostreamsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraMenu();
	}
}
