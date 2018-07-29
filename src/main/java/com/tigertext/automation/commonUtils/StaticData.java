package com.tigertext.automation.commonUtils;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class StaticData {
    public static HashMap<String, String> hashMapURL = new HashMap<String, String>()
    {{
        put("TigerText Download", "https://www.tigerconnect.com/products/download/");
        put("iTunes Download", "https://itunes.apple.com");
        put("Google Play Download", "https://play.google.com/");
        put("End User license agreement", "https://www.tigerconnect.com/terms/end-user-terms-use-license-agreement/");
        put("Privacy", "https://www.tigerconnect.com/privacy-policy/");
        put("TigerText Business", "https://www.tigerconnect.com/?_ga=2.57026090.1820762985.1522975703-1464908355.1515717874");
        put("TigerText Business Alt", "https://www.tigerconnect.com");
        put("Support", "https://www.tigerconnect.com/support/");
        put("link","https://www.google.com/");
        put("image","https://home-static.tigertext.com/1432/img/login/logo_tt.png");
        put("oneLogin","https://justtoplay.onelogin.com/login");

    }};
    public static HashMap<String,String> hashMapMessages = new HashMap<>();
    public static HashMap<String,String> hasMapForums = new HashMap<String, String>()
    {{
        put("qaForumName","Auto QA Forum");
    }};

    public static String testOrganizationName = "TestOrganization";
    public static int testOrganizationAmount = 2;

}
