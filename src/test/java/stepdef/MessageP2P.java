package stepdef;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.commonUtils.CommonGroup;
import com.tigertext.automation.commonUtils.CommonMethod;
import com.tigertext.automation.commonUtils.CommonMessaging;
import com.tigertext.automation.scenarioSettings.BaseForTest;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import java.sql.Time;

import static com.tigertext.automation.commonUtils.StaticData.hashMapMessages;
import static com.tigertext.automation.commonUtils.StaticData.hashMapURL;

public class MessageP2P extends BaseForTest{

    private WebDriverCommands webDriverCommands;
    private BrowserConfig browserConfig;
    private MessagePageLocators messagePageLocators;
    private MessageInfoPageLocators messageInfoPageLocators;
    private PrintPreviewPageLocators printPreviewPageLocators;
    private MutePageLocators mutePageLocators;
    private CommonMethod commonMethod;
    private CommonMessaging commonMessaging;
    private HomePageLocators homePageLocators;
    private CommonGroup commonGroup;
    AutomationWait automationWait;
    WebDriver webDriver;
    String userType = "";

    @Autowired
    public MessageP2P(
            BrowserConfig browserConfig,
            AutomationWait automationWait,
            CommonMessaging commonMessaging,
            MessageInfoPageLocators messageInfoPageLocators,
            MessagePageLocators messagePageLocators,
            PrintPreviewPageLocators printPreviewPageLocators,
            MutePageLocators mutePageLocators,
            HomePageLocators homePageLocators,
            CommonMethod commonMethod,
            CommonGroup commonGroup
    ) {
        this.browserConfig = browserConfig;
        this.automationWait = automationWait;
        this.commonMessaging = commonMessaging;
        this.messagePageLocators = messagePageLocators;
        this.messageInfoPageLocators = messageInfoPageLocators;
        this.printPreviewPageLocators = printPreviewPageLocators;
        this.mutePageLocators = mutePageLocators;
        this.commonMethod = commonMethod;
        this.homePageLocators = homePageLocators;
        this.commonGroup = commonGroup;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.automationWait.init();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
    }

    @Then("^the message is delivered to \"([^\"]*)\"$")
    public void messageIsDeliveredTo(String user) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        String message = hashMapMessages.get(user);
        Assert.assertTrue(message.equals(webDriverCommands.getText(messagePageLocators.getMessageContent())));
        Assert.assertTrue(webDriverCommands.isPresent(messagePageLocators.getMessageSentStatus()) ||
                webDriverCommands.isPresent(messagePageLocators.getMessageDeliveredStatus()));
    }

    @When("^I cancel sending message to \"([^\"]*)\"$")
    public void iCancelSendingMessageTo(String user) throws Throwable {
        commonMessaging.cancelSendingMessage(webDriverCommands,user);
    }

    @Then("^the message is not delivered to \"([^\"]*)\"$")
    public void messageIsNotDeliveredTo(String user) throws Throwable {
        String message = hashMapMessages.get(user);
        Assert.assertFalse((webDriverCommands.getText(messagePageLocators.getConversationController())).contains(message));
    }

    @When("^I recall the last message sent$")
    public void iRecallTheLastMessageSentTo() throws Throwable {
        webDriverCommands.click(messagePageLocators.getMoreClass());
        webDriverCommands.click(messagePageLocators.getMessageMoreOptions());
        webDriverCommands.click(messagePageLocators.getMessageRecallButton());
    }

    @When("^I try to retrieve message info$")
    public void iTryToRetrieveMessageInfo() throws Throwable {
        webDriverCommands.click(messagePageLocators.getMoreClass());
        webDriverCommands.click(messagePageLocators.getMessageMoreOptions());
        webDriverCommands.click(messagePageLocators.getMessageInfo());
    }

    @Then("^I should see sender as \"([^\"]*)\" and receiver as \"([^\"]*)\"$")
    public void iSeeSenderAsAndReceiverAs(String sender, String receiver) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        Assert.assertTrue(sender.contains(webDriverCommands.getText(messageInfoPageLocators.getMessageInfoSenderName())));
        Assert.assertTrue(receiver.contains(webDriverCommands.getText(messageInfoPageLocators.getMessageInfoReceiverName())));
        webDriverCommands.click(messageInfoPageLocators.getMessageCloseButton());
        automationWait.waitForJSandJQueryToLoad();
    }

    @When("^I click print preview$")
    public void iClickPrintPreview() throws Throwable {
        webDriverCommands.click(messagePageLocators.getPrintPreviewButton());
    }

    @Then("^I see message sent to \"([^\"]*)\" in preview$")
    public void iSeeMessageSentToInPreview(String user) throws Throwable {
        String message = hashMapMessages.get(user);
        webDriverCommands.waitUntilElementVisible(printPreviewPageLocators.getPreviewText(),15);
        Assert.assertTrue(message.contains(webDriverCommands.getText(printPreviewPageLocators.getPreviewText())));
        webDriverCommands.click(printPreviewPageLocators.getReturnToNormalView());
    }

    @Then("^the conversation is muted$")
    public void conversationIsMuted() throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.waitUntilElementVisible(messagePageLocators.getAlreadyMutedButton(),35);
        Assert.assertTrue(webDriverCommands.isVisible(messagePageLocators.getAlreadyMutedButton()));
        webDriverCommands.click(messagePageLocators.getAlreadyMutedButton());
    }

    @When("^I mute conversation for \"([^\"]*)\"$")
    public void iMuteConversationFor(String duration) throws Throwable {
        if(webDriverCommands.isPresent(messagePageLocators.getAlreadyMutedButton())){
            webDriverCommands.click(messagePageLocators.getAlreadyMutedButton());
        }
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.click(messagePageLocators.getMuteButton());
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.selectListByVisibleText(mutePageLocators.getSelectMute(),duration);
        webDriverCommands.waitUntilElementVisible(messagePageLocators.getMuteButton(),30);
        webDriverCommands.click(mutePageLocators.getMuteButton());
        automationWait.untilPageLoads();
    }

    @Then("^the priority message is delivered to \"([^\"]*)\"$")
    public void priorityMessageIsDeliveredTo(String user) throws Throwable {
        String message = hashMapMessages.get(user);
        automationWait.untilElementClickable(messagePageLocators.getPriorityMessageHeader());
        Assert.assertTrue(webDriverCommands.isDisplayed(messagePageLocators.getPriorityMessageHeader()));
        Assert.assertTrue(message.equals(webDriverCommands.getText(messagePageLocators.getMessageContent())));
        Assert.assertTrue(webDriverCommands.isPresent(messagePageLocators.getMessageSentStatus()) ||
                webDriverCommands.isPresent(messagePageLocators.getMessageDeliveredStatus()));
    }

    @Given("^I am logged into TigerText home page as \"([^\"]*)\"$")
    public void iAmLoggedIntoTigerTextHomePageAs(String user) throws Throwable {
        commonMethod.navigateHomePage(this.webDriverCommands);
        commonMethod.ttUserLoginAsUser(webDriverCommands, user);
    }

    @When("^I clear conversation with \"([^\"]*)\"$")
    public void iClearConversationWith(String user) throws Throwable {
        commonMessaging.sendRandomMessage(webDriverCommands,user);
        webDriverCommands.click(messagePageLocators.getClearConversationButton());
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.actionsClick(messagePageLocators.getConfirmClearConversationButton());
    }

    @When("^I clear the conversation$")
    public void iClearTheConversation() throws Throwable {
        webDriverCommands.click(messagePageLocators.getClearConversationButton());
        webDriverCommands.click(messagePageLocators.getConfirmClearConversationButton());
    }

    @Then("^I should see no conversation with \"([^\"]*)\"$")
    public void iSeeNoConversationWith(String user) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        String message = hashMapMessages.get(user);
        Assert.assertFalse((webDriverCommands.getText(messagePageLocators.getConversationController())).contains(message));
    }

    @When("^I send \"([^\"]*)\" message to \"([^\"]*)\"$")
    public void iSendMessageTo(String messageType, String user) throws Throwable {
        String lMessageType = messageType.trim().toLowerCase();
        userType = user;
        automationWait.waitForJSandJQueryToLoad();
        automationWait.untilPageLoads();
        switch(lMessageType) {
            case "random":
                commonMessaging.sendRandomMessage(webDriverCommands,user);
                break;
            case "link":
                commonMessaging.sendMessageType(webDriverCommands,user,lMessageType);
                break;
            case "image":
                commonMessaging.sendMessageType(webDriverCommands,user,lMessageType);
                break;
            case "priority":
                commonMessaging.sendPriorityMessage(webDriverCommands,user);
                break;
            case "emoji":
                commonMessaging.sendEmojiMessage(webDriverCommands,user);
                break;
        }
        automationWait.waitForJSandJQueryToLoad();
    }

    @Then("^the message has \"([^\"]*)\"$")
    public void messageHas(String messageType) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        String lMessageType = messageType.trim().toLowerCase();
        switch(lMessageType) {
            case "link":
                if(userType.equals("Everyone")) {
                    automationWait.untilElementClickable(messagePageLocators.getMessageContentEveryone(),60);
                    automationWait.untilElementVisible(messagePageLocators.getMessageContentEveryone(),60);
                    webDriverCommands.click(messagePageLocators.getMessageContentEveryone());
                }
                else {
                    automationWait.untilElementClickable(messagePageLocators.getMessageContent(),60);
                    automationWait.untilElementVisible(messagePageLocators.getMessageContent(),60);
                    webDriverCommands.click(messagePageLocators.getMessageContent());
                }
                webDriverCommands.switchWindow(1);
                Thread.sleep(1000);
                automationWait.untilPageLoads();
                automationWait.untilPageLoads();
                Assert.assertTrue(webDriverCommands.getURL().contains(hashMapURL.get("link")));
                webDriverCommands.closeChildWindow();
                break;
            case "emoji":
                automationWait.untilElementVisible(messagePageLocators.getEmojiMessage(),60);
                Assert.assertTrue(webDriverCommands.isVisible(messagePageLocators.getEmojiMessage()));
                break;
            case "image":
                String message = hashMapURL.get("image");
                if(userType.equals("Everyone")) {
                    automationWait.untilElementClickable(messagePageLocators.getMessageContentEveryone(),60);
                    automationWait.untilElementVisible(messagePageLocators.getMessageContentEveryone(),60);
                    Assert.assertTrue(message.equals(webDriverCommands.getText(messagePageLocators.getMessageContentEveryone())));
                }
                else {
                    automationWait.untilElementClickable(messagePageLocators.getMessageContent(), 60);
                    automationWait.untilElementVisible(messagePageLocators.getMessageContent(), 60);
                    Assert.assertTrue(message.equals(webDriverCommands.getText(messagePageLocators.getMessageContent())));
                }
                automationWait.untilElementsVisible(messagePageLocators.getImageMessage(),60);
                break;
        }
        Assert.assertTrue(webDriverCommands.isPresent(messagePageLocators.getMessageSentStatus()) ||
                webDriverCommands.isPresent(messagePageLocators.getMessageDeliveredStatus()));
    }

    @Then("^the message has \"([^\"]*)\" to \"([^\"]*)\"$")
    public void forumHas(String messageType, String forum) throws Throwable {
        String lMessageType = messageType.trim().toLowerCase();
        switch(lMessageType) {
            case "link":
                webDriverCommands.click(messagePageLocators.getMessageContent());
                webDriverCommands.switchWindow(1);
                Assert.assertTrue(webDriverCommands.getURL().contains(hashMapURL.get("link")));
                webDriverCommands.closeChildWindow();
                break;
            case "emoji":
                automationWait.untilElementVisible(messagePageLocators.getEmojiMessage(),60);
                Assert.assertTrue(webDriverCommands.isVisible(messagePageLocators.getEmojiMessage()));
                break;
            case "image":
                String message = hashMapURL.get("image");
                Assert.assertTrue(message.equals(webDriverCommands.getText(messagePageLocators.getMessageContent())));
                automationWait.untilElementsVisible(messagePageLocators.getImageMessage());
                break;
        }
        Assert.assertTrue(webDriverCommands.isPresent(messagePageLocators.getMessageSentStatus()) ||
                webDriverCommands.isPresent(messagePageLocators.getMessageDeliveredStatus()));
    }
}
