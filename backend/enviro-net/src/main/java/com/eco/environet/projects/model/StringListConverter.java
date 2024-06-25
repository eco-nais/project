package com.eco.environet.projects.model;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception ex) {
            // TODO - Handle the exception appropriately, maybe log it and return null
            return null;
        }
    }
     @Override
     public List<String> convertToEntityAttribute(String dbData) {
        try {
            return Arrays.asList(objectMapper.readValue(dbData, String[].class));
        } catch (Exception ex) {
            // TODO - Handle the exception appropriately, maybe log it and return null
            return null;
        }
    }
}