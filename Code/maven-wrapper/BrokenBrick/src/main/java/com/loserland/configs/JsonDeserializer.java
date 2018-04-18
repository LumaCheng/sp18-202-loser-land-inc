package com.loserland.configs;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonDeserializer {

    ObjectMapper objectMapper;

    public JsonDeserializer() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> T toObject(String fileName, Class<T> cls) throws IOException {
        T object = null;
        File file = getResourceFile(fileName);
        object = objectMapper.readValue(file, cls);
        return object;
    }

    public File getResourceFile(String fileName) {
        String path = getClass().getClassLoader().getResource(fileName).getFile();
        return new File(path);
    }
}
