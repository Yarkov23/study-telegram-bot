package org.yarkov.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yarkov.entity.Theme;
import javax.persistence.AttributeConverter;
import java.io.IOException;

public class ThemeJsonConverter implements AttributeConverter<Theme, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String convertToDatabaseColumn(Theme theme) {
        try {
            return objectMapper.writeValueAsString(theme);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    public Theme convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<Theme>() {
            });
        } catch (IOException ex) {
            return null;
        }
    }
}
