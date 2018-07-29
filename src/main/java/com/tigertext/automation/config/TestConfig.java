package com.tigertext.automation.config;

import com.tigertext.automation.config.DriverConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;

public class TestConfig {
    private static final String PRODUCTION = "prod";
    private static final String INTEGRATION = "dev";
    private static final String STAGE = "stage";
    private static Config config;
    private static final TestConfig testConfig = new TestConfig();

    private TestConfig()  {
        String envType = System.getProperty("environment.type");
        if(StringUtils.isEmpty(envType)) {
            envType = DriverConfig.config().getString("environment.type");
        }
        config = ConfigFactory.load(envType + "TestConfig");
        config.checkValid(ConfigFactory.defaultReference());
    }

    public static class Environment{

        public static String getName() {

            String envType = System.getProperty("environment.type");
            if(StringUtils.isEmpty(envType)) {
                return DriverConfig.config().getString("environment.type");
            } else {
                return envType;
            }
        }

        public boolean isProduction() {
            return PRODUCTION.equals(getName());
        }

        public boolean isDev() {
            return PRODUCTION.equals(getName());
        }

        public boolean isStage() {
            return PRODUCTION.equals(getName());
        }

        public static String getEmail() {
            return config.getString("environment."+getName()+ ".account.email");
        }

        public static String getPassword() {
            return config.getString("environment."+getName()+ ".account.password");
        }

        public static String getUserEmail(String User) {
            return config.getString("environment."+getName()+ "." + User + ".email");
        }

        public static String getUserPassword(String User) {
            return config.getString("environment."+getName()+ "." + User + ".password");
        }

        public static String getUserFirstName(String User) {
            return config.getString("environment."+getName()+ "." + User + ".firstName");
        }

        public static String getApiKey() {
            String apiKey = System.getProperty("key");
            if(apiKey == "" || apiKey == null)
                return config.getString("environment."+getName()+ ".apikey");
            else
                return apiKey;
        }

        public static String getSecret() {
            String secret = System.getProperty("secret");
            if(secret == "" || secret == null)
                return config.getString("environment."+getName()+ ".secret");
            else
                return secret;
        }

        public static String getDevApi() {
            return config.getString("environment."+getName()+ ".devapi");
        }

        public static String getUserLastName(String User) {
            return config.getString("environment."+getName()+ "." + User + ".lastName");
        }

        public static String getUserDisplayName(String User) {
            return config.getString("environment."+getName()+ "." + User + ".displayName");
        }

        public static String getURL() {
            return config.getString("environment."+getName()+ ".url");
        }

        public static String getLoadTestUsers(){ return config.getString("environment."+getName()+".LoadTestUsers.users"); }
    }
}
