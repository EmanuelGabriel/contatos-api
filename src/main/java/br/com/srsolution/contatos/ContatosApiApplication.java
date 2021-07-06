package br.com.srsolution.contatos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ContatosApiApplication implements WebMvcConfigurer, CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ContatosApiApplication.class, args);
	}

	/*
	 * Configuração Global para todas as requisições de outras origins(CrossOrigin)
	 */
	@Override
	public void addCorsMappings(CorsRegistry registro) {
		registro.addMapping("/**").allowedOrigins("*");
		/* Liberando todas as requisições/mapeamento para o usuário(cliente) */
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(new BCryptPasswordEncoder().encode("123"));
		
		
	}

}
