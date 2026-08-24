package com.apps.quantitymeasurment.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Centralized loader for application.properties. Supports system-property
 * overrides so environment-specific values (e.g. CI, staging) can be
 * injected without editing the properties file.
 */
public class ApplicationConfig {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ApplicationConfig.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                logger.warn("application.properties not found; using defaults");
            } else {
                properties.load(input);
                logger.info("Loaded application.properties successfully");
            }
        } catch (IOException e) {
            logger.error("Failed to load application.properties", e);
        }
    }

    public static String get(String key, String defaultValue) {
        // System property takes precedence over the properties file
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }

    public static int getInt(String key, int defaultValue) {
        return Integer.parseInt(get(key, String.valueOf(defaultValue)));
    }

    public static String getRepositoryType() {
        return get("repository.type", "CACHE");
    }

    public static String getDbUrl() {
        return get("db.url", "jdbc:h2:mem:quantitydb;DB_CLOSE_DELAY=-1");
    }

    public static String getDbUsername() {
        return get("db.username", "sa");
    }

    public static String getDbPassword() {
        return get("db.password", "");
    }

    public static String getDbDriver() {
        return get("db.driver", "org.h2.Driver");
    }

    public static int getPoolMaxSize() {
        return getInt("db.pool.maxSize", 10);
    }
}