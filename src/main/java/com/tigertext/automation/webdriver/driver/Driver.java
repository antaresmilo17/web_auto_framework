package com.tigertext.automation.webdriver.driver;

import com.tigertext.automation.enums.BrowserTypes;
import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;

public interface Driver {
    WebDriver startBrowser(BrowserTypes browserType, Scenario scenario);
}
