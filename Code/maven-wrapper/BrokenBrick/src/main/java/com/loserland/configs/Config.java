package com.loserland.configs;

import java.util.List;

public interface Config {
    String get(String key);
    <T> T get(Class<T> cls, String key);
    List<String> getList(String key);
    <T> List<T> getList(Class<T> cls, String key);
    void put(String key, Object value);
}
