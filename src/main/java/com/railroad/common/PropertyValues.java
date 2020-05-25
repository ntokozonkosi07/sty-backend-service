package com.railroad.common;

import lombok.Cleanup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyValues {

    public String getValue(String key) throws IOException {
        Properties prop = new Properties();
        String propertyFileName = "config.properties";

        @Cleanup InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
        if(inputStream != null)
            prop.load(inputStream);
        else
            throw new FileNotFoundException(String.format("Properties file %s not found", propertyFileName));

        return prop.getProperty(key);
    }
}
