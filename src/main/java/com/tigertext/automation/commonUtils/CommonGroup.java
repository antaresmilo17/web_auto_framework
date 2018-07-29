package com.tigertext.automation.commonUtils;

import com.tigertext.automation.command.AutomationWait;
import com.tigertext.automation.command.WebDriverCommands;
import com.tigertext.automation.config.DriverConfig;
import com.tigertext.automation.config.TestConfig;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.webElements.*;
import cucumber.api.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import javax.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;


@Component
public class CommonGroup {

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
    AutomationWait automationWait;
    WebDriver webDriver;
    private WebDriverCommands webDriverCommands;

    @Autowired
    public CommonGroup(AutomationWait automationWait,
                  HomePageLocators homePageLocators,
                  ForumsPageLocators forumsPageLocators,
                  CreateForumPageLocators createForumPageLocators,
                  MessagePageLocators messagePageLocators,
                  CreateGroupPageLocators createGroupPageLocators,
                  RosterPageLocators rosterPageLocators,
                  BrowserConfig browserConfig,
                  UsersPageLocators usersPageLocators) {
        this.automationWait = automationWait;
        this.browserConfig = browserConfig;
        this.homePageLocators = homePageLocators;
        this.messagePageLocators = messagePageLocators;
        this.createGroupPageLocators = createGroupPageLocators;
        this.rosterPageLocators = rosterPageLocators;
        this.forumsPageLocators = forumsPageLocators;
        this.createForumPageLocators = createForumPageLocators;
        this.usersPageLocators = usersPageLocators;
    }

    @PostConstruct
    public void init() {
        this.webDriver = browserConfig.getWebDriver();
        this.webDriverCommands = new WebDriverCommands().webDriver(webDriver).browserConfig(browserConfig).automationWait(automationWait).build();
        this.debug = webDriverCommands.getDebug();
    }

    public boolean isGroupPresent(WebDriverCommands webDriverCommands, String groupName) throws Throwable {
        try {
            if (!(webDriverCommands.isElementDisplayed(rosterPageLocators.getRosterList()))) return false;
            String xpathToSearch = "//*[contains(text(), '" + groupName + "')][@data-class='home.name home.conv.single.name']";
            if (webDriverCommands.isElementDisplayed(By.xpath(xpathToSearch))) {
                webDriverCommands.click(xpathToSearch);
                if (groupName.contains("Existing")) {
                    webDriverCommands.click(messagePageLocators.getClearConversationButton());
                    webDriverCommands.actionsClick(messagePageLocators.getConfirmClearConversationButton());
                }
                automationWait.waitForJSandJQueryToLoad();
                return true;
            } else { automationWait.waitForJSandJQueryToLoad(); return false;}
        } catch (Exception e) { automationWait.waitForJSandJQueryToLoad(); return false; }
    }

    public boolean isForumPresentInAdminTools(WebDriverCommands webDriverCommands, String forumName) throws Throwable {
        try {
            if (!(webDriverCommands.isElementDisplayed(rosterPageLocators.getRosterList()))) return false;
            String xpathToSearch = "//*[contains(text(), '" + forumName + "')][@data-class='home.name']";
            if (webDriverCommands.isElementDisplayed(By.xpath(xpathToSearch))) {
                webDriverCommands.click(xpathToSearch);
                return true;
            } else { automationWait.waitForJSandJQueryToLoad(); return false;}
        } catch (Exception e) { automationWait.waitForJSandJQueryToLoad(); return false; }
    }

    public void leaveGroup(WebDriverCommands webDriverCommands,String groupName) throws Throwable{
        if(isGroupPresent(webDriverCommands,groupName)) {
            webDriverCommands.click(messagePageLocators.getGroupSettings());
            while (webDriverCommands.isPresent(createGroupPageLocators.getRemoveAllGroupMembers()))
                webDriverCommands.click(createGroupPageLocators.getRemoveAllGroupMembers());
            automationWait.untilElementVisible(createGroupPageLocators.getLeaveGroup(),60);
            webDriverCommands.click(createGroupPageLocators.getLeaveGroup());
            automationWait.untilElementVisible(createGroupPageLocators.getConfirmLeaveGroup(),60);
            webDriverCommands.click(createGroupPageLocators.getConfirmLeaveGroup());
            automationWait.untilElementIsNotPresent(homePageLocators.getBackFade());
            automationWait.untilElementClickable(homePageLocators.getSignOutButton());
            automationWait.waitForJSandJQueryToLoad();
        }
    }

    public void addGroupMember(WebDriverCommands webDriverCommands, String user, String groupName) throws Throwable {
        if(isGroupPresent(webDriverCommands,groupName)) {
            automationWait.waitForJSandJQueryToLoad();
            automationWait.untilElementClickable(messagePageLocators.getGroupSettings(),60);
            automationWait.untilElementVisible(messagePageLocators.getGroupSettings(),60);
            webDriverCommands.actionsClick(messagePageLocators.getGroupSettings());
            searchUserInGroup(webDriverCommands, user);
            automationWait.untilElementClickable(createGroupPageLocators.getUpdateGroupButton(),60);
            automationWait.untilElementVisible(createGroupPageLocators.getUpdateGroupButton(),60);
            webDriverCommands.click(createGroupPageLocators.getUpdateGroupButton());
            automationWait.untilElementClickable(messagePageLocators.getGroupSettings(),60);
            automationWait.untilElementVisible(messagePageLocators.getGroupSettings(),60);
        }
    }

    public void updateGroup(WebDriverCommands webDriverCommands,String groupName) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.enterText(createGroupPageLocators.getGroupName(),groupName);
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.click(createGroupPageLocators.getUpdateGroupButton());
        Thread.sleep(3000);
    }

    public void removeGroupMember(WebDriverCommands webDriverCommands, String user, String groupName) throws Throwable {
        if(isGroupPresent(webDriverCommands,groupName)) {
            automationWait.waitForJSandJQueryToLoad();
            String xpath = "(//*[contains(text(),'" + user + "')])/following-sibling::div";
            automationWait.untilElementVisible(messagePageLocators.getGroupSettings(),60);
            webDriverCommands.actionsClick(messagePageLocators.getGroupSettings());
            automationWait.waitForJSandJQueryToLoad();
            webDriverCommands.click(xpath);
            webDriverCommands.click(createGroupPageLocators.getUpdateGroupButton());
        }
    }

    public boolean removeGroupApi(WebDriverCommands webDriverCommands,String groupName) throws IOException{
        if(!groupName.equals("Auto Test Existing") || !groupName.equals("Group Settings Existing")) {
            String xpathToSearch = "//li[.//div[text()='" + groupName + "']]";
            automationWait.untilElementClickable(By.xpath(xpathToSearch));
            List<String> ids = webDriverCommands.getIdsFromElements(By.xpath(xpathToSearch));
            for (String id : ids) {
                String[] parts = id.split(Pattern.quote("."));
                String tempCommand = TestConfig.Environment.getDevApi() + "group/" + parts[0];
                String[] command = {"curl", "-H", "Accept:application/json", "--request", "DELETE", "-u", TestConfig.Environment.getApiKey() + ":" + TestConfig.Environment.getSecret(), tempCommand};
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                try {
                    processBuilder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean removeGroupWithIDApi(WebDriverCommands webDriverCommands,String ID) throws IOException {
        String[] parts = ID.split(Pattern.quote("."));
        String tempCommand = TestConfig.Environment.getDevApi() + "group/" + parts[0];
        String[] command = {"curl", "-H", "Accept:application/json", "--request", "DELETE", "-u", TestConfig.Environment.getApiKey() + ":" + TestConfig.Environment.getSecret(), tempCommand};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteUserApi(String userToken) throws IOException{
        String tempCommand = TestConfig.Environment.getDevApi() + "user/" + userToken;
        String[] command = {"curl", "-H", "Accept:application/json", "--request", "DELETE", "-u", TestConfig.Environment.getApiKey() + ":" + TestConfig.Environment.getSecret(), tempCommand};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void searchUserInGroup(WebDriverCommands webDriverCommands,String user) throws Throwable {
        webDriverCommands.enterText(createGroupPageLocators.getGroupMember(), user);
        automationWait.waitForJSandJQueryToLoad();
        automationWait.untilElementClickable(createGroupPageLocators.getGroupMemberDropDownValue(),60);
        automationWait.untilElementVisible(createGroupPageLocators.getGroupMemberDropDownValue(),60);
        webDriverCommands.click(createGroupPageLocators.getGroupMemberDropDownValue());
    }

    public void createGroup(WebDriverCommands webDriverCommands,String user1, String user2,String groupName) throws Throwable {
        automationWait.untilElementVisible(homePageLocators.getHamburgerMenu(),60);
        automationWait.untilElementClickable(homePageLocators.getHamburgerMenu(),60);
        webDriverCommands.click(homePageLocators.getHamburgerMenu());
        automationWait.untilElementVisible(homePageLocators.getCreateGroup(),60);
        webDriverCommands.click(homePageLocators.getCreateGroup());
        webDriverCommands.enterText(createGroupPageLocators.getGroupName(), groupName);
        searchUserInGroup(webDriverCommands, user1);
        searchUserInGroup(webDriverCommands, user2);
        webDriverCommands.click(createGroupPageLocators.getCreateGroupButton());
        automationWait.untilElementClickable(messagePageLocators.getNewMessageButton());
    }

    public void createForum(WebDriverCommands webDriverCommands,String user1, String user2,String forumName) throws Throwable {
        navigateToForumsTab(webDriverCommands);
        automationWait.untilElementVisible(forumsPageLocators.getCreateNewForumButton(),60);
        automationWait.untilElementClickable(forumsPageLocators.getCreateNewForumButton(),60);
        webDriverCommands.click(forumsPageLocators.getCreateNewForumButton());
        webDriverCommands.enterText(createForumPageLocators.getForumName(), forumName);
        searchUserForum(webDriverCommands, user1);
        searchUserForum(webDriverCommands, user2);
        automationWait.untilElementVisible(createForumPageLocators.getCreateForumButton(),60);
        automationWait.untilElementClickable(createForumPageLocators.getCreateForumButton(),60);
        webDriverCommands.click(createForumPageLocators.getCreateForumButton());
    }
 
     public void navigateToForumsTab(WebDriverCommands webDriverCommands) throws Throwable {
        //adding due to element overlap and being visible/clickable before loading page is gone
         automationWait.untilPageLoads();
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.switchDefaultFrame();
        automationWait.untilElementVisible(homePageLocators.getTigerTextHomeLogo(),60);
        automationWait.untilElementClickable(homePageLocators.getTigerTextHomeLogo(),60);
        //webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
         try {
             automationWait.untilElementVisible(homePageLocators.getSettingsButton(), 60);
             automationWait.untilElementClickable(homePageLocators.getSettingsButton(), 60);
             webDriverCommands.actionsClick(homePageLocators.getSettingsButton());
         }catch (Exception e)
         {
             automationWait.untilElementVisible(homePageLocators.getSettingsSelectedButton(), 60);
             automationWait.untilElementClickable(homePageLocators.getSettingsSelectedButton(), 60);
             webDriverCommands.actionsClick(homePageLocators.getSettingsSelectedButton());

         }


        //webDriverCommands.click();


         if(TestConfig.Environment.getName().equals("env1")||TestConfig.Environment.getName().equals("env4")) {
             automationWait.waitForJSandJQueryToLoad();
             webDriverCommands.switchFrameByInt(0);
         }
        automationWait.untilElementVisible(forumsPageLocators.getForumsTab(),60);
        automationWait.untilElementClickable(forumsPageLocators.getForumsTab(),60);
        webDriverCommands.click(forumsPageLocators.getForumsTab());
        automationWait.untilPageLoads();

     }

     public void addForumMember(WebDriverCommands webDriverCommands, String user, String forumName) throws Throwable {
         //navigateToForumsTab(webDriverCommands);
         if (isForumPresentAdmin(webDriverCommands, forumName)) {
             editForum(webDriverCommands, forumName);
             searchUserForum(webDriverCommands, user);
             webDriverCommands.click(createForumPageLocators.getUpdateForumButton());
//             automationWait.waitForJSandJQueryToLoad();
         }
         webDriverCommands.switchDefaultFrame();
         webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
     }

     public void editForum(WebDriverCommands webDriverCommands, String forumName) throws Throwable {
         String editButtonXpath = "(//*[.='" + forumName +
         "'])/following-sibling::td[@data-class='home.admin.rooms.room.edit']/div[@class='edit']";
         automationWait.untilElementVisible(By.xpath(editButtonXpath),60);
         automationWait.untilElementClickable(By.xpath(editButtonXpath),60);

         if (webDriverCommands.isPresent(By.xpath(editButtonXpath))) {
             webDriverCommands.click(editButtonXpath);
         }
     }

     public void updateForumName(WebDriverCommands webDriverCommands,String forumName, String newForumName) throws Throwable {
         navigateToForumsTab(webDriverCommands);
         if (isForumPresentAdmin(webDriverCommands, forumName)) {
             editForum(webDriverCommands, forumName);
             webDriverCommands.enterText(createForumPageLocators.getForumName(), newForumName);
             webDriverCommands.click(createForumPageLocators.getUpdateForumButton());
         }

         if(TestConfig.Environment.getName().equals("env4") ||TestConfig.Environment.getName().equals("env1")) {
             automationWait.waitForJSandJQueryToLoad();
             webDriverCommands.switchDefaultFrame();
         }

         webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
     }

     public void deleteForum(WebDriverCommands webDriverCommands,String forumName) throws Throwable{
         navigateToForumsTab(webDriverCommands);
         String deleteButtonXpath = "//*[.='" + forumName +
                 "']/following-sibling::td[@data-class='home.admin.rooms.room.confirm_delete']/div[@class='delete']";

         automationWait.untilElementVisible(By.xpath(deleteButtonXpath), 60);
         automationWait.untilElementClickable(By.xpath(deleteButtonXpath),60);
         webDriverCommands.click(By.xpath(deleteButtonXpath));
         automationWait.untilElementVisible(createForumPageLocators.getDeleteForumButton(),60);
         automationWait.untilElementClickable(createForumPageLocators.getDeleteForumButton());
         webDriverCommands.click(createForumPageLocators.getDeleteForumButton());
         automationWait.waitForJSandJQueryToLoad();
         automationWait.untilElementVisible(forumsPageLocators.getForumsTab(),60);
         automationWait.untilElementClickable(forumsPageLocators.getForumsTab(),60);
         webDriverCommands.click(forumsPageLocators.getForumsTab());
     }

     public void removeForumMember(WebDriverCommands webDriverCommands, String user, String forumName) throws Throwable {
         navigateToForumsTab(webDriverCommands);
         if (isForumPresentAdmin(webDriverCommands, forumName)) {
             editForum(webDriverCommands, forumName);
             String xpath = "(//*[.='" + user + "'])/following-sibling::div";
             automationWait.waitForJSandJQueryToLoad();
             webDriverCommands.actionsClick(By.xpath(xpath));
             webDriverCommands.click(createForumPageLocators.getUpdateForumButton());
         }
//         webDriverCommands.waitUntilElementVisible(homePageLocators.getTigerTextHomeLogo(),120);
//         automationWait.untilElementClickable(homePageLocators.getTigerTextHomeLogo(), 120);
//         webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
     }

     public boolean isForumPresentAdmin(WebDriverCommands webDriverCommands, String forumName) throws Throwable {
         return webDriverCommands.isPresent(forumsPageLocators.getForumResults()) && webDriverCommands.getText(forumsPageLocators.getForumResults()).contains(forumName);
     }

     public void searchUserForum(WebDriverCommands webDriverCommands,String user) throws Throwable {
        automationWait.untilElementVisible(createForumPageLocators.getForumUserSearchBox(),60);
        automationWait.untilElementClickable(createForumPageLocators.getForumUserSearchBox(),60);
        try {
            webDriverCommands.enterText(createForumPageLocators.getForumUserSearchBox(), user);
        }
        catch (StaleElementReferenceException e)
        {
            webDriverCommands.enterText(createForumPageLocators.getForumUserSearchBox(), user);

        }
         automationWait.untilElementVisible(createForumPageLocators.getForumUserDropDownValue(),60);
         automationWait.untilElementClickable(createForumPageLocators.getForumUserDropDownValue(),60);
         webDriverCommands.click(createForumPageLocators.getForumUserDropDownValue());
     }

     public boolean isUserPresentForum(WebDriverCommands webDriverCommands,String user,String forumName) throws Throwable {
         navigateToForumsTab(webDriverCommands);
         automationWait.untilPageLoads();
         automationWait.waitForJSandJQueryToLoad();
         if (!(isForumPresentAdmin(webDriverCommands, forumName))) return false;
         editForum(webDriverCommands, forumName);
         automationWait.untilPageLoads();
         automationWait.waitForJSandJQueryToLoad();
         automationWait.untilElementVisible(createForumPageLocators.getListForumMembers(),60);
         automationWait.untilElementClickable(createForumPageLocators.getListForumMembers(),60);
         return webDriverCommands.getText(createForumPageLocators.getListForumMembers()).contains(user);
     }

     public void removeForumAdmin(WebDriverCommands webDriverCommands,String forumName) throws Throwable {
         automationWait.waitForJSandJQueryToLoad();
         webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
         automationWait.untilElementClickable(messagePageLocators.getNewMessageButton());
         removeGroupApi(webDriverCommands, forumName);
         //The code below is just time filler so that there is some time after deleting the forum to take place on the site
         webDriverCommands.click(homePageLocators.getSettingsButton());
         automationWait.untilElementClickable(usersPageLocators.getUserSelectAllCheckbox());
         webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
         webDriverCommands.click(homePageLocators.getSettingsButton());
         automationWait.untilElementClickable(usersPageLocators.getUserSelectAllCheckbox());
         webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
     }

    public void removeForumAdmin(WebDriverCommands webDriverCommands,String forumName, String ID) throws Throwable {
        automationWait.waitForJSandJQueryToLoad();
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
        automationWait.untilElementClickable(messagePageLocators.getNewMessageButton());
        removeGroupWithIDApi(webDriverCommands, ID);
        webDriverCommands.click(homePageLocators.getSettingsButton());
        webDriverCommands.click(homePageLocators.getTigerTextHomeLogo());
    }
}
