package br.com.Alura.LiterAlura.repository;

import br.com.Alura.LiterAlura.model.Autor;
import br.com.Alura.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query("SELECT l.titulo, l.idiomas, a.nome FROM Livro l JOIN l.autores a")
    List<Object[]> findLivrosAndAutores();

    @Query("SELECT DISTINCT a.nome FROM Autor a")
    List<String> findAutores();

    @Query("SELECT DISTINCT a FROM Autor a WHERE a.nascAno <= :ano AND (a.mortAno IS NULL OR a.mortAno >= :ano)")
    List<Autor> findAutoresEmDeterminadoAno(@Param("ano") Integer ano);

    @Query("SELECT l FROM Livro l WHERE l.idiomas LIKE %:idioma%")
    List<Livro> findByIdioma(String idioma);
}
