package com.kamal.demoAi.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kamal.Model.GeminiCommand;
import org.springframework.stereotype.Service;

@Service
public class GeminiParserService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Parses a JSON string into a GeminiCommand object.
     *
     * @param jsonString The JSON string containing action, message, receptionist keys.
     * @return GeminiCommand object with values set or empty object on error.
     */
    public GeminiCommand parseJsonToCommand(String jsonString) {
        try {
            // Deserialize JSON string into GeminiCommand object
            return objectMapper.readValue(jsonString, GeminiCommand.class);
        } catch (JsonProcessingException e) {
            System.err.println("Failed to parse JSON to GeminiCommand: " + e.getMessage());
            e.printStackTrace();
            // Return empty GeminiCommand if parsing fails
            return new GeminiCommand();
        }
    }
}
