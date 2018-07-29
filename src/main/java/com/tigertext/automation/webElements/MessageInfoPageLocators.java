package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class MessageInfoPageLocators {
    public MessageInfoPageLocators(){
    }
    public static final String messageInfoSenderNamePath = "[data-class='home.conv.view_message_info.sender_name']";
    public static final String messageInfoReceiverNamePath = "[data-class='home.conv.view_message_info.recipient_name']";
    public static final String messageCloseButtonPath = "Close";

    private By messageInfoSenderName = By.cssSelector(messageInfoSenderNamePath);
    private By messageInfoReceiverName = By.cssSelector(messageInfoReceiverNamePath);
    private By messageCloseButton = By.linkText(messageCloseButtonPath);

    public By getMessageInfoSenderName() { return messageInfoSenderName; }
    public By getMessageInfoReceiverName() { return messageInfoReceiverName; }
    public By getMessageCloseButton() {return messageCloseButton;}

}
