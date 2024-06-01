package br.com.Alura.LiterAlura.service;

public interface IConverteDados {
    <T> T ObterDados(String json, Class<T> classe);
}
