package br.com.Alura.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConverteDados implements IConverteDados{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T ObterDados(String json, Class<T> classe) {
        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON string is null or empty");
        }
        try {
            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
