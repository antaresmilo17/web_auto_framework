package com.tigertext.automation.webdriver.driver;

import com.saucelabs.saucerest.SauceREST;
import com.tigertext.automation.config.DriverConfig;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.enums.BrowserTypes;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class WebDriverFactory {

    private static final WebDriverFactory webDriverfactory = new WebDriverFactory();
    public static Map<String, Boolean> passFailMap = new HashMap<>();
    private final Map<String, WebDriver> webDriverMap = new HashMap<>();
    private final static List<WebDriver>  listWebDriver = new ArrayList<>();
    private static final Logger LOG = LoggerFactory.getLogger(WebDriverFactory.class);
    private static Properties properties;

    private WebDriverFactory() {
        Runtime.getRuntime().addShutdownHook(new Thread(new BrowserClosing()));
    }

    public static WebDriverFactory getInstance(){
        return webDriverfactory;
    }

    private class BrowserClosing implements Runnable {
        public void run() {
            LOG.info("close the broswers");
            closeTheBrowser();
        }
    }

    private void closeTheBrowser() {
        String isRemote = DriverConfig.config().getString("browser.type");
        if(isRemote.contains("remote")){
            SauceREST sauceAPI = new SauceREST(DriverConfig.config().getString("remote.user"),
                    DriverConfig.config().getString("remote.userapikey"));

            if(passFailMap.values().contains(false)){
                sauceAPI.jobFailed(RemoteDriverConfig.remoteSessionID);
            }
            else{
                sauceAPI.jobPassed(RemoteDriverConfig.remoteSessionID);
            }
        }

        if(!BrowserConfig.isLoadTestInProgress){
            for ( WebDriver notClosedWebDriver: listWebDriver){
                notClosedWebDriver.quit();
            }
        }
        for (WebDriver driver : webDriverMap.values()) {
            try {
                if (driver != null) {
                    driver.quit();
                }
            } catch (UnreachableBrowserException e) {
                // Just quiting
            } finally {
                LOG.info("closing the browser");
            }
        }
    }

    public WebDriver getWebDriver(final BrowserTypes browserTypes, Scenario scenario) {
        WebDriver webDriver = webDriverMap.get(browserTypes.getBrowserName());
        if (webDriver == null || (BrowserConfig.isLoadTestInProgress) && !TestConfig.Environment.getName().equals("lt")) {
            // we can add the logic for location (Grid/Local) here
            LOG.info("No browser defined");
            final Driver driver = createDriver(browserTypes);
            webDriver = driver.startBrowser(browserTypes, scenario);
            webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            this.webDriverMap.put(browserTypes.getBrowserName(), webDriver);
            listWebDriver.add(webDriver);
        }
        return webDriver;
    }

    private static Driver createDriver(final BrowserTypes browserTypes) {
        Driver driver;

        switch (browserTypes) {
            case FIREFOX:
                driver = new FirefoxDriverConfig(DriverConfig.config());
                break;
            case REMOTE:
                driver = new RemoteDriverConfig(DriverConfig.config());
                break;
            case CHROME:
            default:
                driver = new ChromeDriverConfig(DriverConfig.config());
                break;
        }
        return driver;
    }
}
