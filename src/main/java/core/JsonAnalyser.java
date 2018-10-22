package core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonAnalyser {
    private ObjectMapper jsonMapper = new ObjectMapper();

    public <T> T parse(String jsonString, Class<T> jsonClass) {
        try {
            return jsonMapper.readValue(jsonString, jsonClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
