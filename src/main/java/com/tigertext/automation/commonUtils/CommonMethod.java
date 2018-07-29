package com.tigertext.automation.commonUtils;

import com.tigertext.automation.config.DriverConfig;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.commonUtils.CommonGroup;
import com.tigertext.automation.config.UserConfig;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.*;
import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.commonUtils.FileGenerator;
import cucumber.api.Scenario;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.util.StopWatch;
import java.util.concurrent.TimeUnit;


@Component
public class CommonMethod {
    private boolean debug;
    Scenario scenario;
    private BrowserConfig browserConfig;
    private CommonGroup commonGroup;
    private HomePageLocators homePageLocators;
    private LoginPageLocators loginPageLocators;
    private OneLoginPageLocators oneLoginPageLocators;
    private MessagePageLocators messagePageLocators;
    private CreateGroupPageLocators createGroupPageLocators;
    private UsersPageLocators usersPageLocators;
    private UserSettingsPageLocators userSettingsPageLocators;
    AutomationWait automationWait;
    WebDriver webDriver;
    //public static boolean isLoadTestComplete = false;
    Logger logger = Logger.getLogger(CommonMethod.class.getName());
    StopWatch stopWatch;
    private WebDriverCommands webDriverCommands;
    @Autowired
    FileGenerator fileGenerator;

    @Autowired
    public CommonMethod(AutomationWait automationWait,
                        LoginPageLocators loginPageLocators, OneLoginPageLocators oneLoginPageLocators,
                        HomePageLocators homePageLocators,
                        MessagePageLocators messagePageLocators,
                        CreateGroupPageLocators createGroupPageLocators,
                        UsersPageLocators usersPageLocators,
                        UserSettingsPageLocators userSettingsPageLocators,
                        BrowserConfig browserConfig,
                        CommonGroup commonGroup,
                        FileGenerator fileGenerator) {
        this.automationWait = automationWait;
        this.browserConfig = browserConfig;
        this.loginPageLocators = loginPageLocators;
        this.oneLoginPageLocators = oneLoginPageLocators;
        this.homePageLocators = homePageLocators;
        this.messagePageLocators = messagePageLocators;
        this.createGroupPageLocators = createGroupPageLocators;
        this.usersPageLocators = usersPageLocators;
        this.userSettingsPageLocators = userSettingsPageLocators;
        this.commonGroup = commonGroup;
        this.fileGenerator = fileGenerator;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
        this.debug = webDriverCommands.getDebug();
    }

    public void navigateHomePage(WebDriverCommands webDriverCommands) throws IOException, ConfigurationException {
        //webDriverCommands.setSize(DriverConfig.config().getInt("windows.window.width"),
        //        DriverConfig.config().getInt("windows.window.height"));
        webDriverCommands.navigate(TestConfig.Environment.getURL());
    }

    public String getRandomEmail(String prefix) throws Throwable {
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyMMddHHmmss");
        String email = String.format("%s+%s%s@tigertext.com", UserConfig.config().getString("automation.account"), prefix.trim(), fmt.format(date));
        return email;
    }

    public String returnRandomText(){
        StringBuffer buffer = new StringBuffer();
        String characters;
        characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        int charactersLength = characters.length();

        for (int i = 0; i < 10; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }

        return buffer.toString();
    }

    public void ttUserLogin(WebDriverCommands webDriverCommands, String userAccount) throws Throwable {
        if(userAccount.toLowerCase().equals("saml"))
        {
            String samlUserName = TestConfig.Environment.getUserEmail("SAML");
            webDriverCommands.enterText(loginPageLocators.getUserName(), samlUserName );
            webDriverCommands.click(loginPageLocators.getLoginUserNameButton());
            automationWait.waitForJSandJQueryToLoad();
            webDriverCommands.waitUntilElementVisible(oneLoginPageLocators.getOneLoginLogo(),120);
            Assert.assertTrue(webDriverCommands.isElementDisplayed(oneLoginPageLocators.getOneLoginLogo()));
            webDriverCommands.enterText(oneLoginPageLocators.getOneLoginUserName(), samlUserName);
            webDriverCommands.enterText(oneLoginPageLocators.getOneLoginPassword(), TestConfig.Environment.getUserPassword("SAML"));
        }
        else {
            if (userAccount.toLowerCase().equals("ihis")) {

                webDriverCommands.enterText(loginPageLocators.getUserName(), TestConfig.Environment.getUserEmail("IHIS"));
                webDriverCommands.click(loginPageLocators.getLoginUserNameButton());
                automationWait.waitForJSandJQueryToLoad();
                webDriverCommands.enterText(loginPageLocators.getUserPassword(), TestConfig.Environment.getUserPassword("IHIS"));
            }
            else {
                webDriverCommands.enterText(loginPageLocators.getUserName(), TestConfig.Environment.getUserEmail("WebQa1"));
                webDriverCommands.click(loginPageLocators.getLoginUserNameButton());
                automationWait.waitForJSandJQueryToLoad();
                webDriverCommands.enterText(loginPageLocators.getUserPassword(), TestConfig.Environment.getUserPassword("WebQa1"));

            }
            webDriverCommands.click(loginPageLocators.getLoginButton());
            automationWait.untilElementClickable(homePageLocators.getSignOutButton(), 60);
            automationWait.untilElementClickable(messagePageLocators.getSendMessageButton(), 60);
            if (webDriverCommands.isElementDisplayed(homePageLocators.getHelpOverLay()))
                webDriverCommands.click(homePageLocators.getHelpOverLay());
        }
    }

    public void ttUserLoginAsUser(WebDriverCommands webDriverCommands, String User) throws Throwable {
        String user = "";
        String userPassword = "";
        if(BrowserConfig.isLoadTestInProgress) {
            try {

                if (!System.getProperty("remote.username").equals(null)) {
                    if(!"auto admin".equals(User.toLowerCase())) {
                        user = TestConfig.Environment.getUserEmail(System.getProperty("remote.username"));
                        userPassword = System.getProperty("remote.password");
                    }
                    else
                    {
                        user = TestConfig.Environment.getUserEmail(User);
                        userPassword = TestConfig.Environment.getUserPassword(User);
                    }
                }
            } catch (NullPointerException e) {
                user = TestConfig.Environment.getUserEmail(User);
                userPassword = TestConfig.Environment.getUserPassword(User);
            }
        }
        else
        {
            user = TestConfig.Environment.getUserEmail(User);
            userPassword = TestConfig.Environment.getUserPassword(User);
        }
        automationWait.untilPageLoads();
        automationWait.untilElementClickable(loginPageLocators.getLoginUserNameButton(), 60);
        automationWait.untilElementVisible(loginPageLocators.getLoginUserNameButton(),60);
        automationWait.untilElementClickable(loginPageLocators.getUserName(),60);
        webDriverCommands.enterText(loginPageLocators.getUserName(), user);
        automationWait.untilElementClickable(loginPageLocators.getLoginUserNameButton(),60);
        webDriverCommands.click(loginPageLocators.getLoginUserNameButton());
        webDriverCommands.enterText(loginPageLocators.getUserPassword(), userPassword);
        webDriverCommands.click(loginPageLocators.getLoginButton());
        //automationWait.untilPageLoads();
        webDriverCommands.waitUntilElementVisible(homePageLocators.getTigerTextHomeLogo(),120);
        automationWait.untilElementClickable(homePageLocators.getTigerTextHomeLogo(), 120);

    }

    public void ttUserLoginAsUserLoop(WebDriverCommands webDriverCommands, String User, int loopTimes) throws Throwable {
        String user = "";
        String userPassword = "";
        String fileName = "loadtest.txt";
        int loop = loopTimes;
        List<String> loadTestResults = new ArrayList<>();

//        if(User.equals("1k1v2"))
//        {
//            fileGenerator.clearFile(fileName);
//
//        }
        loadTestResults.add(User);
        for(int i = 0; i<loop;i++) {
            if (BrowserConfig.isLoadTestInProgress) {
                try {

                    if (!System.getProperty("remote.username").equals(null)) {
                        if (!"auto admin".equals(User.toLowerCase())) {
                            user = TestConfig.Environment.getUserEmail(System.getProperty("remote.username"));
                            userPassword = System.getProperty("remote.password");
                        } else {
                            user = TestConfig.Environment.getUserEmail(User);
                            userPassword = TestConfig.Environment.getUserPassword(User);
                        }
                    }
                } catch (NullPointerException e) {
                    user = TestConfig.Environment.getUserEmail(User);
                    userPassword = TestConfig.Environment.getUserPassword(User);
                }
            } else {
                user = TestConfig.Environment.getUserEmail(User);
                userPassword = TestConfig.Environment.getUserPassword(User);
            }
            System.out.println("Run: "+i);
            Thread.sleep(4000);
            automationWait.untilElementClickable(loginPageLocators.getLoginUserNameButton(), 200);
            webDriverCommands.enterText(loginPageLocators.getUserName(), user);
            webDriverCommands.click(loginPageLocators.getLoginUserNameButton());

            automationWait.untilElementClickable(loginPageLocators.getLoginButton(), 100);
            webDriverCommands.enterText(loginPageLocators.getUserPassword(), userPassword);

            webDriverCommands.click(loginPageLocators.getLoginButton());
            stopWatch = new StopWatch();
            stopWatch.start();
            automationWait.untilElementVisible(homePageLocators.getLoginProgress(),160);
            automationWait.untilElementNotVisible(homePageLocators.getLoadingBar(),240);
            automationWait.untilElementsVisible(homePageLocators.getSignOutButton(), 240);
            stopWatch.stop();
            loadTestResults.add(String.valueOf(stopWatch.getTotalTimeSeconds()));
            fileGenerator.createFile(fileName);
            webDriverCommands.click(homePageLocators.getSignOutButton());
        }
        fileGenerator.writeToFile(loadTestResults);
        //fileGenerator.readFile();
    }

    public void createNewUsers(WebDriverCommands webDriverCommands, String user) throws Throwable {
        automationWait.untilPageLoads();
        navigateToUsersTab(webDriverCommands);
        automationWait.waitForJSandJQueryToLoad();
        automationWait.untilElementVisible(usersPageLocators.getUserSelectAllCheckbox(),60);
//        automationWait.untilElementClickable(usersPageLocators.getUserSelectAllCheckbox(),60);
        webDriverCommands.click(usersPageLocators.getAddUser());
        if (!user.contains(", ")) {
            user = user + ", ";
        }
        String[] users = user.split(", ");
        int amount_of_users = users.length;

        for (int x = 0; x < amount_of_users; x++) {
            webDriverCommands.enterText(usersPageLocators.getFirstName(), TestConfig.Environment.getUserFirstName(users[x]));
            webDriverCommands.enterText(usersPageLocators.getLastName(), TestConfig.Environment.getUserLastName((users[x])));
            webDriverCommands.enterText(usersPageLocators.getDisplayName(), TestConfig.Environment.getUserDisplayName((users[x])));
            webDriverCommands.enterText(usersPageLocators.getEmail(), TestConfig.Environment.getUserEmail(users[x]));
            webDriverCommands.enterText(usersPageLocators.getPassword(), TestConfig.Environment.getUserPassword(users[x]));
            webDriverCommands.click(usersPageLocators.getCreateUser());
            webDriverCommands.waitForAlertToAppearAndAccept();
            webDriverCommands.waitForAlertToAppearAndAccept();
        }

        webDriverCommands.click(usersPageLocators.getCancel());
        webDriverCommands.scrollUp();
        automationWait.untilPageLoads();
    }

    public void deleteUsers(WebDriverCommands webDriverCommands, String user) throws Throwable {
        if (!user.contains(", ")) {
            user = user + ", ";
        }
        String[] users = user.split(", ");
        int amount_of_users = users.length;
        navigateToCustomerSupportTab(webDriverCommands);

        for (int x = 0; x < amount_of_users; x++) {

            webDriverCommands.enterText(userSettingsPageLocators.getCustomerSupportTabUserSearchTextfield(), TestConfig.Environment.getUserEmail(users[x]));
            webDriverCommands.click(userSettingsPageLocators.getCustomerSupportTabUserSearchButton());

            automationWait.untilElementsVisible(userSettingsPageLocators.getCustomerSupportTabDeleteUserTab());
            automationWait.waitForJSandJQueryToLoad();
            if (isUserEmailPresent(webDriverCommands, users[x])) {
                if (x == 0)
                    webDriverCommands.click(userSettingsPageLocators.getCustomerSupportTabDeleteUserTab());
                String customerToken = webDriverCommands.getText(userSettingsPageLocators.getCustomerToken());
                webDriverCommands.click(userSettingsPageLocators.getCustomerSupportTabDeleteUserButton());
                Thread.sleep(5000);
                webDriverCommands.waitForAlertToAppearAndAccept();
                Thread.sleep(4000);
                webDriverCommands.waitForAlertToAppearAndAccept();
                Thread.sleep(5000);
            }
        }
        webDriverCommands.switchDefaultFrame();
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
        automationWait.waitForJSandJQueryToLoad();
    }

    public void createOrganization(WebDriverCommands webDriverCommands, String newOrganizationName)throws Throwable{
        webDriverCommands.click(userSettingsPageLocators.getOrgCreateNewOrgButton());
        automationWait.untilElementClickable(userSettingsPageLocators.getOrgOrgNameTextField());
        webDriverCommands.enterText(userSettingsPageLocators.getOrgOrgNameTextField(), newOrganizationName);
        webDriverCommands.click(userSettingsPageLocators.getOrgCreateButton());
        automationWait.waitForJSandJQueryToLoad();
        String orgNameHeader = webDriverCommands.getText(userSettingsPageLocators.getOrgOrgNameHeaderTextField());
        if(!orgNameHeader.contains(newOrganizationName)){
            Assert.fail("The organization was created with the incorrect name. We provided: " + newOrganizationName +
                    " but it created: " + orgNameHeader);
        }

        webDriverCommands.click(userSettingsPageLocators.getOrganizationTab());
    }

    public void theOrganizationIsCreated(WebDriverCommands webDriverCommands, String newOrganizationName) throws Throwable{
        webDriverCommands.enterText(userSettingsPageLocators.getOrgOrgSearchTextField(), newOrganizationName);
        automationWait.waitForJSandJQueryToLoad();
        String organizationID = webDriverCommands.getText(By.xpath("//*[@data-class='home.admin.orgs.org_name' and .='"+
                newOrganizationName + "']/following-sibling::td[1]"));
        if (organizationID == "")
            Assert.fail("The organization ID returned with a blank value. This means the organization could not be found: " + newOrganizationName);

        System.out.println("*************\n");
        System.out.println("Org Created: " + newOrganizationName);
        System.out.println("Org ID: " + organizationID + "\n");
        System.out.println("*************\n");
        webDriverCommands.click(userSettingsPageLocators.getOrganizationTab());
    }

    public void navigateToUsersTab(WebDriverCommands webDriverCommands) throws Throwable {
        automationWait.untilElementClickable(homePageLocators.getSettingsButton(),60);
        webDriverCommands.actionsClick(homePageLocators.getSettingsButton());
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.switchFrameByInt(0);
       automationWait.untilElementVisible(usersPageLocators.getUsersTab(),60);
        automationWait.untilElementClickable(usersPageLocators.getUsersTab(),60);
        webDriverCommands.actionsClick(usersPageLocators.getUsersTab());
    }

    public void navigateToCustomerSupportTab(WebDriverCommands webDriverCommands) throws Throwable {

        automationWait.untilElementClickable(homePageLocators.getSettingsButton(), 60);
        automationWait.untilElementsVisible(homePageLocators.getSettingsButton(), 60);
        webDriverCommands.actionsClick(homePageLocators.getSettingsButton());
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.switchFrameByInt(0);

        automationWait.untilElementClickable(userSettingsPageLocators.getCustomerSupportTab(),90);
        automationWait.untilElementsVisible(userSettingsPageLocators.getCustomerSupportTab(),90);
        webDriverCommands.click(userSettingsPageLocators.getCustomerSupportTab());
    }


    public void navigateToAdminOption(WebDriverCommands webDriverCommands, String adminOption) throws Throwable
    {
        automationWait.untilElementClickable(homePageLocators.getSettingsButton(), 60);
        automationWait.untilElementsVisible(homePageLocators.getSettingsButton(), 60);
        webDriverCommands.actionsClick(homePageLocators.getSettingsButton());

        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.switchFrameByInt(0);
        switch (adminOption.toLowerCase())
        {
            case "organizations":
                automationWait.untilElementsVisible(userSettingsPageLocators.getOrganizationTab(),60);
                webDriverCommands.click(userSettingsPageLocators.getOrganizationTab());
                break;
            case "customer support":
                automationWait.untilElementsVisible(userSettingsPageLocators.getCustomerSupportTab(),60);
                webDriverCommands.click(userSettingsPageLocators.getCustomerSupportTab());
                break;
            case "forums":
                break;
            case "users":
                automationWait.waitForJSandJQueryToLoad();
                webDriverCommands.actionsClick(usersPageLocators.getUsersTab());
                automationWait.waitForJSandJQueryToLoad();
                break;
            default:
                break;

        }


    }

    public boolean isUserPresent(WebDriverCommands webDriverCommands, String user) throws Throwable {
        try {
            String xpathToSearch = "//*[contains(text(), '" + TestConfig.Environment.getUserFirstName(user) + "')]";
            if (webDriverCommands.isPresent(By.xpath(xpathToSearch))) {
                webDriverCommands.click(xpathToSearch);
                return true;
            } else return false;
        } catch (Exception e) {
            if (e.getMessage().contains("waiting for element")) {
                return false;
            }
        }
        return false;
    }

    public boolean isUserEmailPresent(WebDriverCommands webDriverCommands, String user) throws Throwable {
        try {
            String xpathToSearch = "//*[contains(text(), '" + TestConfig.Environment.getUserEmail(user) + "')]";
            if (webDriverCommands.isPresent(By.xpath(xpathToSearch))) {
                return true;
            } else return false;
        } catch (Exception e) {
            if (e.getMessage().contains("waiting for element")) {
                return false;
            }
        }
        return false;
    }

    public String capitalizeFirstLetter(String value) {
        String firstChar = (value.charAt(0) + "").toUpperCase();
        return firstChar + value.substring(1, value.length());
    }

    public void selectOrgName(WebDriverCommands webDriverCommands, String orgName) throws Throwable {
        navigateToUsersTab(webDriverCommands);
        webDriverCommands.selectListByVisibleText(usersPageLocators.getSelectOrgDropDown(), orgName);
    }
}