package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class MutePageLocators {
    public MutePageLocators(){
    }

    public static final String confirmMuteButtonPath = "[data-class='home.conv.mute_conversation']";
    public static final String selectMutePath = "[data-class='home.conv.mute_duration']";

    private final By confirmMuteButton = By.cssSelector(confirmMuteButtonPath);
    private final By selectMute = By.cssSelector(selectMutePath);

    public By getMuteButton() {return confirmMuteButton;}
    public By getSelectMute() {return selectMute;}
}
