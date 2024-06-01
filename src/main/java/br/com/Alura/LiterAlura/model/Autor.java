package br.com.Alura.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer nascAno;
    private Integer mortAno;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    public Autor() {
    }

    public Autor(DadosAutor dadosAutor, Livro livro) {
        this.nome = dadosAutor.getName();
        this.nascAno = dadosAutor.getBirthYear();
        this.mortAno = dadosAutor.getDeathYear();
        this.livro = livro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNascAno() {
        return nascAno;
    }

    public void setNascAno(Integer nascAno) {
        this.nascAno = nascAno;
    }

    public Integer getMortAno() {
        return mortAno;
    }

    public void setMortAno(Integer mortAno) {
        this.mortAno = mortAno;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "Autor: " + nome + "\n" +
                "Ano de nascimento: " + nascAno + "\n"+
                "________________________________________________\n";

    }
}
