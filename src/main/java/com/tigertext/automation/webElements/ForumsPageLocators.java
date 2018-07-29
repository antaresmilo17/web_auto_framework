package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class ForumsPageLocators {
    public ForumsPageLocators() {
    }

    public static final String forumsTabPath = "[data-class='home.admin.roomsTab']";
    public static final String createNewForumButtonPath = "[data-class='home.admin.rooms.create']";
    public static final String forumResultsPath = "[data-class='home.admin.rooms.results']";

    private By forumsTab = By.cssSelector(forumsTabPath);
    private By createNewForumButton = By.cssSelector(createNewForumButtonPath);
    private By forumResults = By.cssSelector(forumResultsPath);

    public By getForumsTab() { return forumsTab; }
    public By getCreateNewForumButton() { return createNewForumButton; }
    public By getForumResults() { return forumResults; }
}
