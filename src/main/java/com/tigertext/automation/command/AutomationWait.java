package com.tigertext.automation.command;

import com.tigertext.automation.scenarioSettings.BrowserConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class AutomationWait {
    private FluentWait<WebDriver> webDriverWait;
    private WebDriver webDriver;
    public FluentWait<WebDriver> getWebDriverWait() {
        return webDriverWait;
    }
    @Autowired
    BrowserConfig browserConfig;

    public void init() {
        webDriverWait = new FluentWait<>(browserConfig.getWebDriver());
        webDriverWait.withTimeout(30, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .pollingEvery(250, TimeUnit.MILLISECONDS);
    }

    public FluentWait<WebDriver> getNewWait(int seconds){
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(browserConfig.getWebDriver());
        wait.withTimeout(seconds, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .pollingEvery(250, TimeUnit.MILLISECONDS);
        return wait;
    }

    public FluentWait<WebDriver> getNewWait(int seconds, int polling){
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(browserConfig.getWebDriver());
        wait.withTimeout(seconds, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .pollingEvery(polling, TimeUnit.MILLISECONDS);
        return wait;
    }

    public WebElement untilElementVisible(By locator)throws Exception {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement untilElementClickable(WebElement webElement,int seconds) {
        return getNewWait(seconds).until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public WebElement untilElementVisible(By locator, int seconds) throws Exception {
        return getNewWait(seconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> untilElementsVisible(By locator){
        return webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public List<WebElement> untilElementsVisible(By locator, int seconds){
        return getNewWait(seconds).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public boolean untilElementNotVisible(By locator){
        return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean untilElementNotVisible(By locator, int seconds){
        return getNewWait(seconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement untilElementClickable(By locator){
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement untilElementClickable(By locator, int seconds){
        return getNewWait(seconds).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement untilElementClickable(WebElement webElement) {
        return webDriverWait.
                until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public WebElement untilElementExist(By locator) {
        return webDriverWait.
                until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public Boolean untilElementDoesNotExist(By locator) {
        try {
            webDriverWait.until((WebDriver webDriver) -> {
                if (webDriver == null) {
                    throw new TimeoutException("null webDriver");
                }
                return webDriver.findElements(locator).isEmpty();
            });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public Boolean untilElementIsNotPresent(By locator) {
        try {
            webDriverWait.until((WebDriver webDriver) -> {
                if (webDriver == null) {
                    throw new TimeoutException("null webDriver");
                }
                return webDriver.findElements(locator).size() == 0;
            });
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<WebElement> untilAllElementExist(By locator) {
        return webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public WebElement untilPresenceOfElement(By locator){
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean untilURLContainsText(String value){
        try {
            webDriverWait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver){
                    return webDriver.getCurrentUrl().contains(value);
                }
            });
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean untilURLEqualsText(String value){
        try {
            webDriverWait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver webDriver){
                    return webDriver.getCurrentUrl().equals(value);
                }
            });
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void untilTextValueExists(By locator) throws Throwable{
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        getNewWait(30).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !webDriver.findElement(locator).getAttribute("value").trim().isEmpty();
            }
        });
        Thread.sleep(500);
    }

    public void untilPageLoads()throws Throwable{
        webDriverWait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor)wd).executeScript("return document.readyState").equals("complete"));
    }

    public void untilValuePopulates(By locator, String value) throws Throwable{
        webDriverWait.until(ExpectedConditions.textToBePresentInElementValue(locator, value));
    }

    public void untilTextIsVisible(By locator, String value){
        webDriverWait.until(ExpectedConditions.textToBePresentInElementLocated(locator, value));
    }

    public void waitForJSandJQueryToLoad() {

        try {
            WebDriverWait driverWait = new WebDriverWait(browserConfig.getWebDriver(), 2);

            ExpectedCondition<Boolean> expectation;
            expectation = new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driverjs) {

                    JavascriptExecutor js = (JavascriptExecutor) driverjs;
                    return js.executeScript("return((window.jQuery != null) && (jQuery.active === 0))").equals("true");
                }
            };
            driverWait.until(expectation);
        }
        catch (TimeoutException eTimeout) { }
        catch (WebDriverException eWebDriverException) { }
    }

}
