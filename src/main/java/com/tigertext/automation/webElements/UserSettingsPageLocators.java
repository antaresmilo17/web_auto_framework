package com.tigertext.automation.webElements;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

@Component
public class UserSettingsPageLocators {
    public UserSettingsPageLocators(){
    }

    public static final String removeUserTabPath = "//*[@data-class='home.admin.user.btn_delete_user']";
    public static final String removeUserPath = "//*[@data-class='home.admin.user.delete_user']";
    public static final String customerSupportTabPath = "//*[@data-class='home.admin.consTab']";
    public static final String customerSupportTabUserSearchTextfieldPath = "//*[@data-class='home.admin.cons_admin.search_input']";
    public static final String customerSupportTabUserSearchButtonPath = "//*[@data-class='home.admin.cons_admin.search_submit']";
    public static final String customerSupportTabDeleteUserSearchResultsValuesPath = "//*[@data-class='home.admin.cons_admin.search_result.value']";
    public static final String customerSupportTabDeleteUserTabPath = "//*[@data-class='home.admin.cons_admin.btn_delete_user']";
    public static final String customerSupportTabDeleteUserButtonPath = "//*[@data-class='home.admin.cons_admin.search_result.delete_user']";
    public static final String customerSupportTabHomeUserNamePath = "//*[@data-class='home.admin.cons_admin.name']";
    public static final String customerTokenPath = "//*[@data-class='home.admin.cons_admin.search_result.key' and .='token']/following-sibling::span";
    public static final String customerSupportAdminDevicesOptionPath = "//*[@data-class='home.admin.cons_admin.btn_resources']";
    public static final String customerSupportAdminDeleteOptionPath = "//*[@data-class='home.admin.cons_admin.btn_delete_user']";
    public static final String customerSupportAdminForceLogOutOptionPath = "//*[@data-class='home.admin.cons_admin.btn_force_logout']";
    public static final String customerSupportAdminSetPasswordOptionPath = "//*[@data-class='home.admin.cons_admin.btn_password']";
    public static final String customerSupportAdminClearCNbuttonPath = "//*[@data-class='home.admin.cons_admin.clear_cn_resource']";
    public static final String customerSupportAdminNotifyMessagePopUpPath = "//div[@class='noty_bar noty_type_success']/descendant::span[.='Web resource succesfully cleared.']";
    public static final String organizationTabPath = "//*[@data-class='home.admin.orgsTab']";
    public static final String orgCreateNewOrgButtonPath = "//*[@data-class='home.admin.orgs.new_org']";
    public static final String orgOrgNameTextFieldPath = "//*[@data-class='home.admin.org_create.org_name']";
    public static final String orgCreateButtonPath = "//*[@data-class='home.admin.org_create.create']";
    public static final String orgOrgNameHeaderTextFieldPath = "//*[@data-class='home.admin.org_settings.org_name']";
    public static final String orgOrgSearchTextFieldPath = "//*[@data-class='home.admin.orgs.search_text']";
    public static final String orgOrgNameSearchResultsPath = "//*[@data-class='home.admin.orgs.org_name']";
    public static final String orgOrgIDSearchResultsPath = "//*[@data-class='home.admin.orgs.org_id']";

    private By removeUserTab = By.xpath(removeUserTabPath);
    private By removeUser = By.xpath(removeUserPath);
    private By customerSupportTab = By.xpath(customerSupportTabPath);
    private By customerSupportTabUserSearchTextfield = By.xpath(customerSupportTabUserSearchTextfieldPath);
    private By customerSupportTabUserSearchButton = By.xpath(customerSupportTabUserSearchButtonPath);
    private By customerSupportTabDeleteUserSearchResultsValues = By.xpath(customerSupportTabDeleteUserSearchResultsValuesPath);
    private By customerSupportTabDeleteUserTab = By.xpath(customerSupportTabDeleteUserTabPath);
    private By customerSupportTabDeleteUserButton = By.xpath(customerSupportTabDeleteUserButtonPath);
    private By customerSupportTabHomeUserName = By.xpath(customerSupportTabHomeUserNamePath);
    private By customerSupportAdminDevicesOption = By.xpath(customerSupportAdminDevicesOptionPath);
    private By customerSupportAdminDeleteOption = By.xpath(customerSupportAdminDeleteOptionPath);
    private By customerSupportAdminForceLogOutOption = By.xpath(customerSupportAdminForceLogOutOptionPath);
    private By customerSupportAdminSetPasswordOption = By.xpath(customerSupportAdminSetPasswordOptionPath);
    private By customerSupportAdminClearCNbutton = By.xpath(customerSupportAdminClearCNbuttonPath);
    private By customerSupportAdminNotifyMessagePopUp = By.xpath(customerSupportAdminNotifyMessagePopUpPath);
    private By customerToken = By.xpath(customerTokenPath);
    private By organizationTab = By.xpath(organizationTabPath);
    private By orgCreateNewOrgButton = By.xpath(orgCreateNewOrgButtonPath);
    private By orgOrgNameTextField = By.xpath(orgOrgNameTextFieldPath);
    private By orgCreateButton = By.xpath(orgCreateButtonPath);
    private By orgOrgNameHeaderTextField = By.xpath(orgOrgNameHeaderTextFieldPath);
    private By orgOrgSearchTextField = By.xpath(orgOrgSearchTextFieldPath);
    private By orgOrgNameSearchResults = By.xpath(orgOrgNameSearchResultsPath);
    private By orgOrgIDSearchResults = By.xpath(orgOrgIDSearchResultsPath);

    public By getOrgOrgIDSearchResults() { return orgOrgIDSearchResults; }
    public By getOrgOrgNameSearchResults() { return orgOrgNameSearchResults; }
    public By getOrgOrgSearchTextField() { return orgOrgSearchTextField; }
    public By getOrgOrgNameHeaderTextField() { return orgOrgNameHeaderTextField; }
    public By getOrgCreateButton() { return orgCreateButton; }
    public By getOrgOrgNameTextField() { return orgOrgNameTextField; }
    public By getOrgCreateNewOrgButton() { return orgCreateNewOrgButton; }
    public By getOrganizationTab() {return organizationTab;}
    public By getRemoveUserTab() {return removeUserTab;}
    public By getRemoveUser() {return removeUser;}
    public By getCustomerSupportTab() {return customerSupportTab;}
    public By getCustomerSupportTabUserSearchTextfield() {return customerSupportTabUserSearchTextfield;}
    public By getCustomerSupportTabUserSearchButton() {return customerSupportTabUserSearchButton;}
    public By getCustomerSupportTabDeleteUserSearchResultsValues() {return customerSupportTabDeleteUserSearchResultsValues;}
    public By getCustomerSupportTabDeleteUserTab() {return customerSupportTabDeleteUserTab;}
    public By getCustomerSupportTabDeleteUserButton() {return customerSupportTabDeleteUserButton;}
    public By getCustomerToken() {return customerToken;}
    public By getCustomerSupportTabHomeUserName() {
        return customerSupportTabHomeUserName;
    }
    public By getCustomerSupportAdminDevicesOption() {
        return customerSupportAdminDevicesOption;
    }
    public By getCustomerSupportAdminDeleteOption() {
        return customerSupportAdminDeleteOption;
    }
    public By getCustomerSupportAdminForceLogOutOption() {
        return customerSupportAdminForceLogOutOption;
    }
    public By getCustomerSupportAdminSetPasswordOption() {
        return customerSupportAdminSetPasswordOption;
    }
    public By getCustomerSupportAdminClearCNbutton() {
        return customerSupportAdminClearCNbutton;
    }
    public By getCustomerSupportAdminNotifyMessagePopUp() {
        return customerSupportAdminNotifyMessagePopUp;
    }
}
