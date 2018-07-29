package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class LoginPageLocators {
    public LoginPageLocators() {
    }
    //need to refactor by moving static data to the static data file and change name to CAPS
    public static final String userNamePath = "//*[@data-class='index.input_login']";
    public static final String callUsPhoneNumberPath = "//*[@class='off-canvas-content']/descendant::*[contains(text(),'Sales')]";
    public static final String loginUserNameButtonPath = "//*[@data-class='index.login_check']";
    public static final String loginButtonPath = "//*[@data-class='index.login']";
    public static final String userPasswordPath= "//*[@data-class='index.input_password']";
    public static final String errorUserNamePath = "//*[@data-class='index.login_error']";
    public static final String tryAgainButtonPath = "//*[@data-class='index.try_again']";
    public static final String forgotPasswordButtonPath = "//*[@data-class='index.forgot_password']";
    public static final String downloadNowPath = "//*[@id='imgBanner']";
    public static final String iPhonePath = "iphone";
    public static final String iPadPath = "ipad";
    public static final String androidPath = "android";
    public static final String termsPath =  "Terms";
    public static final String privacyPath =  "//a[contains(text(),'Privacy')]";
    public static final String businessLinkPath =  "//a[contains(text(),'Learn More About TigerConnect for Business')]";
    public static final String supportPath =  "//a[contains(text(),'Contact Support')]";
    public static final String timeoutPath = "//div[@data-class='index.login_error' and contains(text(), 'Please sign in again')]";
    public static final String messageBubblePath = ".helpBlockMessages .block";
    public static final String tigerconnectDownloadPageHeaderMessagePath = "//h1[contains(text(),'Download our Healthcare Messaging App')]";
    public static final String tigerconnectEndUserPageHeaderMessagePath = "//h1[contains(text(),'TigerConnect End-User License Agreement')]";
    public static final String tigerconnectPrivacyPageHeaderMessagePath = "//h1[contains(text(),'TigerConnect Privacy Policy')]";
    public static final String tigerconnectSupportPageHeaderMessagePath = "//h1[contains(text(),'Support')]";

    private By userName = By.xpath(userNamePath);
    private By callUsPhoneNumber = By.xpath(callUsPhoneNumberPath);
    private By tigerconnectDownloadPageheaderMessage = By.xpath(tigerconnectDownloadPageHeaderMessagePath);
    private By tigerconnectEndUserPageHeaderMessage = By.xpath(tigerconnectEndUserPageHeaderMessagePath);
    private By tigerconnectPrivacyPageHeaderMessage = By.xpath(tigerconnectPrivacyPageHeaderMessagePath);
    private By tigerconnectSupportPageHeaderMessage = By.xpath(tigerconnectSupportPageHeaderMessagePath);
    private By loginUserNameButton = By.xpath(loginUserNameButtonPath);
    private By loginButton = By.xpath(loginButtonPath);
    private By userPassword = By.xpath(userPasswordPath);
    private By errorUserName = By.xpath(errorUserNamePath);
    private By tryAgainButton = By.xpath(tryAgainButtonPath);
    private By forgotPasswordButton = By.xpath(forgotPasswordButtonPath);
    private By downloadNow = By.xpath(downloadNowPath);
    private By iPhone = By.className(iPhonePath);
    private By iPad = By.className(iPadPath);
    private By android = By.className(androidPath);
    private By terms =  By.linkText(termsPath);
    private By privacy =  By.xpath(privacyPath);
    private By businessLink =  By.xpath(businessLinkPath);
    private By support =  By.xpath(supportPath);
    private By timeout = By.xpath(timeoutPath);
    private By messageBubble = By.cssSelector(messageBubblePath);

    public By getTigerconnectDownloadPageheaderMessage() {
        return tigerconnectDownloadPageheaderMessage;
    }
    public By getTigerconnectEndUserPageHeaderMessage() {
        return tigerconnectEndUserPageHeaderMessage;
    }
    public By getTigerconnectPrivacyPageHeaderMessage() {
        return tigerconnectPrivacyPageHeaderMessage;
    }
    public By getTigerconnectSupportPageHeaderMessage() {
        return tigerconnectSupportPageHeaderMessage;
    }
    public By getUserName() {return userName;}
    public By getLoginUserNameButton() {
        return loginUserNameButton;
    }
    public By getUserPassword() {return userPassword;}
    public By getLoginButton() {
        return loginButton;
    }
    public By getErrorUserName(){ return errorUserName;}
    public By getTryAgainButton(){ return tryAgainButton;}
    public By getForgotPasswordButton(){ return forgotPasswordButton;}
    public By getDownloadNow() { return downloadNow; }
    public By getiPhone() {return iPhone;}
    public By getiPad() { return iPad;}
    public By getAndroid() { return android;}
    public By getTerms() { return terms;}
    public By getPrivacy() { return privacy;}
    public By getBusinessLink() { return businessLink;}
    public By getSupport() { return support;}
    public By getTimeout() { return timeout; }
    public By getMessageBubble() { return messageBubble; }
    public By getCallUsPhoneNumber() {
        return callUsPhoneNumber;
    }
}
