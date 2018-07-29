package com.tigertext.automation.commonUtils;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.*;
import cucumber.api.Scenario;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class ImportTestUsers {

    private boolean debug;
    Scenario scenario;
    private BrowserConfig browserConfig;
    private HomePageLocators homePageLocators;
    private MessagePageLocators messagePageLocators;
    private CreateGroupPageLocators createGroupPageLocators;
    private RosterPageLocators rosterPageLocators;
    private ForumsPageLocators forumsPageLocators;
    private CreateForumPageLocators createForumPageLocators;
    private UsersPageLocators usersPageLocators;
    private UserSettingsPageLocators userSettingsPageLocators;
    private CommonMethod commonMethod;
    AutomationWait automationWait;
    WebDriver webDriver;
    private WebDriverCommands webDriverCommands;

    @Autowired
    public ImportTestUsers(AutomationWait automationWait,
                           HomePageLocators homePageLocators,
                           ForumsPageLocators forumsPageLocators,
                           CreateForumPageLocators createForumPageLocators,
                           MessagePageLocators messagePageLocators,
                           CreateGroupPageLocators createGroupPageLocators,
                           RosterPageLocators rosterPageLocators,
                           BrowserConfig browserConfig,
                           UsersPageLocators usersPageLocators,
                           UserSettingsPageLocators userSettingsPageLocators,
                           CommonMethod commonMethod) {
        this.automationWait = automationWait;
        this.browserConfig = browserConfig;
        this.homePageLocators = homePageLocators;
        this.messagePageLocators = messagePageLocators;
        this.createGroupPageLocators = createGroupPageLocators;
        this.rosterPageLocators = rosterPageLocators;
        this.forumsPageLocators = forumsPageLocators;
        this.createForumPageLocators = createForumPageLocators;
        this.usersPageLocators = usersPageLocators;
        this.userSettingsPageLocators = userSettingsPageLocators;
        this.commonMethod = commonMethod;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
        this.debug = webDriverCommands.getDebug();
    }

    public void createSingleCustomOrganization(WebDriverCommands webDriverCommands) throws Throwable{
        String newOrganizationName = System.getProperty("organization.name").toString();
        webDriverCommands.click(userSettingsPageLocators.getOrganizationTab());
        commonMethod.createOrganization(webDriverCommands, newOrganizationName);
    }

    public void createCustomOrganizations(WebDriverCommands webDriverCommands) throws Throwable{
        String organizationName = StaticData.testOrganizationName;
        Integer organizationAmount = StaticData.testOrganizationAmount;
        webDriverCommands.click(userSettingsPageLocators.getOrganizationTab());

        for(int x = 0; x < organizationAmount; x++)
        {
            String newOrganizationName = organizationName + x;
            commonMethod.createOrganization(webDriverCommands, newOrganizationName);
        }
    }

    public void theCustomOrgIsCreated(WebDriverCommands webDriverCommands) throws Throwable{
        String newOrganizationName = System.getProperty("organization.name");
        commonMethod.theOrganizationIsCreated(webDriverCommands, newOrganizationName);
    }

    public void theCustomOrgsAreCreated(WebDriverCommands webDriverCommands) throws Throwable{
        String organizationName = StaticData.testOrganizationName;
        Integer organizationAmount = StaticData.testOrganizationAmount;

        for(int x = 0; x < organizationAmount; x++)
        {
            String newOrganizationName = organizationName + x;
            commonMethod.theOrganizationIsCreated(webDriverCommands, newOrganizationName);
        }
    }

    public void theOrgIsSwitched(WebDriverCommands webDriverCommands) throws Throwable{
        navigateToTheOrgTab(webDriverCommands);
        String newOrganizationName = System.getProperty("organization.name");
        webDriverCommands.enterText(userSettingsPageLocators.getOrgOrgSearchTextField(), newOrganizationName);
        automationWait.waitForJSandJQueryToLoad();
        String orgIDxPath = "//*[@data-class='home.admin.orgs.org_name' and .='"+
                newOrganizationName + "']/following-sibling::td[1]";
        String organizationID = webDriverCommands.getText(By.xpath(orgIDxPath));
        if (organizationID == "")
            Assert.fail("The organization ID returned with a blank value. This means the organization could not be found: " + newOrganizationName);

        webDriverCommands.click(orgIDxPath);
        webDriverCommands.click(userSettingsPageLocators.getOrganizationTab());
    }

    public void navigateToTheOrgTab(WebDriverCommands webDriverCommands) throws Throwable {
        automationWait.untilElementClickable(homePageLocators.getSettingsButton());
        webDriverCommands.actionsClick(homePageLocators.getSettingsButton());
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.actionsClick(userSettingsPageLocators.getOrganizationTab());
        automationWait.waitForJSandJQueryToLoad();
    }

    public void importNewUsers(WebDriverCommands webDriverCommands) throws Throwable {
        automationWait.untilPageLoads();
        String classpathroot = currentFolderPath();
        File currentFolderPath = new File(classpathroot,"/");
        String currentDir = currentFolderPath.getParentFile().getParentFile().getParentFile().getParentFile().toString();
        commonMethod.navigateToUsersTab(webDriverCommands);
        automationWait.untilElementClickable(usersPageLocators.getImportUsers());
        webDriverCommands.click(usersPageLocators.getImportUsers());
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.enterPath(usersPageLocators.getChooseFile(), currentDir + "/ImportTestUsers.csv");
        webDriverCommands.click(usersPageLocators.getSubmitButton());
        automationWait.waitForJSandJQueryToLoad();
        automationWait.untilElementClickable(usersPageLocators.getSuccessfulUsersImportedCount());
        commonMethod.navigateToUsersTab(webDriverCommands);
    }

    public String currentFolderPath(){
        return new File(ImportTestUsers.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsolutePath();
    }
}
