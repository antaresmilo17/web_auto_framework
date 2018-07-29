package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class CreateForumPageLocators {
    public CreateForumPageLocators(){
    }
     public static final String forumNamePath = "[data-class='home.admin.public_group.edit_group_name']";
     public static final String forumUserSearchBoxPath = "[data-class='home.admin.public_group.search_text']";
     public static final String forumUserDropDownValuePath = ".//*[@class='off blue']//div[@class='name']";
     public static final String createForumButtonPath = "[data-class='home.admin.public_group.create_group_btn']";
     public static final String updateForumButtonPath = "[data-class='home.admin.public_group.update_group_btn']";
     public static final String listForumMembersPath = ".groupResultsCntr";
     public static final String deleteForumButtonPath = "[data-class='home.admin.rooms.room.delete']";
     public static final String deleteForumUsersButtonPath = "[data-class='home.admin.public_group.group_member_remove']";

     private By forumName = By.cssSelector(forumNamePath);
     private By forumUserSearchBox = By.cssSelector(forumUserSearchBoxPath);
     private By forumUserDropDownValue = By.xpath(forumUserDropDownValuePath);
     private By createForumButton = By.cssSelector(createForumButtonPath);
     private By updateForumButton = By.cssSelector(updateForumButtonPath);
     private By listForumMembers = By.cssSelector(listForumMembersPath);
     private By deleteForumButton = By.cssSelector(deleteForumButtonPath);
     private By deleteForumUsersButton = By.cssSelector(deleteForumUsersButtonPath);

     public By getDeleteForumUsersButton() {return deleteForumUsersButton;}
     public By getForumName() {return forumName;}
     public By getForumUserSearchBox() {return forumUserSearchBox;}
     public By getForumUserDropDownValue() {return forumUserDropDownValue;}
     public By getCreateForumButton() {return createForumButton;}
     public By getUpdateForumButton() {return updateForumButton;}
     public By getListForumMembers() {return listForumMembers;}
     public By getDeleteForumButton() {return deleteForumButton;}
 }