package com.aluradesafio.literalura;

import com.aluradesafio.literalura.principal.Principal;
import com.aluradesafio.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Es necesario colocar la implementación para que no salga error en el run
@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired //para que inyecte dependencias (_bases de datos)
	private LibroRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.muestraElMenu();
	}

}
