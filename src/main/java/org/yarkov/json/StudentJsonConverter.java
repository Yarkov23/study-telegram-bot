package org.yarkov.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.yarkov.dto.StudentDTO;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@Component
public class StudentJsonConverter implements AttributeConverter<StudentDTO, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String convertToDatabaseColumn(StudentDTO data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert to Json", e);
        }
    }

    public StudentDTO convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<StudentDTO>() {
            });
        } catch (IOException ex) {
            return null;
        }
    }
}
