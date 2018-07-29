package stepdef;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.commonUtils.*;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.CreateGroupPageLocators;
import com.tigertext.automation.webElements.HomePageLocators;
import com.tigertext.automation.webElements.MessagePageLocators;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

import static org.junit.Assert.fail;

public class GroupSettings {

    private WebDriverCommands webDriverCommands;
    private BrowserConfig browserConfig;
    private CommonGroup commonGroup;
    private MessagePageLocators messagePageLocators;
    private CreateGroupPageLocators createGroupPageLocators;
    private HomePageLocators homePageLocators;
    AutomationWait automationWait;
    WebDriver webDriver;

    @Autowired
    public GroupSettings(
            BrowserConfig browserConfig,
            AutomationWait automationWait,
            CommonGroup commonGroup,
            MessagePageLocators messagePageLocators,
            CreateGroupPageLocators createGroupPageLocators,
            HomePageLocators homePageLocators
    ) {
        this.browserConfig = browserConfig;
        this.automationWait = automationWait;
        this.commonGroup = commonGroup;
        this.messagePageLocators = messagePageLocators;
        this.createGroupPageLocators = createGroupPageLocators;
        this.homePageLocators = homePageLocators;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.automationWait.init();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
    }

    @When("^I change group name to \"([^\"]*)\"$")
    public void iChangeGroupNameFromTo(String newGroupName) throws Throwable {
        webDriverCommands.click(messagePageLocators.getGroupSettings());
        commonGroup.updateGroup(webDriverCommands,newGroupName);
    }

    @Then("^group name is updated to \"([^\"]*)\"$")
    public void groupNameIsChangedFromTo(String newGroupName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        Assert.assertTrue(webDriverCommands.getText(createGroupPageLocators.getGroupNameHomePage()).contains(newGroupName));
        commonGroup.leaveGroup(webDriverCommands,newGroupName);
        //commonGroup.removeGroupApi(webDriverCommands,newGroupName);
        automationWait.waitForJSandJQueryToLoad();
    }

    @When("^I leave existing group \"([^\"]*)\"$")
    public void iLeaveGroup(String groupName) throws Throwable {
        commonGroup.leaveGroup(webDriverCommands,groupName);
    }

    @When("^I cancel leaving group$")
    public void iCancelLeavingGroup() throws Throwable {
        automationWait.untilPageLoads();
        webDriverCommands.click(messagePageLocators.getGroupSettings());
        webDriverCommands.click(createGroupPageLocators.getLeaveGroup());
        automationWait.untilElementVisible(createGroupPageLocators.getCancelLeaveGroup(),60);
        webDriverCommands.click(createGroupPageLocators.getCancelLeaveGroup());
        automationWait.untilElementDoesNotExist(homePageLocators.getBackFade());
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.click(createGroupPageLocators.getCancelButton());
    }

    @Then("^I see group \"([^\"]*)\"$")
    public void iSeeGroup(String groupName) throws Throwable {
        automationWait.untilElementClickable(messagePageLocators.getNewMessageButton());
        Assert.assertTrue(commonGroup.isGroupPresent(webDriverCommands,groupName));
        commonGroup.leaveGroup(webDriverCommands,groupName);
    }

    @Then("^new group of \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" is created$")
    public void newGroupOfIsCreated(String user1, String user2, String user3) throws Throwable {
        String groupName = TestConfig.Environment.getUserFirstName(user1) + ", " +
                TestConfig.Environment.getUserFirstName(user2) + ", " +
                TestConfig.Environment.getUserFirstName(user3);
        automationWait.waitForJSandJQueryToLoad();
        System.out.println("groupname: "+groupName);
        Assert.assertTrue(commonGroup.isGroupPresent(webDriverCommands,groupName));
        commonGroup.leaveGroup(webDriverCommands,groupName);
        //commonGroup.removeGroupApi(webDriverCommands,groupName);
    }

    @Then("^new group \"([^\"]*)\" is created$")
    public void newGroupIsCreated(String groupName) throws Throwable {
        automationWait.untilElementClickable(messagePageLocators.getNewMessageButton());
        Assert.assertTrue(commonGroup.isGroupPresent(webDriverCommands,groupName));
        commonGroup.leaveGroup(webDriverCommands,groupName);
        //commonGroup.removeGroupApi(webDriverCommands,groupName);
    }

    @When("^I create a group of \"([^\"]*)\" and \"([^\"]*)\" with name \"([^\"]*)\" via Hamburger menu$")
    public void iCreateAGroupOfAndWithNameViaHamburgerMenu(String user1, String user2, String groupName) throws Throwable {
        commonGroup.createGroup(webDriverCommands,user1,user2,groupName);
        automationWait.waitForJSandJQueryToLoad();
    }

    @When("^I add \"([^\"]*)\" to existing group \"([^\"]*)\"$")
    public void iAddToExistingGroup(String user, String groupName) throws Throwable {
        commonGroup.addGroupMember(webDriverCommands,user,groupName);
    }

    @Then("^\"([^\"]*)\" is added to existing Group \"([^\"]*)\"$")
    public void isAddedToExistingGroup(String user, String groupName) throws Throwable {
        automationWait.untilPageLoads();
        webDriverCommands.click(messagePageLocators.getGroupSettings());
        automationWait.untilElementVisible(createGroupPageLocators.getListGroupMembers(), 60);
        Assert.assertTrue(webDriverCommands.getText(createGroupPageLocators.getListGroupMembers()).contains(user));
        Assert.assertTrue(webDriverCommands.getText(createGroupPageLocators.getGroupNameLabel()).contains(groupName));
        webDriverCommands.click(createGroupPageLocators.getCancelButton());
        commonGroup.removeGroupMember(webDriverCommands,user,groupName);
    }

    @When("^I remove \"([^\"]*)\" from existing group \"([^\"]*)\"$")
    public void iRemoveFromExistingGroup(String user, String groupName) throws Throwable {
        commonGroup.removeGroupMember(webDriverCommands,user,groupName);
        /* */
    }

    @Then("^\"([^\"]*)\" is not present in existing group \"([^\"]*)\"$")
    public void isNotPresentInExistingGroup(String user, String groupName) throws Throwable {
        webDriverCommands.actionsClick(messagePageLocators.getGroupSettings());
        Assert.assertFalse(webDriverCommands.getText(createGroupPageLocators.getListGroupMembers()).contains(user));
        webDriverCommands.click(createGroupPageLocators.getCancelButton());
    }

    @When("^I leave new group \"([^\"]*)\"$")
    public void iLeaveNewGroup(String groupName) throws Throwable {
        commonGroup.leaveGroup(webDriverCommands,groupName);
    }

    @Then("^I do not see group \"([^\"]*)\"$")
    public void iDoNotSeeGroup(String groupName) throws Throwable {
        String xpathToSearch = "//*[contains(text(), '" + groupName + "')][@data-class='home.name home.conv.single.name']";
        automationWait.untilElementNotVisible(By.xpath(xpathToSearch),60);
        Assert.assertFalse(commonGroup.isGroupPresent(webDriverCommands,groupName));
    }
}
