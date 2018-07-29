package stepdef;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.commonUtils.CommonMethod;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.HomePageLocators;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import javax.annotation.PostConstruct;

public class LoadTest {

    private WebDriverCommands webDriverCommands;
    private BrowserConfig browserConfig;
    private HomePageLocators homePageLocators;
    private CommonMethod commonMethod;
    private AdminSettings adminSettings;
    private Login login;
    private MessageP2P messageP2P;
    AutomationWait automationWait;
    WebDriver webDriver;

    @Autowired
    public LoadTest(
            BrowserConfig browserConfig,
            AutomationWait automationWait,
            HomePageLocators homePageLocators,
            CommonMethod commonMethod,
            AdminSettings adminSettings,
            Login login,
            MessageP2P messageP2P
    ){
        this.browserConfig = browserConfig;
        this.automationWait = automationWait;
        this.homePageLocators = homePageLocators;
        this.commonMethod = commonMethod;
        this.adminSettings = adminSettings;
        this.login = login;
        this.messageP2P = messageP2P;
    }

    @PostConstruct
    public void init(){
        this.webDriver = browserConfig.getWebDriver();
        this.automationWait.init();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
    }

    @Then("^I am logged into TigerText home page$")
    public void iAmLoggedIntoTigerTextHomePage() throws Throwable {
        Assert.assertTrue(webDriverCommands.isVisible(homePageLocators.getTigerTextHomeLogo()));
    }

    @When("^I enter correct credentials for (.*) while load testing$")
    public void iEnterCorrectCredentialsForWhileLoadTesting(String user) throws Throwable {
        BrowserConfig.isLoadTestInProgress = true;
        commonMethod.ttUserLoginAsUser(webDriverCommands,user);
        if(user.equals("WebUser3")){
            BrowserConfig.isLoadTestInProgress=false;
        }
    }

    @When("^I enter correct credentials for (.*) while load testing (.*) times per user$")
    public void iEnterCorrectCredentialsForWhileLoadTesting(String user, int loopTimes) throws Throwable {
        BrowserConfig.isLoadTestInProgress = true;
        commonMethod.ttUserLoginAsUserLoop(webDriverCommands,user,loopTimes);
        if(user.equals("WebUser3")){
            BrowserConfig.isLoadTestInProgress=false;
        }
    }
    @Then("^I clear (.*) web resources$")
    public void iClearUserWebResources(String userName) throws Throwable {
        String user = System.getProperty("remote.username");
        if(!TestConfig.Environment.getName().equals("lt")) {
            webDriverCommands.waitUntilElementVisible(homePageLocators.getSignOutButton(), 60);
            webDriverCommands.click(homePageLocators.getSignOutButton());
        }
        commonMethod.ttUserLoginAsUser(webDriverCommands,"Auto Admin");
        adminSettings.iNavigateToTheCustomerSupportPage();
        try {
            if (!user.equals(null)) {
                adminSettings.iSearchForUser(user);
            }
        }
        catch (NullPointerException e)
        {
            adminSettings.iSearchForUser(userName);
        }
        adminSettings.iSelectCustomerSupportAdminOption("Devices");
        adminSettings.iShouldBeAbleToClearUserWebResources();

        if(TestConfig.Environment.getName().equals("lt")) {
            webDriverCommands.waitUntilElementVisible(homePageLocators.getSignOutButton(), 60);
            webDriverCommands.click(homePageLocators.getSignOutButton());
        }
    }

}
