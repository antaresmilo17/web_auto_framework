package com.tigertext.automation.command;

import com.tigertext.automation.commonUtils.CommonMessaging;
import com.tigertext.automation.config.DriverConfig;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.config.UserConfig;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.rmi.runtime.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class WebDriverCommands {
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CommonMessaging.class.getName());
    WebDriver webDriver;
    BrowserConfig browserConfig;
    AutomationWait automationWait;
    boolean debug = false;

    public WebDriverCommands webDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
        return this;
    }

    public WebDriverCommands browserConfig(BrowserConfig browserConfig) {
        this.browserConfig = browserConfig;
        return this;
    }

    public WebDriverCommands  automationWait(AutomationWait automationWait) {
        this.automationWait = automationWait;
        return this;
    }


    public WebDriverCommands build() {
        this.webDriver = browserConfig.getWebDriver();
        return this;
    }

    public Cookie getCookie(String cookieName) {
        return webDriver.manage().getCookieNamed(cookieName);
    }

    public void navigate(String url, WebDriver webDriver) {
        webDriver.get(url);
    }

    public void navigate(String url) {
        logger.info("My webdriver instance is " + webDriver);
        webDriver.get(url);
    }

    public void refresh(){
        ((JavascriptExecutor) webDriver).executeScript("location.reload();");
    }

    public void click(WebElement locator) {
        automationWait.untilElementClickable(locator);
        locator.click();
    }

    public boolean getDebug(){
        return debug;
    }

    public void click(By locator, WebDriver webDriver) {
        click((WebElement) webDriver.findElement(locator));
    }

    public void click(By locator) throws Throwable{
        automationWait.untilElementExist(locator);
        WebElement clickableElement = automationWait.untilElementClickable(locator);
        clickableElement.click();
    }

    public void waitForAlertToAppearAndAccept()
    {
        try {
            WebDriverWait alertCheck = new WebDriverWait(browserConfig.getWebDriver(), 3);
            if (alertCheck.until(ExpectedConditions.alertIsPresent()) == null) {
                System.out.println("alert was not present");
            } else {
                Alert alert = webDriver.switchTo().alert();
                alert.accept();
            }
        }catch (Throwable e){}
    }

    public void waitForMultipleAlertToAppearAndAccept()
    {
        WebDriverWait alertCheck = new WebDriverWait(browserConfig.getWebDriver(), 3);
        try {
            if (alertCheck.until(ExpectedConditions.alertIsPresent()) == null) {
                System.out.println("alert was not present");
            } else {
                while(alertCheck.until(ExpectedConditions.alertIsPresent())!=null) {
                    Alert alert = webDriver.switchTo().alert();
                    alert.accept();
                }
            }
        }catch (Throwable e){}
    }

    public void waitUntilElementVisible(By locator, int seconds) throws Throwable{
        automationWait.untilElementVisible(locator, seconds);
    }

    public void selectListByVisibleText(By locator, String text) throws Throwable{
        Select select = new Select(findElement(locator));
        select.selectByVisibleText(text);
    }

    public void actionsClick(By locator) throws Throwable{
        WebElement clickableElement = automationWait.untilElementClickable(locator);
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", clickableElement);
    }

    public void actionsSendkeys(String elementId, String message) throws Throwable{
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("document.querySelector('" + elementId + "').value='" + message + "';");
    }

    public void clickAndWait(By locator,int seconds) throws Throwable{
        automationWait.untilElementExist(locator);
        WebElement clickableElement = automationWait.untilElementClickable(locator);
        clickableElement.click();
    }

    public void click(String locator) throws Throwable{
        click(By.xpath(locator));
    }

    public List<WebElement> findElements(By selector) {
        return webDriver.findElements(selector);
    }

    public List<String> getIdsFromElements(By locator){
        List<String> results = new ArrayList<>();
        for (WebElement we : webDriver.findElements(locator)){
            results.add(we.getAttribute("id"));
        }
        return results;
    }

    public void enterText(By locator, String inputData) throws Throwable {
        actionsClick(locator);
        webDriver.findElement(locator).clear();
        webDriver.findElement(locator).sendKeys(inputData);
    }

    public void enterTextByActionSendKeys(String elementXpath, String inputData) throws Throwable
    {
        actionsSendkeys(elementXpath,inputData);
    }

    public void enterText(WebElement element, String inputData) {
        automationWait.untilElementClickable(element);
        element.clear();
        element.sendKeys(inputData);
    }

    public void enterTextForAll(By locator, String inputData){
        automationWait.untilElementsVisible(locator);
        for(WebElement element : webDriver.findElements(locator)){
            enterText(element, inputData);
        }
    }

    public void enterPath(By locator, String path){
        webDriver.findElement(locator).sendKeys(path);
    }

    public void enterKeyboardKey(By locator, String key){
        try {
            automationWait.untilElementVisible(locator);
            switch (key.trim().toLowerCase()) {
                case "enter":
                case "return":
                    findElement(locator).sendKeys(Keys.RETURN);
                    break;
                case "esc":
                case "escape":
                    findElement(locator).sendKeys(Keys.ESCAPE);
                    break;
                default:
                    System.out.println("Unable to find key: " + key);
            }
        }catch(Exception e){

        }
    }

    public WebElement findElement(By selector) {
        return webDriver.findElement(selector);
    }

    public boolean isVisible(By locator){
        try{
            automationWait.untilElementVisible(locator);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVisible(By locator, int seconds){
        try{
            automationWait.untilElementVisible(locator, seconds);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean isElementsVisible(By locator, int seconds){
        try{
            automationWait.untilElementsVisible(locator, seconds);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean isElementsVisible(By locator){
        try{
            automationWait.untilElementsVisible(locator);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean isNotVisible(By locator){
        try{
            return automationWait.untilElementNotVisible(locator);
        }catch(Exception e){
            return false;
        }
    }

    public boolean isNotVisible(By locator, int seconds){
        try{
            return automationWait.untilElementNotVisible(locator, seconds);
        }catch(Exception e){
            return false;
        }
    }

    public boolean isEnabled(By locator){
        try{
            automationWait.untilElementVisible(locator);
            return webDriver.findElement(locator).isEnabled();
        } catch(Exception e){
            return false;
        }
    }

    public boolean isClickable(By locator){
        try{
            automationWait.untilElementClickable((WebElement) webDriver.findElement(locator));
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public boolean isPresent(By locator){
        if (webDriver.findElements(locator).size() != 0) return true;
        else return false;
    }


    public boolean isClickable(By locator, int seconds){
        try{
            automationWait.untilElementClickable((WebElement) webDriver.findElement(locator), seconds);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public String getCSS(By locator, String key){
        try {
            automationWait.untilElementVisible(locator);
            return webDriver.findElement(locator).getCssValue(key);
        }catch(Exception e){
            return null;
        }
    }

    public void acceptAlert(){
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    public String getURL(){
        return webDriver.getCurrentUrl();
    }

    public void sleep(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        }catch(Exception e){

        }
        if (debug) System.out.println("finished sleeping for " + seconds + " second(s)");
    }

    public void sleep(double seconds){
        long milli = 0;
        try{
            milli = (long) (seconds * 1000.0);
            Thread.sleep(milli);
        }catch(Exception e){

        }
        if (debug) System.out.println("finished sleeping for " + milli + " milli second(s)");
    }

    public WebDriver switchFrameByElement(By element){
        webDriver.switchTo().frame((WebElement) webDriver.findElement(element));
        return webDriver;
    }

    public WebDriver switchFrameById(String id){
        webDriver.switchTo().frame(id);
        return webDriver;
    }

    public WebDriver switchFrameByInt(int id){
        webDriver.switchTo().frame(id);
        return webDriver;
    }
    public WebDriver switchDefaultFrame(){
        webDriver.switchTo().defaultContent();
        return webDriver;
    }

    public WebDriver switchParentFrame(){
        webDriver = webDriver.switchTo().parentFrame();
        return webDriver;
    }

    public String getRandomEmail(String account){
        String result = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.json"));
            String file = "";
            String line = "";
            while ((line = reader.readLine()) != null){
                file += line;
            }
            JsonObject js = new JsonParser().parse(file).getAsJsonObject();
            result = js.get(account).getAsString();
        }catch (FileNotFoundException e){
            logger.info("Unable to find the file");
        }catch(IOException e){
            logger.info("Unable to read lines from file");
        }
        return result;
    }

    public void clear(By locator){
        try {
            automationWait.untilElementVisible(locator);
            webDriver.findElement(locator).clear();
        }catch(Exception e){

        }
    }

    public boolean isChecked(By locator){
        try {
            String result = webDriver.findElement(locator).getAttribute("checked");
            System.out.println(result);
            if (result.trim().isEmpty() || result.equalsIgnoreCase("checked") || result.equalsIgnoreCase("true")){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    public boolean isDisplayed(By locator){
        try {
            String result = webDriver.findElement(locator).getAttribute("style");
            if (result.trim().equalsIgnoreCase("display: none;")){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            return true;
        }
    }

    public boolean isElementDisplayed(By locator){
        try{
            WebElement result = webDriver.findElement(locator);
            return result.isDisplayed();
        }catch (Exception e){return false;}
    }

    public void switchWindow(int index){
        Set<String> windows = webDriver.getWindowHandles();
        String currentWindow = webDriver.getWindowHandle();
        if (index != -1) {
            System.out.println("Windows: " + windows.toString());
            if (index < windows.size()) {
                webDriver.switchTo().window(windows.toArray()[index].toString());
            } else {
                System.out.println("Sorry, unable to switch to index " + index + " when there is only " + windows.size() + " windows.");
            }
        }else{
            int counter = 0;
            boolean checker = false;
            for(String window : windows){
                if (window == currentWindow){
                    checker = true;
                    if (counter < windows.size()) {
                        webDriver.switchTo().window(windows.toArray()[counter].toString());
                    } else {
                        System.out.println("Sorry, unable to switch to index " + counter + " when there is only " + windows.size() + " windows.");
                    }
                    break;
                }
                counter += 1;
            }
            if(checker){
                System.out.println("currentWindow("+currentWindow+") does not equal anything in windows("+windows.toString()+")");
            }
        }
    }

    public void switchToActiveWindow(){
        Set<String> windows = webDriver.getWindowHandles();
        webDriver.switchTo().window(windows.toArray()[1].toString());
    }


    public void closeChildWindow() {
        try {
            Set<String> windows = webDriver.getWindowHandles();
            Iterator<String> iter = windows.iterator();
            String[] winNames=new String[windows.size()];
            int i=0;
            while (iter.hasNext()) {
                winNames[i]=iter.next();
                i++;
            }

            if(winNames.length > 1) {
                for(i = winNames.length; i > 1; i--) {
                    webDriver.switchTo().window(winNames[i - 1]);
                    webDriver.close();
                }
            }
            webDriver.switchTo().window(winNames[0]);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void switchToParentWindow(){
        String parentHandle = webDriver.getWindowHandle();
        webDriver.switchTo().window(parentHandle);
    }

    public void closeAllActiveWindow(){
        webDriver.quit();
    }

    public void closeActiveWindow(){
        ((JavascriptExecutor) webDriver).executeScript("close();");
    }

    public void scrollUp(){
        ((JavascriptExecutor) webDriver).executeScript("scroll(0, -250);");
    }

    public void scrollDown(){
        ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }



    public void checkCheckboxUsingJavascript(String id){
        ((JavascriptExecutor) webDriver).executeScript(String.format("document.getElementById('%s').click();", id));
    }

    public boolean isSelected(By locator){
        try {
            automationWait.untilElementVisible(locator);
        }catch(Exception e){

        }
        return webDriver.findElement(locator).isSelected();
    }

    public void dragAndDrop(By locator, int x, int  y) throws Throwable{
        try{
            WebElement element = automationWait.untilElementVisible(locator);
            new Actions(webDriver).clickAndHold(element).moveByOffset(x, y).release().perform();
        } catch(Exception e){
            throw new Exception("Cannot find element");
        }
    }

    public String getText(By locator) throws Throwable{
        String result = null;
        try{
            WebElement element = automationWait.untilElementVisible(locator);
            result = element.getText();
        }catch(Exception e){
            throw new Exception("Unable to get element");
        }
        return result;
    }

    public List<String> getTextFromAll(By locator) throws Throwable{
        List<String> values = new ArrayList<>();
        List<WebElement> elements;
        try {
            elements = automationWait.untilElementsVisible(locator, 7);
            for (WebElement element : elements) {
                values.add(element.getText().trim());
            }
        }catch(TimeoutException e){
            System.out.println("Unable to find elements");
        }
        return values;
    }

    public void takeScreenshot() throws Throwable{
        File path = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        SimpleDateFormat sfrmt = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String filename = sfrmt.format(calendar.getTime()) + ".png";
        String absPath = System.getProperty("user.dir");
        System.out.println(absPath+UserConfig.config().getString("screenshot.path")+filename);
        FileUtils.copyFile(path, new File(absPath+UserConfig.config().getString("screenshot.path")+filename));
    }

    public void embedScreenShot(Scenario scenario, byte[] screenShot){
        scenario.embed(screenShot,"image/png");
    }

    public byte[] takeScenarioShot() {
        return((TakesScreenshot) browserConfig.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public WebElement getElementFromElements(By locator, int index){
        List<WebElement> elements = automationWait.untilElementsVisible(locator);
        return elements.get(index);
    }

    public String getValue(By locator) throws Throwable{
        return webDriver.findElement(locator).getAttribute("value").trim();
    }

    public void waitForPageLoad() throws Throwable{
        automationWait.untilPageLoads();
    }

    public void waitForValue(By locator, String value) throws Throwable{
        automationWait.untilPageLoads();
        automationWait.untilValuePopulates(locator, value);
    }

    public void refreshPage(){
        ((JavascriptExecutor) webDriver).executeScript("location.reload();");
    }
}
