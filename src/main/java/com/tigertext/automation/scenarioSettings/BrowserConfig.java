package com.tigertext.automation.scenarioSettings;

import com.tigertext.automation.config.DriverConfig;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.enums.BrowserTypes;
import com.tigertext.automation.webdriver.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;
import cucumber.api.Scenario;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BrowserConfig {
    private static final Logger LOG = LoggerFactory.getLogger(BrowserConfig.class);
    public static boolean isLoadTestInProgress = false;

    private Scenario scenario;

    private WebDriver webDriver;

    private BrowserTypes browserTypes;

    private BrowserTypes setBrowserType() {
        String myBrowserType = DriverConfig.config().getString("browser.type");

        if (!StringUtils.isEmpty(System.getProperty("browser.type"))) {
            myBrowserType = System.getProperty("browser.type");
        }
        if (myBrowserType.equals(BrowserTypes.CHROME.getBrowserName())) {
            browserTypes = BrowserTypes.CHROME;

        } else if(myBrowserType.equals(BrowserTypes.FIREFOX.getBrowserName())) {
            browserTypes = BrowserTypes.FIREFOX;

        } else if (myBrowserType.equals(BrowserTypes.REMOTE.getBrowserName())) {
            browserTypes = BrowserTypes.REMOTE;

        } else if (myBrowserType.equals(BrowserTypes.IE.getBrowserName())) {
            browserTypes = BrowserTypes.IE;

        } else {
            browserTypes = BrowserTypes.SAFARI;
        }
        return browserTypes;
    }

    public WebDriver initialize(Scenario scenario) {
        this.scenario = scenario;
        if(webDriver==null || (BrowserConfig.isLoadTestInProgress) && !TestConfig.Environment.getName().equals("lt")) {
            this.webDriver = WebDriverFactory.getInstance().getWebDriver(setBrowserType(), scenario);
            LOG.info("Initialized the browser for this scenario" + scenario.getName());
        }
        return webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public static String getScreenResolution() {
        return DriverConfig.config().getString("windows.screenResolution");
    }
}
