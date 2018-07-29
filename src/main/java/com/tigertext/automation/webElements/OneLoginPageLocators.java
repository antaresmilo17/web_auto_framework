package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class OneLoginPageLocators {
    public OneLoginPageLocators() {
    }

    public static final String oneLoginUserNamePath = "//*[@id='user_email']";
    public static final String oneLoginPasswordPath = "//*[@id='user_password']";
    public static final String oneLoginCommitButtonPath = "//*[@class='commit']";
    public static final String oneLoginLogoPath = "//*[@class='logo-area']";
    public static final String oneLoginLogOutButtonPath = "//*[@class='admin-and-menu']";
    public static final String oneLoginLogOutOptionButtonPath = "//*[@class='link-button main-menu-item']";

    private By oneLoginUserName = By.xpath(oneLoginUserNamePath);
    private By oneLoginLogOutButton = By.xpath(oneLoginLogOutButtonPath);
    private By oneLoginLogOutOptionButton = By.xpath(oneLoginLogOutOptionButtonPath);

    private By oneLoginPassword = By.xpath(oneLoginPasswordPath);
    private By oneLoginCommitButton = By.xpath(oneLoginCommitButtonPath);
    private By oneLoginLogo = By.xpath(oneLoginLogoPath);

    public By getOneLoginPassword() {
        return oneLoginPassword;
    }
    public By getOneLoginUserName() {
        return oneLoginUserName;
    }
    public By getOneLoginCommitButton() {
        return oneLoginCommitButton;
    }
    public By getOneLoginLogo() {
        return oneLoginLogo;
    }
    public By getOneLoginLogOutButton() {
        return oneLoginLogOutButton;
    }
    public By getOneLoginLogOutOptionButton() {
        return oneLoginLogOutOptionButton;
    }
}
