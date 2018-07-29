package stepdef;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.commonUtils.CommonGroup;
import com.tigertext.automation.commonUtils.CommonMessaging;
import com.tigertext.automation.scenarioSettings.BaseForTest;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class MessageGroup extends BaseForTest {
    private WebDriverCommands webDriverCommands;
    private BrowserConfig browserConfig;
    private CommonGroup commonGroup;
    private CommonMessaging commonMessaging;
    AutomationWait automationWait;
    WebDriver webDriver;

    @Autowired
    public MessageGroup(
            BrowserConfig browserConfig,
            AutomationWait automationWait,
            CommonGroup commonGroup,
            CommonMessaging commonMessaging
    ) {
        this.browserConfig = browserConfig;
        this.automationWait = automationWait;
        this.commonGroup = commonGroup;
        this.commonMessaging = commonMessaging;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.automationWait.init();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
    }

    @And("^I have an existing Group \"([^\"]*)\"$")
    public void iHaveAnExistingGroup(String groupName) throws Throwable {
        commonGroup.isGroupPresent(webDriverCommands,groupName);
    }
    //add defaults to all switch statements
    @When("^I send \"([^\"]*)\" message to \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iSendMessageToAnd(String messageType, String user1, String user2) throws Throwable {
        String lMessageType = messageType.trim().toLowerCase();
        switch(lMessageType) {
            case "random":
                commonMessaging.sendRandomMessageToMultipleUser(webDriverCommands, user1, user2);
                break;
        }
    }
}
