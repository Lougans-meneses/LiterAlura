package br.com.Alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String idiomas;

    private Integer contadorDown;

    @OneToMany (mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores = new ArrayList<>();

    public Livro(){}

    public Livro(DadosLivro livro){
        this.titulo = livro.getTitle();
        this.contadorDown = livro.getDownloadCount();
        for(DadosAutor dadosAutor : livro.getAuthors()){
            Autor autor = new Autor(dadosAutor, this);
            this.autores.add(autor);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getContadorDown() {
        return contadorDown;
    }

    public void setContadorDown(Integer contadorDown) {
        this.contadorDown = contadorDown;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Livro: " +
                "Título = '" + titulo + '\'' +
                ", Idiomas =" + idiomas +
                ", Downloads realizados = " + contadorDown +
                ", Autores = " + autores;
    }
}
