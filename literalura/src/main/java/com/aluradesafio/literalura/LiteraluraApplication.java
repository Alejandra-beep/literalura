package com.aluradesafio.literalura;

import com.aluradesafio.literalura.principal.Principal;
import com.aluradesafio.literalura.repository.AutorRepository;
import com.aluradesafio.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Es necesario colocar la implementación para que no salga error en el run
@SpringBootApplication
@EnableAutoConfiguration
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraElMenu();
	}

}
