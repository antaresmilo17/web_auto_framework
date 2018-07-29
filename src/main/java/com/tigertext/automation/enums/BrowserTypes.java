package com.tigertext.automation.enums;

public enum BrowserTypes {

    CHROME("chrome"),
    FIREFOX("firefox"),
    IE("ie"),
    SAFARI("safari"),
    REMOTE("remote");

    private String browserName;

    BrowserTypes(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }

    @Override
    public String toString() { return this.browserName;}
}
