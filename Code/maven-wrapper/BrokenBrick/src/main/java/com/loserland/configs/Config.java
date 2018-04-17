package com.loserland.configs;

public interface Config {
    public String get(String key);
    public <T> T get(Class<T> cls, String key);
    public void put(String key, Object value);
}
