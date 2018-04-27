package com.loserland.configs;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class JsonSerializationUtils {

    ObjectMapper objectMapper;

    public JsonSerializationUtils() {
        this.objectMapper = new ObjectMapper();
    }

    public <T> T jsonToObject(String fileName, Class<T> cls) throws Exception {
        T object = null;
        File file = getResourceFile(fileName);
        object = objectMapper.readValue(file, cls);
        return object;
    }

    public <T> void objectToJson(String fileName, T object) throws Exception {
        File file = getResourceFile(fileName);
        objectMapper.writeValue(file, object);
    }

    public File getResourceFile(String fileName) throws Exception {
        File file = null;
        URL url = getClass().getClassLoader().getResource(fileName);
        if (url == null){
            URL parentURL = this.getClass().getResource("/");
            String parentPath = parentURL.toURI().getPath();
//                System.out.println("parentPath = " + parentPath);
            String filePath = parentPath+fileName;
//                System.out.println("filePath = " + filePath);
            file = new File(filePath);
            file.createNewFile();
        } else{
            String path = url.getFile();
            file = new File(path);
        }
        return file;
    }
}
