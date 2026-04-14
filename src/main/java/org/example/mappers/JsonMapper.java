package org.example.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.ProblemEntity;
import org.example.entity.Test;
import org.example.exception.RepositoryException;

import java.util.Collections;
import java.util.List;

public final class JsonMapper {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonMapper() {}

    public static String toJson(List<Test> tests) {
        try {
            return MAPPER.writeValueAsString(tests);
        } catch (JsonProcessingException e) {
            throw new RepositoryException("Ошибка сериализации task в JSON", e);
        }
    }

    public static ProblemEntity fromJson(String json) {

        if (json == null)
            return null;

        try {
            return MAPPER.readValue(json, ProblemEntity.class);
        } catch (JsonProcessingException e) {
            throw new RepositoryException("Ошибка парсинга JSON в task", e);
        }
    }

    public static List<Test> fromJsonToListOfTests(String json) {

        if (json == null)
            return Collections.emptyList();

        try {
            return MAPPER.readValue(json, new TypeReference<List<Test>>() {});
        } catch (JsonProcessingException e) {
            throw new RepositoryException("Ошибка парсинга JSON в List<Test>", e);
        }
    }

}