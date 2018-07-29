package com.tigertext.automation.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class DriverConfig {
    private static Config config;

    private static final DriverConfig dvConfig = new DriverConfig();

    private DriverConfig() {
        config = ConfigFactory.load("driver");
    }

    public static Config config() {
        return config;
    }
}
