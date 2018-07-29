package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class PasswordSetupLocators {
    public PasswordSetupLocators(){
    }

    private static final String userNamePath = "input[type='email']";

    private By userName = By.cssSelector(userNamePath);

    public By getUserName(){
        return userName;
    }
}
