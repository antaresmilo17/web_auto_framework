package com.tigertext.automation.webdriver.driver;

import com.saucelabs.saucerest.SauceREST;
import com.tigertext.automation.config.DriverConfig;
import com.tigertext.automation.enums.BrowserTypes;
import cucumber.api.Scenario;
import org.apache.commons.lang3.StringUtils;
import org.apache.xpath.operations.Bool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.*;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.typesafe.config.Config;
public class RemoteDriverConfig implements Driver{
    private final Config properties;
    public static String remoteSessionID;
    public RemoteDriverConfig(Config properties) {
        this.properties = properties;
    }

    @Override
    public WebDriver startBrowser(BrowserTypes browserType, Scenario scenario) {
        try {
            return createRemoteURL(scenario);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private WebDriver createRemoteURL(Scenario scenario) throws IOException {
        DesiredCapabilities caps = null;
        String RemoteBrowser = System.getProperty("remote.browser");
        String RemotePlatform = System.getProperty("remote.platform");
        String RemoteVersion = System.getProperty("remote.version");
        String RemoteTimeout = System.getProperty("remote.timeout");
        String ScreenResolution = System.getProperty("remote.screenResolution");
        String userName = System.getProperty("remote.stackusername");
        String accessKey = System.getProperty("remote.stackkey");
        boolean browserStack = properties.getBoolean("remote.browserstack");
        boolean grid = properties.getBoolean("remote.grid");
        int RemoteTimeoutSeconds = properties.getInt("remote.timeout");

        if(StringUtils.isEmpty(RemoteBrowser)) {
            RemoteBrowser = properties.getString("remote.browser");
        }
        if(StringUtils.isEmpty(RemotePlatform)) {
            RemotePlatform = properties.getString("remote.platform");
        }
        if(StringUtils.isEmpty(RemoteVersion)) {
            RemoteVersion = properties.getString("remote.version");
        }
        if(!StringUtils.isEmpty(RemoteTimeout)){
            RemoteTimeoutSeconds = Integer.parseInt(RemoteTimeout);
        }
        if ((RemoteBrowser == null || RemoteBrowser.isEmpty())){
            RemoteBrowser = "CHROME";
        }
        if ((RemotePlatform == null || RemotePlatform.isEmpty())){
            RemotePlatform = "macOS 10.12";
        }
        if ((RemoteVersion == null || RemoteVersion.isEmpty())){
            RemoteVersion = "58.0";
        }
        if ((ScreenResolution == null || ScreenResolution.isEmpty())){
            ScreenResolution = BrowserConfig.getScreenResolution();
        }
        if(RemoteBrowser.equals("CHROME") || RemoteBrowser.equals("chrome")) {

            caps = DesiredCapabilities.chrome();
            caps.setCapability("idleTimeout", RemoteTimeoutSeconds);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("allow-running-insecure-content");
            options.addArguments("test-type");
            options.addArguments("start-maximized");
            options.addArguments("disable-infobars");
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);
            caps.setCapability(ChromeOptions.CAPABILITY, options);

            if (browserStack) {
                caps.setCapability("browser", "chrome");
                caps.setCapability("browser_version", "65.0.3325.181");
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "10");
                caps.setCapability("resolution", "1024x768");
                caps.setCapability("browserstack.debug", "true");
                caps.setCapability("browserstack.networkLogs", "true");
            }
            else
            {

                caps.setCapability("platform", RemotePlatform);
                caps.setCapability("version", RemoteVersion);
                caps.setCapability("screenResolution", ScreenResolution);


            }

        }
        if(RemoteBrowser.equals("FIREFOX") || RemoteBrowser.equals("firefox")) {
            caps = DesiredCapabilities.firefox();

            if (browserStack) {
                caps.setCapability("browser", "Firefox");
                caps.setCapability("browser_version", "60.0 beta");
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "10");
                caps.setCapability("resolution", "1024x768");
                caps.setCapability("browserstack.debug", "true");
                caps.setCapability("browserstack.networkLogs", "true");
            }

            caps.setCapability("platform", RemotePlatform);
            caps.setCapability("version", RemoteVersion);
            caps.setCapability("screenResolution", ScreenResolution);
            caps.setCapability("idleTimeout", RemoteTimeoutSeconds);

        }
        if(RemoteBrowser.equals("IE") || RemoteBrowser.equals("ie")) {

            caps = DesiredCapabilities.internetExplorer();

            if (browserStack) {
                caps.setCapability("browser", "IE");
                caps.setCapability("browser_version", "11.0");
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "8.1");
                caps.setCapability("resolution", "1280x1024");
                caps.setCapability("browserstack.debug", "true");
                caps.setCapability("browserstack.networkLogs", "true");
            }
            else {
                caps.setCapability("platform", RemotePlatform);
                caps.setCapability("version", RemoteVersion);
                caps.setCapability("screenResolution", ScreenResolution);


            }
            caps.setCapability("idleTimeout", RemoteTimeoutSeconds);
            caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);
            caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
            caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);
        }
        if(RemoteBrowser.equals("SAFARI") || RemoteBrowser.equals("safari")) {
            caps = DesiredCapabilities.safari();
            if (browserStack) {
                caps.setCapability("browser", "Safari");
                caps.setCapability("browser_version", "11.0");
                caps.setCapability("os", "OS X");
                caps.setCapability("os_version", "High Sierra");
                caps.setCapability("resolution", "1024x768");
                caps.setCapability("browserstack.debug", "true");
                caps.setCapability("browserstack.networkLogs", "true");
            }
            else {
                caps.setCapability("platform", RemotePlatform);
                caps.setCapability("version", RemoteVersion);
                caps.setCapability("screenResolution", ScreenResolution);
                caps.setCapability("idleTimeout", RemoteTimeoutSeconds);
            }
        }

        String remoteURL = "";
        if(browserStack)
        {
            remoteURL = "https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
        }
        else if(grid)
        {
            remoteURL = properties.getString("remote.gridurl");
        }
        else {
               remoteURL = properties.getString("remote.url");
        }
        RemoteWebDriver browserStackDriver= new RemoteWebDriver(new URL(remoteURL),caps);
        //This enables uploading a file from local to Sauce Labs
        if(!browserStack) {
            browserStackDriver.setFileDetector(new LocalFileDetector());
            remoteSessionID = browserStackDriver.getSessionId().toString();
            updateSauceJobInfo(scenario);
        }
        return browserStackDriver;
    }

    public void updateSauceJobInfo(Scenario scenario){
        SauceREST sauceAPI = new SauceREST(DriverConfig.config().getString("remote.user"), DriverConfig.config().getString("remote.userapikey"));
        HashMap<String, Object> updates = new HashMap<>();
        //Gets the user name from environment variable
        String currentUser = System.getProperty("user.name");
        //Gets the name of the scenario being ran
        String scenarioName = scenario.getName();
        String scenarioID = scenario.getId();
        String[] splitID = scenarioID.split(";");
        //Tries to get the tag if available, if not use Scenario name
        String tagExecuted = System.getProperty("cucumber.options");
        if(tagExecuted != null){
            scenarioName = tagExecuted.replace("--tags ", "") + " Suite";
        }
        //Tries to get the Jenkins build number, if not, assume Jenkins is not used.
        String jenkinsBuildNumber = System.getenv("BUILD_NUMBER");
        String jobName = "";
        if(jenkinsBuildNumber != null) {
            jenkinsBuildNumber = "Jenkins #" + jenkinsBuildNumber;
            jobName = jenkinsBuildNumber + " - " + "Feature: " + splitID[0].replace("-", " ") + " - " + scenarioName;
        }
        else{
            jobName = currentUser + " - " + "Feature: " + splitID[0].replace("-", " ") + " - " + scenarioName;
        }
        updates.put("name", jobName);
        sauceAPI.updateJobInfo(remoteSessionID, updates);
    }
}