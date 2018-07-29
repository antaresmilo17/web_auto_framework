package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class ExploreForumPageLocators {
    public ExploreForumPageLocators() {
    }

    public static final String exploreForumListPath = "[data-class='home.conv.rooms.results']";
    public static final String closeExploreForumButtonPath = "(//h3[contains(text(),'Forums')])/../button";

    private By exploreForumList = By.cssSelector(exploreForumListPath);
    private By closeExploreForumButton = By.xpath(closeExploreForumButtonPath);

    public By getExploreForumList() { return exploreForumList; }
    public By getCloseExploreForumButton() { return closeExploreForumButton; }

}
