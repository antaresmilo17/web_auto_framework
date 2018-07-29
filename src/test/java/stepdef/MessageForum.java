package stepdef;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.commonUtils.CommonGroup;
import com.tigertext.automation.commonUtils.CommonMessaging;
import com.tigertext.automation.scenarioSettings.BaseForTest;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.MessagePageLocators;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class MessageForum extends BaseForTest {
    private WebDriverCommands webDriverCommands;
    private BrowserConfig browserConfig;
    private CommonGroup commonGroup;
    private MessageP2P messageP2P;
    private MessagePageLocators messagePageLocators;
    AutomationWait automationWait;
    WebDriver webDriver;

    @Autowired
    public MessageForum(
            BrowserConfig browserConfig,
            AutomationWait automationWait,
            CommonGroup commonGroup,
            MessageP2P messageP2P,
            MessagePageLocators messagePageLocators
    ) {
        this.browserConfig = browserConfig;
        this.automationWait = automationWait;
        this.commonGroup = commonGroup;
        this.messagePageLocators = messagePageLocators;
        this.messageP2P = messageP2P;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.automationWait.init();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
    }

    @And("^I have an existing forum \"([^\"]*)\"$")
    public void iHaveAnExistingForum(String forumName) throws Throwable {
        commonGroup.isGroupPresent(webDriverCommands, forumName);
        automationWait.untilElementVisible(messagePageLocators.getHideForumBanner(),60);
        automationWait.untilElementVisible(messagePageLocators.getHideForumBanner(),60);
        webDriverCommands.click(messagePageLocators.getHideForumBanner());
    }

    @Then("^I should see the conversation is muted for forum \"([^\"]*)\"$")
    public void theConversationIsMutedForForum(String forumName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        Assert.assertTrue(webDriverCommands.isElementDisplayed(messagePageLocators.getAlreadyMutedButton()));
        webDriverCommands.click(messagePageLocators.getAlreadyMutedButton());
    }

    @Then("^the message delivered to forum \"([^\"]*)\" has \"([^\"]*)\"$")
    public void theMessageDeliveredToForumHas(String forumName, String messageType) throws Throwable {
        messageP2P.iSendMessageTo(messageType, forumName);
    }
}