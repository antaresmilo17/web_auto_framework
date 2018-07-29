package com.tigertext.automation.webdriver.driver;

import com.tigertext.automation.config.DriverConfig;
import com.tigertext.automation.scenarioSettings.BrowserConfig;
import com.tigertext.automation.enums.BrowserTypes;
import com.typesafe.config.Config;
import cucumber.api.Scenario;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.tigertext.automation.config.TestConfig;
import java.io.File;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.net.Inet4Address;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarLog;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import net.lightbody.bmp.core.har.Har;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import net.lightbody.bmp.client.ClientUtil;

import java.util.logging.Logger;


public class ChromeDriverConfig implements Driver {

    private static final String SELNIUM_DRIVER_NAME = "SELENIUM_CHROME_DRIVER";
    private final Config properties;
    public static BrowserMobProxy server = new BrowserMobProxyServer();
    Proxy proxy;
    Logger logger = Logger.getLogger(ChromeDriverConfig.class.getName());
    Boolean activateProxy = DriverConfig.config().getBoolean("webdriver.activateProxy");

    @Override
    public WebDriver startBrowser(BrowserTypes browserType, Scenario scenario) {
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);

        final DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        //System.setProperty("webdriver.chrome.logfile","/tmp/chromedriver.log");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("-incognito");
        options.addArguments("start-maximized");
        options.addArguments("--ignore-certificate-errors");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);


        if(activateProxy.equals(true))
        {
            // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
            server.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
            server.setTrustAllServers(true);
            server.start(5555);
            proxy = ClientUtil.createSeleniumProxy(server);


            try {
                String hostIp = Inet4Address.getLocalHost().getHostAddress();
                proxy.setHttpProxy(hostIp +":"+server.getPort());
                proxy.setSslProxy(hostIp +":"+server.getPort());
                //proxy.setSslProxy("trustAllSSLCertificates");
            }catch(UnknownHostException e)
            {
                logger.info(e.toString());
            }

            cap.setCapability(CapabilityType.PROXY, proxy);
//            cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS,proxy);
//            cap.setAcceptInsecureCerts(true);
            logger.info("started proxy: " + proxy.getHttpProxy());

        }


        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("start-maximized");


        cap.setCapability(ChromeOptions.CAPABILITY, options);
        cap.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
        WebDriver webDriver = new ChromeDriver(cap);



        // create a new HAR with the label "yahoo.com"
        server.newHar("test");


        return webDriver;
    }

    public ChromeDriverConfig(Config properties) {
        this.properties = properties;
    }

    private void setChromeDriverPath() {
        String path = DriverConfig.config().getString("webdriver.chrome.driver");
        if(StringUtils.isEmpty(path)) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + File.separator + "chromedriver");
        }
        else { System.setProperty("webdriver.chrome.driver",path);};
    }
}
