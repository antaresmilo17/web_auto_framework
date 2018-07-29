package stepdef;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.commonUtils.*;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.Assert.fail;

public class ForumSettings {

    private WebDriverCommands webDriverCommands;
    private BrowserConfig browserConfig;
    private CommonGroup commonGroup;
    private CommonMethod commonMethod;
    private ForumsPageLocators forumsPageLocators;
    private CreateForumPageLocators createForumPageLocators;
    private HomePageLocators homePageLocators;
    private MessagePageLocators messagePageLocators;
    private ExploreForumPageLocators exploreForumPageLocators;
    public static String forumID;
    AutomationWait automationWait;
    WebDriver webDriver;

    @Autowired
    public ForumSettings(
            BrowserConfig browserConfig,
            AutomationWait automationWait,
            ForumsPageLocators forumsPageLocators,
            CreateForumPageLocators createForumPageLocators,
            CommonGroup commonGroup,
            CommonMethod commonMethod,
            ExploreForumPageLocators exploreForumPageLocators,
            HomePageLocators homePageLocators,
            MessagePageLocators messagePageLocators
    ) {
        this.browserConfig = browserConfig;
        this.automationWait = automationWait;
        this.commonGroup = commonGroup;
        this.commonMethod = commonMethod;
        this.homePageLocators = homePageLocators;
        this.forumsPageLocators = forumsPageLocators;
        this.createForumPageLocators = createForumPageLocators;
        this.messagePageLocators = messagePageLocators;
        this.exploreForumPageLocators = exploreForumPageLocators;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.automationWait.init();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
    }

    @When("^I create a forum of \"([^\"]*)\" and \"([^\"]*)\" with name \"([^\"]*)\" via admin tool$")
    public void iCreateAForumOfAndWithNameViaAdminTool(String user1, String user2, String forumName) throws Throwable {
        commonGroup.createForum(webDriverCommands, user1, user2, forumName);
        if(TestConfig.Environment.getName().equals("env4") ||TestConfig.Environment.getName().equals("env1")) {
            automationWait.waitForJSandJQueryToLoad();
            webDriverCommands.switchDefaultFrame();
        }
        automationWait.untilElementVisible(homePageLocators.getTigerTextHomeLogo(),60);
        automationWait.untilElementClickable(homePageLocators.getTigerTextHomeLogo(),60);
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
    }

    @Then("^new forum \"([^\"]*)\" is created$")
    public void newForumIsCreated(String forumName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        automationWait.untilElementVisible(messagePageLocators.getNewMessageButton(),60);
        automationWait.untilElementClickable(messagePageLocators.getNewMessageButton(),60);
        Assert.assertTrue(commonGroup.isGroupPresent(webDriverCommands, forumName));
    }

    @When("^I add \"([^\"]*)\" to new forum \"([^\"]*)\"$")
    public void iAddToNewForum(String user, String forumName) throws Throwable {
        commonGroup.addForumMember(webDriverCommands, user, forumName);
        commonGroup.searchUserForum(webDriverCommands, user);
    }

    @And("^I remove \"([^\"]*)\" from forum \"([^\"]*)\"$")
    public void iRemoveFromForum(String user, String forumName) throws Throwable {
        commonGroup.removeForumMember(webDriverCommands, user, forumName);
    }

    @Then("^I should see \"([^\"]*)\" added to forum \"([^\"]*)\"$")
    public void isAddedToForum(String user, String forumName) throws Throwable {
        Assert.assertTrue(commonGroup.isUserPresentForum(webDriverCommands, user, forumName));
        webDriverCommands.switchDefaultFrame();
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
    }

    @Then("^I add \"([^\"]*)\" to existing Forum \"([^\"]*)\" via admin tool$")
    public void isAddedToExistingForum(String user, String forumName) throws Throwable {
        commonGroup.navigateToForumsTab(webDriverCommands);
        commonGroup.editForum(webDriverCommands, forumName);
        commonGroup.searchUserForum(webDriverCommands, user);
        webDriverCommands.click(createForumPageLocators.getUpdateForumButton());
        commonGroup.addForumMember(webDriverCommands, user, forumName);
    }

    @Then("^\"([^\"]*)\" is not present in forum \"([^\"]*)\"$")
    public void isNotPresentInForum(String user, String forumName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        Assert.assertFalse(commonGroup.isUserPresentForum(webDriverCommands, user, forumName));
    }

    @Then("I delete forum \"([^\"]*)\" via admin tools$")
    public void iDeleteForum(String forumName) throws Throwable {
       commonGroup.deleteForum(webDriverCommands,forumName);
    }

    @When("^I change forum name from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iChangeForumNameFromTo(String forumName, String newForumName) throws Throwable {
        commonGroup.updateForumName(webDriverCommands, forumName, newForumName);
    }

    @Then("^forum name is changed to \"([^\"]*)\"$")
    public void forumNameIsChangedTo(String newForumName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.actionsClick(homePageLocators.getSettingsButton());
        if(TestConfig.Environment.getName().equals("env4") ||TestConfig.Environment.getName().equals("env1")) {
            automationWait.waitForJSandJQueryToLoad();
            webDriverCommands.switchFrameByInt(0);
        }

        webDriverCommands.click(forumsPageLocators.getForumsTab());
        automationWait.waitForJSandJQueryToLoad();
        boolean forumName = commonGroup.isForumPresentAdmin(webDriverCommands, newForumName);
        Assert.assertTrue(forumName);

        if(TestConfig.Environment.getName().equals("env4") ||TestConfig.Environment.getName().equals("env1")) {
            automationWait.waitForJSandJQueryToLoad();
            webDriverCommands.switchDefaultFrame();
        }
    }

    @When("^I cancel leaving forum \"([^\"]*)\"$")
    public void iCancelLeavingForum(String forumName) throws Throwable {
        commonGroup.isGroupPresent(webDriverCommands, forumName);
        webDriverCommands.click(messagePageLocators.getLeaveForumButton());
        webDriverCommands.click(messagePageLocators.getCancelLeaveForumButton());
        automationWait.untilElementDoesNotExist(homePageLocators.getBackFadeAll());
        automationWait.untilElementClickable(messagePageLocators.getNewMessageButton());
    }

    @Then("^I see forum \"([^\"]*)\"$")
    public void iSeeForum(String forumName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        Assert.assertTrue(commonGroup.isGroupPresent(webDriverCommands, forumName));
    }

    @When("^I leave forum \"([^\"]*)\"$")
    public void iLeaveForum(String forumName) throws Throwable {
        String xpathToSearch = "//li[.//div[text()='" + forumName + "']]";
        automationWait.untilElementClickable(By.xpath(xpathToSearch));
        List<String> ids = webDriverCommands.getIdsFromElements(By.xpath(xpathToSearch));
        forumID = ids.get(0);
        webDriverCommands.click(By.xpath(xpathToSearch));
        webDriverCommands.click(messagePageLocators.getLeaveForumButton());
        webDriverCommands.click(messagePageLocators.getConfirmLeaveForumButton());
    }

    @Then("^I do not see forum \"([^\"]*)\"$")
    public void iDoNotSeeForum(String forumName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        automationWait.untilPageLoads();
        Assert.assertFalse(commonGroup.isGroupPresent(webDriverCommands, forumName));
    }

    @Then("^I do not see forum \"([^\"]*)\" in admin tools$")
    public void iDoNotSeeForumInAdminTools(String forumName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.click(forumsPageLocators.getForumsTab());
        Assert.assertFalse(commonGroup.isForumPresentInAdminTools(webDriverCommands,forumName));
    }

    @Then("^I see \"([^\"]*)\" in explore forum menu$")
    public void iSeeInExploreForumMenu(String forumName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        System.out.println("test: "+webDriverCommands.getText(exploreForumPageLocators.getExploreForumList()));
        Assert.assertTrue(webDriverCommands.getText(exploreForumPageLocators.getExploreForumList()).contains(forumName));
        webDriverCommands.click(exploreForumPageLocators.getCloseExploreForumButton());
        automationWait.untilElementDoesNotExist(homePageLocators.getBackFadeAll());
        automationWait.untilElementClickable(messagePageLocators.getNewMessageButton());
    }

    @When("^I explore forums$")
    public void iExploreForums() throws Throwable {
        webDriverCommands.click(homePageLocators.getHamburgerMenu());
        webDriverCommands.click(homePageLocators.getExploreForumsButton());
    }

    @And("^I select \"([^\"]*)\" Org$")
    public void iSelectOrg(String orgName) throws Throwable {
        commonMethod.selectOrgName(webDriverCommands, orgName);
    }
}