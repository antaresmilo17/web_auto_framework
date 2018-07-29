package com.tigertext.automation.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class UserConfig {
    private static Config config;

    private static final UserConfig userConfig = new UserConfig();

    private UserConfig(){
        config = ConfigFactory.load("user");
    }

    public static Config config() {
        return config;
    }
}
