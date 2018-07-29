package com.tigertext.automation.commonUtils;


import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.config.DriverConfig;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.CreateGroupPageLocators;
import com.tigertext.automation.webElements.HomePageLocators;
import com.tigertext.automation.webElements.LoginPageLocators;
import com.tigertext.automation.webElements.MessagePageLocators;
import com.tigertext.automation.webdriver.driver.ChromeDriverConfig;
import cucumber.api.Scenario;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import static com.tigertext.automation.commonUtils.StaticData.hashMapMessages;
import static com.tigertext.automation.commonUtils.StaticData.hashMapURL;
import static com.tigertext.automation.commonUtils.StaticData.hasMapForums;

@Component
public class CommonMessaging {

    private boolean debug;
    private BrowserConfig browserConfig;
    private MessagePageLocators messagePageLocators;
    AutomationWait automationWait;
    WebDriver webDriver;
    private WebDriverCommands webDriverCommands;
    Logger logger = Logger.getLogger(CommonMessaging.class.getName());

    @Autowired
    public CommonMessaging(AutomationWait automationWait,
                           MessagePageLocators messagePageLocators,
                           BrowserConfig browserConfig) {
        this.automationWait = automationWait;
        this.browserConfig = browserConfig;
        this.messagePageLocators = messagePageLocators;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
        this.debug = webDriverCommands.getDebug();
    }

    public void cancelSendingMessage(WebDriverCommands webDriverCommands, String user) throws Throwable{
        automationWait.untilPageLoads();
        String message = generateMessage(webDriverCommands, user);
        if(user.contains(hasMapForums.get("qaForumName")))
            webDriverCommands.actionsClick(messagePageLocators.getNewMessageButton());
        searchUserMessaging(webDriverCommands,user);
        webDriverCommands.enterText(messagePageLocators.getTextInputField(), message);
        webDriverCommands.click(messagePageLocators.getCancelMessageButton());
    }

    public void searchUserMessaging(WebDriverCommands webDriverCommands,String user) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.actionsClick(messagePageLocators.getSearchMessageList());
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.enterText(messagePageLocators.getSearchInputField(),user);
        automationWait.waitForJSandJQueryToLoad();
        automationWait.untilElementVisible(messagePageLocators.getUserDropDownValue(),120);
        automationWait.untilElementClickable(messagePageLocators.getUserDropDownValue(),120);
        webDriverCommands.click(messagePageLocators.getUserDropDownValue());
        automationWait.untilPageLoads();
    }

    public void enterMessageAndSend(WebDriverCommands webDriverCommands, String message) throws Throwable {
        automationWait.untilElementClickable(messagePageLocators.getTextInputField(),60);
        automationWait.untilElementVisible(messagePageLocators.getTextInputField(),60);

//        if(DriverConfig.config().getString("remote.browser").toLowerCase().equals("ie"))
//        {
//            logger.info("IE SENDING MESSAGE");
//            webDriverCommands.enterTextByActionSendKeys(messagePageLocators.textInputFieldPath,message);
//        }

        webDriverCommands.enterText(messagePageLocators.getTextInputField(), message);

        automationWait.untilElementClickable(messagePageLocators.getSendMessageButton(),60);
        automationWait.untilElementVisible(messagePageLocators.getSendMessageButton(),60);
        webDriverCommands.actionsClick(messagePageLocators.getSendMessageButton());
       // webDriverCommands.click(messagePageLocators.getSendMessageButton());
    }

    public void sendRandomMessageToMultipleUser(WebDriverCommands webDriverCommands,String user1, String user2) throws Throwable{
        String message = generateMessage(webDriverCommands, user1);
        searchUserMessaging(webDriverCommands,user1);
        searchUserMessaging(webDriverCommands,user2);
        enterMessageAndSend(webDriverCommands,message);
        automationWait.untilElementClickable(messagePageLocators.getNewMessageButton());
        automationWait.untilElementNotVisible(messagePageLocators.getCancelMessageButton());
        automationWait.untilPageLoads();
    }

    private String generateMessage(WebDriverCommands webDriverCommands, String user) throws Throwable {
        String message = RandomStringUtils.randomAlphanumeric(17).toUpperCase();
        if(user.equals("Everyone"))
            message = "[Everyone]: " + message;
        hashMapMessages.put(user,message);
        if(!user.contains(hasMapForums.get("qaForumName")))
            webDriverCommands.actionsClick(messagePageLocators.getNewMessageButton());
        return message;
    }

    public void sendRandomMessage(WebDriverCommands webDriverCommands,String user) throws Throwable{
        automationWait.untilPageLoads();
        String message = generateMessage(webDriverCommands, user);
        if(!user.contains(hasMapForums.get("qaForumName"))) {
            searchUserMessaging(webDriverCommands, user);
        }
        automationWait.untilPageLoads();
        enterMessageAndSend(webDriverCommands, message);
    }

    public void sendPriorityMessage(WebDriverCommands webDriverCommands,String user) throws Throwable{
        automationWait.untilPageLoads();
        if(webDriverCommands.isVisible(messagePageLocators.getPriorityMessageIntro())) {
            webDriverCommands.click(messagePageLocators.getPriorityMessageIntro());
            webDriverCommands.actionsClick(messagePageLocators.getPriorityMessage());
            webDriverCommands.actionsClick(messagePageLocators.getPriorityMessageConfirm());
        }
        else {
            webDriverCommands.click(messagePageLocators.getPriorityMessage());
        }
        sendRandomMessage(webDriverCommands,user);
    }

    public void sendMessageType(WebDriverCommands webDriverCommands,String user,String type) throws Throwable{
        automationWait.untilPageLoads();
        String message = hashMapURL.get(type);

        logger.info("SEND MESSAGE TYPE TXT: "+ message);

        if(!user.contains(hasMapForums.get("qaForumName"))) {
            automationWait.untilElementVisible(messagePageLocators.getNewMessageButton(),60);
            automationWait.untilElementClickable(messagePageLocators.getNewMessageButton(),60);
            webDriverCommands.click(messagePageLocators.getNewMessageButton());
            searchUserMessaging(webDriverCommands, user);
        }
        enterMessageAndSend(webDriverCommands, message);
    }

    public void sendEmojiMessage(WebDriverCommands webDriverCommands,String user) throws Throwable{
        if(!user.contains(hasMapForums.get("qaForumName"))) {
            automationWait.untilElementVisible(messagePageLocators.getNewMessageButton(),60);
            automationWait.untilElementClickable(messagePageLocators.getNewMessageButton(),60);
            webDriverCommands.click(messagePageLocators.getNewMessageButton());
            searchUserMessaging(webDriverCommands, user);
        }
        automationWait.untilElementVisible(messagePageLocators.getEmojiButton(),60);
        automationWait.untilElementClickable(messagePageLocators.getEmojiButton(),60);
        webDriverCommands.click(messagePageLocators.getEmojiButton());
        automationWait.untilElementVisible(messagePageLocators.getEmojiPicker(),60);
        automationWait.untilElementVisible(messagePageLocators.getEmotionPointUp(),60);
        automationWait.untilElementClickable(messagePageLocators.getEmotionPointUp(),60);
        webDriverCommands.click(messagePageLocators.getEmotionPointUp());
        automationWait.untilElementVisible(messagePageLocators.getSendMessageButton(),60);
        automationWait.untilElementClickable(messagePageLocators.getSendMessageButton(),60);
        webDriverCommands.click(messagePageLocators.getSendMessageButton());
        automationWait.waitForJSandJQueryToLoad();
    }
}
