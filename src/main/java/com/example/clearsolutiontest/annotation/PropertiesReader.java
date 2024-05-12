package com.example.clearsolutiontest.annotation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static final Properties properties = new Properties();
    private static final String PROPERTY_FILE_NAME = "application.properties";

    static {
        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file " + PROPERTY_FILE_NAME + " not found in the classpath");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
