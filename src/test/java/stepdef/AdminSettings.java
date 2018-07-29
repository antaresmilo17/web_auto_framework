package stepdef;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.commonUtils.CommonGroup;
import com.tigertext.automation.commonUtils.CommonMethod;
import com.tigertext.automation.commonUtils.ImportTestUsers;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.enums.CustomerSupportAdminOptionTypes;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.*;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.tigertext.automation.commonUtils.StaticData.hashMapMessages;

public class AdminSettings {

    private WebDriverCommands webDriverCommands;
    private BrowserConfig browserConfig;
    private CommonMethod commonMethod;
    private CommonGroup commonGroup;
    private ImportTestUsers importTestUsers;
    private MessagePageLocators messagePageLocators;
    private UserSettingsPageLocators userSettingsPageLocators;
    private CreateGroupPageLocators createGroupPageLocators;
    private HomePageLocators homePageLocators;
    private MessageInfoPageLocators messageInfoPageLocators;
    private UsersPageLocators usersPageLocators;
    AutomationWait automationWait;
    WebDriver webDriver;
    CustomerSupportAdminOptionTypes customerSupportAdminOptionTypes;

    @Autowired
    public AdminSettings(
            BrowserConfig browserConfig,
            AutomationWait automationWait,
            CommonMethod commonMethod,
            ImportTestUsers importTestUsers,
            MessagePageLocators messagePageLocators,
            MessageInfoPageLocators messageInfoPageLocators,
            CreateGroupPageLocators createGroupPageLocators,
            UserSettingsPageLocators userSettingsPageLocators,
            HomePageLocators homePageLocators,
            UsersPageLocators usersPageLocators,
            CommonGroup commonGroup
    ) {
        this.browserConfig = browserConfig;
        this.automationWait = automationWait;
        this.commonMethod = commonMethod;
        this.importTestUsers = importTestUsers;
        this.messagePageLocators = messagePageLocators;
        this.createGroupPageLocators = createGroupPageLocators;
        this.homePageLocators = homePageLocators;
        this.usersPageLocators = usersPageLocators;
        this.commonGroup = commonGroup;
        this.userSettingsPageLocators = userSettingsPageLocators;
        this.messageInfoPageLocators = messageInfoPageLocators;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.automationWait.init();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
    }

    @When("^I create users \"([^\"]*)\"$")
    public void iCreateUsers(String users) throws Throwable {
        commonMethod.createNewUsers(webDriverCommands,users);
    }

    @When("^I import users$")
    public void iImportUsers() throws Throwable {
        importTestUsers.importNewUsers(webDriverCommands);
    }

    @Then("^User \"([^\"]*)\" created$")
    public void userCreated(String user) throws Throwable {
        automationWait.untilPageLoads();
        automationWait.untilElementVisible(usersPageLocators.getAddUser(),60);
        automationWait.untilElementClickable(usersPageLocators.getAddUser(),60);
        Assert.assertTrue(webDriverCommands.getText(usersPageLocators.getListUsers()).contains(TestConfig.Environment.getUserFirstName(user)));
        Assert.assertTrue(webDriverCommands.getText(usersPageLocators.getListUsers()).contains(TestConfig.Environment.getUserLastName((user))));
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
        automationWait.untilPageLoads();
    }

    @Then("^users \"([^\"]*)\" are created$")
    public void usersCreated(String user) throws Throwable {

        automationWait.untilPageLoads();
        automationWait.untilElementVisible(usersPageLocators.getAddUser(),60);
        automationWait.untilElementClickable(usersPageLocators.getAddUser(),60);
        String[] users = user.split(", ");
        int amount_of_users = users.length;

        for (int x =0; x < amount_of_users; x++) {
            Assert.assertTrue(webDriverCommands.getText(usersPageLocators.getListUsers()).contains(TestConfig.Environment.getUserFirstName(users[x])));
            Assert.assertTrue(webDriverCommands.getText(usersPageLocators.getListUsers()).contains(TestConfig.Environment.getUserLastName(users[x])));
        }
        webDriverCommands.switchDefaultFrame();
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
        automationWait.untilPageLoads();
    }

    @Then("^users are created$")
    public void usersCreated() throws Throwable {
        automationWait.untilPageLoads();
        automationWait.untilElementClickable(usersPageLocators.getUsersCount());
        String[] numOfUsers = webDriverCommands.getText(usersPageLocators.getUsersCount()).split(" ");
        if(Integer.parseInt(numOfUsers[0]) == 0){
            Assert.fail("Import did not succeed! The number of users on the user's table is displaying 0 users");
        }
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
        automationWait.untilPageLoads();
    }

    @When("^I delete users \"([^\"]*)\"$")
    public void iDeleteUsers(String user) throws Throwable {
        commonMethod.deleteUsers(webDriverCommands,user);
    }

    @When("^I create custom organizations$")
    public void iCreateOrganizations() throws Throwable {
        commonMethod.navigateToAdminOption(webDriverCommands,"organizations");
        importTestUsers.createCustomOrganizations(webDriverCommands);
    }

    @When("^I create a single custom organization$")
    public void iCreateSingleOrganization() throws Throwable {
        commonMethod.navigateToCustomerSupportTab(webDriverCommands);
        importTestUsers.createSingleCustomOrganization(webDriverCommands);
    }

    @When("^I navigate to the customer support page$")
    public void iNavigateToTheCustomerSupportPage() throws Throwable {
        commonMethod.navigateToCustomerSupportTab(webDriverCommands);

    }

    @When("^I search for user \"([^\"]*)\"$")
    public void iSearchForUser(String username) throws Throwable {
        webDriverCommands.waitUntilElementVisible(userSettingsPageLocators.getCustomerSupportTabUserSearchTextfield(),20);
        String userEmail = TestConfig.Environment.getUserEmail(username);
        webDriverCommands.enterText(userSettingsPageLocators.getCustomerSupportTabUserSearchTextfield(),userEmail);
        webDriverCommands.click(userSettingsPageLocators.getCustomerSupportTabUserSearchButton());
        webDriverCommands.waitUntilElementVisible(userSettingsPageLocators.getCustomerSupportTabHomeUserName(),20);
    }

    @When("^I select customer support \"([^\"]*)\" admin option$")
    public void iSelectCustomerSupportAdminOption(String adminOption) throws Throwable {
        customerSupportAdminOptionTypes = CustomerSupportAdminOptionTypes.valueOf(adminOption.replace(" ","").toUpperCase());
        switch (customerSupportAdminOptionTypes){
            case DEVICES:
                webDriverCommands.click(userSettingsPageLocators.getCustomerSupportAdminDevicesOption());
                break;
            case DELETEUSER:
                break;
            case FORCELOGOUT:
                break;
            case SETPASSWORD:
                break;
            default:
                break;
        }
    }

    @When("^I should be able to clear user web resources")
    public void iShouldBeAbleToClearUserWebResources() throws Throwable {
        automationWait.untilPageLoads();
        webDriverCommands.waitUntilElementVisible(userSettingsPageLocators.getCustomerSupportAdminClearCNbutton(),20);
        webDriverCommands.click(userSettingsPageLocators.getCustomerSupportAdminClearCNbutton());
        webDriverCommands.waitUntilElementVisible(userSettingsPageLocators.getCustomerSupportAdminNotifyMessagePopUp(),30);
        Assert.assertTrue(webDriverCommands.isPresent(userSettingsPageLocators.getCustomerSupportAdminNotifyMessagePopUp()));
    }

    @Then("^User \"([^\"]*)\" is deleted$")
    public void userIsDeleted(String user) throws Throwable {
        commonMethod.navigateToUsersTab(webDriverCommands);
        automationWait.untilPageLoads();
        Assert.assertFalse(commonMethod.isUserPresent(webDriverCommands,user));
        webDriverCommands.scrollUp();
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
        automationWait.untilPageLoads();
    }

    @Then("^users \"([^\"]*)\" are deleted$")
    public void usersAreDeleted(String user) throws Throwable {
        commonMethod.navigateToUsersTab(webDriverCommands);
        automationWait.untilPageLoads();
        automationWait.untilElementClickable(usersPageLocators.getUserSelectAllCheckbox());
        String[] users = user.split(", ");
        int amount_of_users = users.length;

        for (int x =0; x < amount_of_users; x++) {
            Assert.assertFalse(commonMethod.isUserPresent(webDriverCommands,users[x]));
        }

        webDriverCommands.scrollUp();
        webDriverCommands.switchDefaultFrame();
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
        automationWait.untilPageLoads();
    }

    @Then("^the message is delivered to new group \"([^\"]*)\"$")
    public void theMessageIsDeliveredToNewGroup(String groupName) throws Throwable {
        automationWait.untilPageLoads();
        String message = hashMapMessages.get(groupName);
        Assert.assertTrue(message.equals(webDriverCommands.getText(messagePageLocators.getMessageContent())));
        Assert.assertTrue(webDriverCommands.isPresent(messagePageLocators.getMessageSentStatus()) ||
                webDriverCommands.isPresent(messagePageLocators.getMessageDeliveredStatus()));
        commonGroup.leaveGroup(webDriverCommands,groupName);
    }

    @Then("^I see sender as \"([^\"]*)\" and Receiver as \"([^\"]*)\" of the new group$")
    public void iSeeSenderAsAndReceiverAsOfTheNewGroup(String sender, String receiver) throws Throwable {
        Assert.assertTrue(sender.contains(webDriverCommands.getText(messageInfoPageLocators.getMessageInfoSenderName())));
        Assert.assertTrue(receiver.contains(webDriverCommands.getText(messageInfoPageLocators.getMessageInfoReceiverName())));
        webDriverCommands.click(messageInfoPageLocators.getMessageCloseButton());
        commonGroup.leaveGroup(webDriverCommands,receiver);

    }

    @Then("^the custom organizations are created$")
    public void theCustomOrganizationsAreCreated() throws Throwable {
        importTestUsers.theCustomOrgsAreCreated(webDriverCommands);
    }

    @Then("^the custom organization is created$")
    public void theCustomOrganizationIsCreated() throws Throwable {
        importTestUsers.theCustomOrgIsCreated(webDriverCommands);
    }

    @Then("^the org is switched to the new org created$")
    public void theOrgIsSwitchToTheNewOrgCreated() throws Throwable {
        importTestUsers.theOrgIsSwitched(webDriverCommands);
    }
}
