package com.sho.literalura;

import com.sho.literalura.Main.Principal;
import com.sho.literalura.repository.IAutoresRepository;
import com.sho.literalura.repository.ILibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	@Autowired
	private ILibrosRepository repositorioLibros;
	@Autowired
	private IAutoresRepository repositorioAutores;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal nuevo = new Principal(repositorioLibros, repositorioAutores);
		nuevo.muestraElMenu();
	}
}
