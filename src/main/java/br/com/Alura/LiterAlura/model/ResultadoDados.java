package br.com.Alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoDados(@JsonProperty("results") List<DadosLivro> resultados) {
}