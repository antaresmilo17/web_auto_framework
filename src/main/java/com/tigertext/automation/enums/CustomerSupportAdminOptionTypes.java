package com.tigertext.automation.enums;

public enum CustomerSupportAdminOptionTypes {

    DEVICES("Devices"),
    DELETEUSER("Delete User"),
    FORCELOGOUT("Force Logout"),
    SETPASSWORD("Set Password");

    private String customerSupportAdminOption;

    CustomerSupportAdminOptionTypes(String browserName) {
        this.customerSupportAdminOption = browserName;
    }

    public String getBrowserName() {
        return customerSupportAdminOption;
    }

    @Override
    public String toString() { return this.customerSupportAdminOption;}
}
