package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class PrintPreviewPageLocators {
    public PrintPreviewPageLocators(){
    }

    public static final String previewTextPath = "(//*[@data-class='home.conv.message.body'])[last()]";
    public static final String returnToNormalViewPath = "[class='normalView']";

    private By previewText = By.xpath(previewTextPath);
    private By returnToNormalView = By.cssSelector(returnToNormalViewPath);

    public By getPreviewText() { return previewText;}
    public By getReturnToNormalView() { return returnToNormalView;}
}
