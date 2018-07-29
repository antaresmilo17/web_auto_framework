package com.tigertext.automation.scenarioSettings;

import com.tigertext.automation.scenarioSettings.BrowserConfig;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import static org.testng.FileAssert.fail;

@ContextConfiguration(locations = {"classpath:seleniumContext.xml"})
public class BaseForTest extends AbstractTestNGSpringContextTests {
}