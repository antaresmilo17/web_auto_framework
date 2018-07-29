package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class CreateGroupPageLocators {
    public CreateGroupPageLocators(){
    }
    public static final String groupNamePath = "#groupName";
    public static final String groupNameHomePagePath = ".nameStatus h1";
    public static final String groupNameLabelPath = "[data-class='home.group.group_name']";
    public static final String groupMemberPath = "#groupMembers";
    public static final String groupMemberDropDownValuePath = ".//*[@class='off blue']//div[@class='name']";
    public static final String createGroupButtonPath = "[data-class='home.group.create_group_btn']";
    public static final String updateGroupButtonPath = "[data-class='home.group.update_group_btn']";
    public static final String listGroupMembersPath = "[data-class='home.group.group_members']";
    public static final String leaveGroupPath = "[data-class='home.group.confirm_leave_group_btn']";
    public static final String cancelButtonPath = "//*[@data-class='home.group.cancel']";
    public static final String confirmLeaveGroupPath = "//*[@data-class='home.group.leave_group_btn']";
    public static final String cancelLeaveGroupPath = "#confirmLeaveGroup > div.modal-footer > a[class='btn']";
    public static final String removeAllGroupMembersPath = ".remove";

    private By groupName = By.cssSelector(groupNamePath);
    private By groupNameHomePage = By.cssSelector(groupNameHomePagePath);
    private By groupNameLabel = By.cssSelector(groupNameLabelPath);
    private By groupMember = By.cssSelector(groupMemberPath);
    private By groupMemberDropDownValue = By.xpath(groupMemberDropDownValuePath);
    private By createGroupButton = By.cssSelector(createGroupButtonPath);
    private By updateGroupButton = By.cssSelector(updateGroupButtonPath);
    private By listGroupMembers = By.cssSelector(listGroupMembersPath);
    private By leaveGroup = By.cssSelector(leaveGroupPath);
    private By cancelButton =  By.xpath(cancelButtonPath);
    private By confirmLeaveGroup = By.xpath(confirmLeaveGroupPath);
    private By cancelLeaveGroup = By.cssSelector(cancelLeaveGroupPath);
    private By removeAllGroupMembers = By.cssSelector(removeAllGroupMembersPath);

    public By getGroupName() {  return groupName;   }
    public By getGroupNameHomePage() {  return groupNameHomePage;   }
    public By getGroupMember() { return groupMember;}
    public By getGroupMemberDropDownValue() {return groupMemberDropDownValue;}
    public By getCreateGroupButton() {return createGroupButton;}
    public By getUpdateGroupButton() {return updateGroupButton;}
    public By getListGroupMembers() {return listGroupMembers;}
    public By getGroupNameLabel() {return groupNameLabel;}
    public By getLeaveGroup() {return leaveGroup;}
    public By getConfirmLeaveGroup() {return confirmLeaveGroup;}
    public By getCancelLeaveGroup() {return cancelLeaveGroup;}
    public By getRemoveAllGroupMembers() {return removeAllGroupMembers;}
    public By getCancelButton() {return cancelButton;}
}
