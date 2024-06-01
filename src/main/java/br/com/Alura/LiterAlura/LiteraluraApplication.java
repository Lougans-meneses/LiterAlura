package br.com.Alura.LiterAlura;

import br.com.Alura.LiterAlura.principal.LiterAluraPrincipal;
import br.com.Alura.LiterAlura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
    @Autowired
    private LivroRepository repositorio;
    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LiterAluraPrincipal principal = new LiterAluraPrincipal(repositorio);
        principal.exibeMenu();
    }
}
