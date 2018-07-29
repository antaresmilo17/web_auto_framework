package com.tigertext.automation.scenarioSettings;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.webElements.HomePageLocators;
import com.tigertext.automation.webdriver.driver.WebDriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ScenarioSettings {

    @Autowired
    BrowserConfig browserConfig;
    private WebDriverCommands webDriverCommands;
    private HomePageLocators homePageLocators;
    private AutomationWait automationWait;
    private static final Logger LOG = LoggerFactory.getLogger(ScenarioSettings.class);

    @Before
    public void before(Scenario scenario) {
        browserConfig.initialize(scenario);
        webDriverCommands = new WebDriverCommands().webDriver(browserConfig.getWebDriver()).browserConfig(browserConfig).build();
        homePageLocators = new HomePageLocators();
    }

    @After
    public void after(Scenario scenario) throws Throwable {
        //Below stores the results of whether the Scenario passed or failed.
        if(scenario.isFailed()){
            WebDriverFactory.passFailMap.put("didTestSuitePass" + scenario.getId(), false);
        }
        else {
            WebDriverFactory.passFailMap.put("didTestSuitePass"+ scenario.getId(), true);
        }
        screenshot(scenario);
        if(BrowserConfig.isLoadTestInProgress && !TestConfig.Environment.getName().equals("lt")) { return;}
//        if(webDriverCommands.isPresent(homePageLocators.getSignOutButton()) && !TestConfig.Environment.getName().equals("lt")){
//            try {
//                webDriverCommands.waitUntilElementVisible(homePageLocators.getSignOutButton(),60);
//                webDriverCommands.click(homePageLocators.getSignOutButton());
//            }catch (Throwable e){}
//        }
        //if we are deleting cookies, then we need a refresh after due to the window being stale. Without it, an alert appears
        //displaying "Are you sure you want to log out?" and the following test would fail.
       browserConfig.getWebDriver().manage().deleteAllCookies();
       browserConfig.getWebDriver().navigate().refresh();
       webDriverCommands.waitForAlertToAppearAndAccept();
       webDriverCommands.waitForAlertToAppearAndAccept();

    }

    private void screenshot(Scenario scenario) {
        if(scenario.isFailed()) {
            try {
                LOG.info("The testcase failed");
                webDriverCommands.embedScreenShot(scenario, webDriverCommands.takeScenarioShot());
            } catch (Exception e) {
                LOG.info("Fail to embed Scenario screenshot");
            }
        }
        else {
            LOG.info("Fail to embed Scenario screenshot " + scenario.getStatus()+" : " + scenario.getName() );
        }
    }
}
