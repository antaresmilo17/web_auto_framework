package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class UsersPageLocators {
    public UsersPageLocators(){
    }

    public static final String usersTabPath = ".icon-users";
    public static final String addUserPath = ".//*[@data-class='home.admin.users.user_add']";
    public static final String firstNamePath = ".//*[@data-class='home.admin.new_user.first_name']";
    public static final String lastNamePath = ".//*[@data-class='home.admin.new_user.last_name']";
    public static final String displayNamePath = ".//*[@data-class='home.admin.new_user.display_name']";
    public static final String emailPath = ".//*[@data-class='home.admin.new_user.email']";
    public static final String passwordPath = ".//*[@data-class='home.admin.new_user.password']";
    public static final String createUserPath = ".//*[@data-class='home.admin.new_user.create_user_btn']";
    public static final String listUsersPath = "//*[@data-class='home.admin.users.users']";
    public static final String cancelPath = "//*[@data-class='home.admin.new_user.cancel']";
    public static final String userSelectAllCheckboxPath = "//*[@data-class='home.admin.users.batch.selectAll']";
    public static final String selectOrgDropDownPath = "[data-class='home.admin.contexts']";
    public static final String importUsersPath = "[data-class='home.admin.users_batch.addUsersBatch']";
    public static final String chooseFilePath = "//input[@name='csvFile']";
    public static final String submitButtonPath = "//button[@type='submit']";
    public static final String successfulUsersImportedCountPath = "[data-class='home.admin.users_batch.users_created']";
    public static final String usersCountPath = ".//*[@data-class='home.admin.users.userCount']";

//    private By usersTab = By.cssSelector(usersTabPath);
    private By usersTab = By.cssSelector(usersTabPath);
    private By addUser = By.xpath(addUserPath);
    private By firstName = By.xpath(firstNamePath);
    private By lastName = By.xpath(lastNamePath);
    private By displayName = By.xpath(displayNamePath);
    private By email = By.xpath(emailPath);
    private By password = By.xpath(passwordPath);
    private By createUser = By.xpath(createUserPath);
    private By listUsers = By.xpath(listUsersPath);
    private By cancel = By.xpath(cancelPath);
    private By userSelectAllCheckbox = By.xpath(userSelectAllCheckboxPath);
    private By selectOrgDropDown = By.cssSelector(selectOrgDropDownPath);
    private By importUsers = By.cssSelector(importUsersPath);
    private By chooseFile = By.xpath(chooseFilePath);
    private By submitButton = By.xpath(submitButtonPath);
    private By successfulUsersImportedCount = By.cssSelector(successfulUsersImportedCountPath);
    private By usersCount = By.xpath(usersCountPath);

    public By getUsersCount() { return usersCount; }
    public By getSuccessfulUsersImportedCount() { return successfulUsersImportedCount; }
    public By getSubmitButton() { return submitButton; }
    public By getChooseFile() { return chooseFile; }
    public By getImportUsers() { return importUsers; }
    public By getUsersTab() {return usersTab;}
    public By getAddUser() {return addUser;}
    public By getFirstName() {return firstName;}
    public By getLastName() {return lastName;}
    public By getDisplayName() {return displayName;}
    public By getEmail() {return email;}
    public By getPassword() {return password;}
    public By getCreateUser() {return createUser;}
    public By getListUsers() {return listUsers;}
    public By getCancel() {return cancel;}
    public By getUserSelectAllCheckbox() {return userSelectAllCheckbox;}
    public By getSelectOrgDropDown() {return selectOrgDropDown;}
}
