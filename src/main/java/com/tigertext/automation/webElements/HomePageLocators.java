package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomePageLocators{

    public HomePageLocators() {
    }

    public static final String loadingEmailPath = "//*[@class='email']/descendant::*[@id='loadingEmail']";
    public static final String loadingBarPath = "//*[@class='loadingBarCntr']";
    public static final String helpOverLayPath = "//*[@id='helpOverlay']";
    public static final String signOutButtonPath = "//*[@data-original-title='Sign Out']";
    public static final String hamburgerMenuPath = ".menu";
    public static final String createGroupPath = ".group";
    public static final String settingsButtonPath = "//*[@class='iconsCntr']/descendant::*[@class='settings']";
    public static final String settingsSelectedButtonPath = "//*[@class='iconsCntr']/descendant::*[@class='settings selected']";

    public static final String backFadePath = "//*[@class='modal-backdrop fade in']";
    public static final String helpOverlayBackPath = "#helpOverlay > div.helpBack";
    public static final String loginProgressPath = "loginPrgrs";
    public static final String loadingControlPath = "//*[@class='loadingCntr']";
    public static final String tigerTextHomeLogoPath = ".brand>span>img";
    public static final String finishedLoadingControllerPath = ".//*[@id='loadingCntr' and @style='display: none;']";
    public static final String backFadeAllPath = "[class*='modal-backdrop']";
    public static final String exploreForumsButtonPath = "[data-class='home.conv.rooms.open']";

    private By loadingEmail = By.xpath(loadingEmailPath);
    private By loadingBar = By.xpath(loadingBarPath);
    private By loadingControl = By.xpath(loadingControlPath);
    private By helpOverLay = By.xpath(helpOverLayPath);
    private By signOutButton = By.xpath(signOutButtonPath);
    private By hamburgerMenu = By.cssSelector(hamburgerMenuPath);
    private By createGroup = By.cssSelector(createGroupPath);
    private By settingsButton = By.xpath(settingsButtonPath);
    private By settingsSelectedButton = By.xpath(settingsSelectedButtonPath);
    private By backFade = By.xpath(backFadePath);
    private By helpOverlayBack = By.cssSelector(helpOverlayBackPath);
    private By loginProgress = By.className(loginProgressPath);
    private By tigerTextHomeLogo = By.cssSelector(tigerTextHomeLogoPath);
    private By finishedLoadingController = By.xpath(finishedLoadingControllerPath);
    private By backFadeAll = By.cssSelector(backFadeAllPath);
    private By exploreForumsButton = By.cssSelector(exploreForumsButtonPath);

    public By getLoadingEmail() {
        return loadingEmail;
    }
    public By getLoadingControl() {
        return loadingControl;
    }
    public By getLoadingBar() {
        return loadingBar;
    }
    public By getHelpOverLay() {return helpOverLay;}
    public By getSignOutButton () {return signOutButton;}
    public By getHamburgerMenu() {return hamburgerMenu;}
    public By getCreateGroup() {return createGroup;}
    public By getSettingsButton() {return settingsButton;}
    public By getSettingsSelectedButton() {
        return settingsSelectedButton;
    }
    public By getBackFade() {return backFade;}
    public By getHelpOverlayBack() {return helpOverlayBack;}
    public By getLoginProgress() {return loginProgress;}
    public By getTigerTextHomeLogo() {return tigerTextHomeLogo;}
    public By getFinishedLoadingController() {return finishedLoadingController;}
    public By getBackFadeAll() {return backFadeAll;}
    public By getExploreForumsButton() {return exploreForumsButton;}
}