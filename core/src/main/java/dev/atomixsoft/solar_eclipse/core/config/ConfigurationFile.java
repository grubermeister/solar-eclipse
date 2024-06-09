package dev.atomixsoft.solar_eclipse.core.config;

import java.util.Map;


public interface ConfigurationFile {
    void load(String path) throws Exception;
    void save(String path) throws Exception;

    String getValue(String key);
    void setValue(String key, String value);

    Map<String, String> getConfig();
    void setConfig(Map<String, String> values);
}
