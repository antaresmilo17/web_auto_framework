package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class RosterPageLocators {
    public RosterPageLocators(){
    }

    public static final String rosterPagePath = "//*[@data-class='home.conv.single.roster']";
    public static final String rosterListPath = "//li[@data-class='home.conv.single.roster.contact']";
    public static final String rosterRemoveFirstItemPath = "(.//*[@data-class=\"home.conv.deleteFromRoster\"])[1]";
    public static final String deleteFromRosterPath = "//*[@id='confirmLeaveGroupFromRoster']//a[@data-class='home.conv.deleteFromRoster_btn']";

    private final By rosterPage = By.xpath(rosterPagePath);
    private final By rosterList = By.xpath(rosterListPath);
    private final By rosterRemoveFirstItem = By.xpath(rosterRemoveFirstItemPath);
    private final By deleteFromRoster = By.xpath(deleteFromRosterPath);

    public By getRosterPage() {return rosterPage;}
    public By getRosterList() {return rosterList;}
    public By getDeleteFromRoster() {
        return deleteFromRoster;
    }
    public By getRosterRemoveFirstItem() {
        return rosterRemoveFirstItem;
    }
}
