package stepdef;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.commonUtils.CommonMethod;
import com.tigertext.automation.commonUtils.StaticData;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.scenarioSettings.BaseForTest;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.*;
import com.tigertext.automation.webdriver.driver.ChromeDriverConfig;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.tigertext.automation.commonUtils.StaticData.hashMapURL;


import net.lightbody.bmp.core.har.Har;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Login extends BaseForTest{

    private WebDriverCommands webDriverCommands;
    private HomePageLocators homePageLocators;
    private LoginPageLocators loginPageLocators;
    private OneLoginPageLocators oneLoginPageLocators;
    private PasswordSetupLocators passwordSetupLocators;
    private BrowserConfig browserConfig;
    private CommonMethod commonMethod;
    private MessagePageLocators messagePageLocators;
    private static final Logger LOG = LoggerFactory.getLogger(BrowserConfig.class);
    AutomationWait automationWait;
    Scenario scenario;
    WebDriver webDriver;

    @Autowired
    public Login(
            HomePageLocators homePageLocators,
            LoginPageLocators loginPageLocators,
            OneLoginPageLocators oneLoginPageLocators,
            MessagePageLocators messagePageLocators,
            BrowserConfig browserConfig,
            AutomationWait automationWait,
            PasswordSetupLocators passwordSetupLocators,
            CommonMethod commonMethod
    ) {
        this.homePageLocators = homePageLocators;
        this.loginPageLocators = loginPageLocators;
        this.oneLoginPageLocators = oneLoginPageLocators;
        this.browserConfig = browserConfig;
        this.automationWait = automationWait;
        this.passwordSetupLocators = passwordSetupLocators;
        this.messagePageLocators = messagePageLocators;
        this.commonMethod = commonMethod;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.automationWait.init();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();

    }

    @And("^I enter \"([^\"]*)\" credentials$")
    public void iEnterUserCredentials(String userAccount) throws Throwable {
        if(userAccount.toLowerCase().equals("saml"))
        {
            iLogOutOfOneLogin();
        }
        commonMethod.ttUserLogin(webDriverCommands, userAccount);

    }


    @Then("^I should see error message$")
    public void iShouldSeeErrorMessage() throws Throwable {
        Assert.assertTrue(webDriverCommands.getText(loginPageLocators.getErrorUserName()).contains("There was a problem verifying your account information"));
    }

    @When("^I enter blank username$")
    public void iClickLoginButtonWithoutProvidingUsername() throws Throwable {
        webDriverCommands.click(loginPageLocators.getLoginUserNameButton());
    }

    @When("^I enter incorrect username$")
    public void iEnterIncorrectUsername() throws Throwable {
        webDriverCommands.enterText(loginPageLocators.getUserName(), "unknown@test.com");
        webDriverCommands.click(loginPageLocators.getLoginUserNameButton());
    }

    @When("^I enter correct username$")
    public void iEnterCorrectUsername() throws Throwable {
        webDriverCommands.enterText(loginPageLocators.getUserName(), TestConfig.Environment.getUserEmail("WebQa1"));
        webDriverCommands.click(loginPageLocators.getLoginUserNameButton());
    }

    @And("^I click Forgot Password$")
    public void iClickForgotPassword() throws Throwable {
        webDriverCommands.actionsClick(loginPageLocators.getForgotPasswordButton());
    }

    @Then("^I should see Password setup Page$")
    public void iShouldSeePasswordSetupPage() throws Throwable {
        automationWait.untilElementVisible(passwordSetupLocators.getUserName(),60);
        Assert.assertTrue(webDriverCommands.isVisible(passwordSetupLocators.getUserName()));
    }

    @When("^I click Sign out button$")
    public void iClickSignOutButton() throws Throwable {
        automationWait.untilElementVisible(homePageLocators.getSignOutButton(),60);
        automationWait.untilElementClickable(homePageLocators.getSignOutButton(),60);
        webDriverCommands.click(homePageLocators.getSignOutButton());
    }

    @When("^I click the OneLogin log in button$")
    public void iClickTheOneLoginLogInButton() throws Throwable {
        webDriverCommands.click(oneLoginPageLocators.getOneLoginCommitButton());
    }

    @Then("^I should see TigerText (.*) page$")
    public void iShouldSeeTigerTextPage(String page) throws Throwable {
        String lpage =page.trim().toLowerCase();
        automationWait.untilPageLoads();
        switch(lpage) {
            case "login":
                automationWait.untilElementClickable(loginPageLocators.getUserName(),60);
                automationWait.untilElementVisible(loginPageLocators.getUserName(),60);
                Assert.assertTrue(webDriverCommands.isElementDisplayed(loginPageLocators.getUserName()));
                break;
            case "home":
                automationWait.untilElementClickable(homePageLocators.getTigerTextHomeLogo());
                Assert.assertTrue(webDriverCommands.isElementDisplayed(homePageLocators.getTigerTextHomeLogo()));
        }
    }

    @Then("^I should see the user inbox$")
    public void iShouldSeeTheUserInbxo() throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        automationWait.untilElementsVisible(messagePageLocators.getNewMessageButton(),60);
        Assert.assertTrue(webDriverCommands.isElementDisplayed(messagePageLocators.getNewMessageButton()));
        iClickSignOutButton();
        iShouldSeeTigerTextPage("login");
    }

    @Given("^I am logged in to TigerText Home page$")
    public void iAmLoggedInToTigerTextHomePage() throws Throwable {
        commonMethod.ttUserLogin(webDriverCommands,"correct");
    }

    @Then("^I should see \"([^\"]*)\" page$")
    public void iShouldSeePage(String downloadPageType) throws Throwable {

        String currentURL = "";
        String storedURL = "";
        String storedURLAlt = "";

        if(downloadPageType.toLowerCase().equals("tigertext download")) {
            automationWait.untilElementVisible(loginPageLocators.getTigerconnectDownloadPageheaderMessage(), 60);
        }
        else if(downloadPageType.toLowerCase().equals("terms"))
        {
            automationWait.untilElementVisible(loginPageLocators.getTigerconnectEndUserPageHeaderMessage(), 60);

        }
        else if(downloadPageType.toLowerCase().equals("privacy"))
        {
            automationWait.untilElementVisible(loginPageLocators.getTigerconnectPrivacyPageHeaderMessage(), 60);

        }
        else if(downloadPageType.toLowerCase().equals("support"))
        {
            automationWait.untilElementVisible(loginPageLocators.getTigerconnectSupportPageHeaderMessage(), 60);

        }
        else if(downloadPageType.equals("TigerText Business")){
            automationWait.waitForJSandJQueryToLoad();
            //automationWait.untilElementVisible(loginPageLocators.getCallUsPhoneNumber(),60);
            currentURL = webDriverCommands.getURL();
            storedURL = hashMapURL.get(downloadPageType);
            webDriverCommands.closeChildWindow();
            storedURLAlt = hashMapURL.get(downloadPageType + " Alt");


            logger.info("current url: " + currentURL);
            logger.info("current url: " + storedURL);
            logger.info("current url: " + storedURLAlt);
            if(!currentURL.contains(storedURL))
                Assert.assertTrue(currentURL.contains(storedURLAlt));
            else
                Assert.assertTrue(currentURL.contains(storedURL));
        }else
            Assert.assertTrue(currentURL.contains(storedURL));
    }

    @Then("^I should be asked to \"([^\"]*)\"$")
    public void iShouldBeAskedTo(String TryAgain) throws Throwable {
        Assert.assertTrue(webDriverCommands.getText(loginPageLocators.getTryAgainButton()).equals(TryAgain));
    }

    @When("^I click \"([^\"]*)\" link$")
    public void iClickLink(String Text) throws Throwable {
        automationWait.untilPageLoads();
        By locator = null;
        String lText =Text.trim().toLowerCase();
        switch(lText) {
            case "download now":
                locator = loginPageLocators.getDownloadNow();
                break;
            case "iphone":
                locator = loginPageLocators.getiPhone();
                break;
            case "ipad":
                locator = loginPageLocators.getiPad();
                break;
            case "android":
                locator = loginPageLocators.getAndroid();
                break;
            case "terms":
                locator = loginPageLocators.getTerms();
                break;
            case "privacy":
                locator = loginPageLocators.getPrivacy();
                break;
            case "tigertext business":
                locator = loginPageLocators.getBusinessLink();
        }
        automationWait.untilElementClickable(locator,60);
        webDriverCommands.click(locator);
        webDriverCommands.switchToActiveWindow();
    }

    @When("^I click Support link$")
    public void iClickSupportLink() throws Throwable {
       automationWait.untilElementVisible(loginPageLocators.getSupport(),120);
       try {
           webDriverCommands.click(loginPageLocators.getSupport());
       }catch(Exception e){};
   }

    @Given("^I am on TigerText Login Page$")
    public void iAmOnTigerTextLoginPage() throws Throwable {
        commonMethod.navigateHomePage(this.webDriverCommands);
    }

    @When("^I wait for (.*) minutes")
    public void waitForNumberOfMinutes(int minutes) throws Throwable{
        try {
            webDriverCommands.click(loginPageLocators.getMessageBubble());
        }catch(Exception e){
            LOG.info("There is no Message Bubble to click");
        }
        webDriverCommands.waitUntilElementVisible(loginPageLocators.getUserName(),(minutes + 2) * 60);
    }

    @Then("^I should see the timeout screen")
    public void timeoutScreen() throws Throwable{
        Assert.assertTrue(webDriverCommands.isVisible(loginPageLocators.getUserName()));
    }

    private void iLogOutOfOneLogin() throws Throwable {
        iSwitchURL(StaticData.hashMapURL.get("oneLogin"));
        String samlUserName = TestConfig.Environment.getUserEmail("SAML");
        iEnterOneLogInCredentials(samlUserName);
        iClickTheOneLoginLogInButton();
        iLogOutOfOneLoginPage();
        iSwitchURL(TestConfig.Environment.getURL());
    }

    private void iLogOutOfOneLoginPage() throws Throwable {
        automationWait.untilElementVisible(oneLoginPageLocators.getOneLoginLogOutButton(),60);
        automationWait.untilElementClickable(oneLoginPageLocators.getOneLoginLogOutButton(),60);
        webDriverCommands.click(oneLoginPageLocators.getOneLoginLogOutButton());
        automationWait.untilElementVisible(oneLoginPageLocators.getOneLoginLogOutOptionButton(),60);
        automationWait.untilElementClickable(oneLoginPageLocators.getOneLoginLogOutOptionButton(),60);
        webDriverCommands.click(oneLoginPageLocators.getOneLoginLogOutOptionButton());
        webDriverCommands.waitUntilElementVisible(oneLoginPageLocators.getOneLoginLogo(),60);
        Assert.assertTrue(webDriverCommands.isElementDisplayed(oneLoginPageLocators.getOneLoginLogo()));
    }

    private void iEnterOneLogInCredentials(String samlUserName) throws Throwable {
        webDriverCommands.waitUntilElementVisible(oneLoginPageLocators.getOneLoginLogo(),60);
        Assert.assertTrue(webDriverCommands.isElementDisplayed(oneLoginPageLocators.getOneLoginLogo()));
        automationWait.untilElementVisible(oneLoginPageLocators.getOneLoginUserName(),60);
        automationWait.untilElementClickable(oneLoginPageLocators.getOneLoginUserName(),60);
        webDriverCommands.enterText(oneLoginPageLocators.getOneLoginUserName(), samlUserName);
        webDriverCommands.enterText(oneLoginPageLocators.getOneLoginPassword(), TestConfig.Environment.getUserPassword("SAML"));
    }

    private void iSwitchURL(String oneLogin) {
        this.webDriver.navigate().to(oneLogin);
        this.webDriver.switchTo().activeElement();
    }

}
