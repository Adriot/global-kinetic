package selenium.web.driver.managers;

import files.FilePropertiesConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Properties;

import static files.Config.propertiesFile;

public class AppiumDriverManager extends DriverManager {
    private Logger log  = LogManager.getLogger(AppiumDriverManager.class);
    @Override
    protected void createDriver() {
        String deviceName = null, udid = null, platformName = null, platformVersion = null, appPackage = null, appActivity = null, appiumServerUrl = null;

        FilePropertiesConfig filePropertiesConfig = new FilePropertiesConfig(propertiesFile);
        filePropertiesConfig.loadProperties();
        Properties testConfigProperties = filePropertiesConfig.getProperties();
        if (testConfigProperties != null) {
            deviceName = testConfigProperties.getProperty("DEVICE_NAME");
            udid = testConfigProperties.getProperty("UD_ID");
            platformName = testConfigProperties.getProperty("PLATFORM_NAME");
            platformVersion = testConfigProperties.getProperty("PLATFORM_VERSION");
            appPackage = testConfigProperties.getProperty("APP_PACKAGE");
            appActivity = testConfigProperties.getProperty("APP_ACTIVITY");
            appiumServerUrl = testConfigProperties.getProperty("APPIUM_SERVER_URL");
        }

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("deviceName" , deviceName);
        desiredCapabilities.setCapability("udid" , udid);
        desiredCapabilities.setCapability("platformName" , platformName);
        desiredCapabilities.setCapability("platformVersion" , platformVersion);
        desiredCapabilities.setCapability("appPackage" , appPackage);
        desiredCapabilities.setCapability("appActivity" , appActivity);

        try {
            URL url = new URL(appiumServerUrl);
            appiumDriver = new AppiumDriver<MobileElement>(url, desiredCapabilities);
        } catch (Exception e) {
            log.error(e.getCause());
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void createDriver(boolean headless) {
        createDriver();
    }
}
