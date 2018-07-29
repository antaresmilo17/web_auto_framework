package com.tigertext.automation.webdriver.driver;

import com.tigertext.automation.enums.BrowserTypes;
import com.typesafe.config.Config;
import cucumber.api.Scenario;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import java.io.File;

public class FirefoxDriverConfig implements Driver {

    private static final String FIREFOX_DRIVER_NAME = "SELENIUM_FIREFOX_DRIVER";
    private final Config config;

    public FirefoxDriverConfig(Config properties) {
        this.config = properties;
    }

    @Override
    public WebDriver startBrowser(BrowserTypes browserType, Scenario scenario) {
        String[] window_size = BrowserConfig.getScreenResolution().split("x");
        int width = Integer.parseInt(window_size[0]);
        int height = Integer.parseInt(window_size[1]);
        //FirefoxProfile profile = new FirefoxProfile();
        //profile.setPreference("dom.max_chrome_script_run_time",0);
        //profile.setPreference("dom.max_script_run_time",0);
        DesiredCapabilities capabilities=DesiredCapabilities.firefox();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        capabilities.setCapability("marionette", true);
        //capabilities.setCapability(FirefoxDriver.PROFILE,profile);
        WebDriver driver = new FirefoxDriver(capabilities);
        Dimension d = new Dimension(width, height);
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(d);
        return driver;
    }

    private void setFireFoxDriverPath() {
        String path = config.getString("webdriver.gecko.driver");
        if(StringUtils.isEmpty(path)) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + File.separator + "geckodriver");
        }
        else { System.setProperty("webdriver.gecko.driver",path);};
    }

}
