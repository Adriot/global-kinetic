package selenium.web.driver.managers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class DriverManager {
    private Logger log  = LogManager.getLogger(DriverManager.class);
    protected WebDriver driver;
    protected AppiumDriver appiumDriver;
    protected AndroidDriver<AndroidElement> androidDriver;
    protected DesiredCapabilities desiredCapabilities;

    public DriverManager() {
    }

    protected abstract void createDriver();
    protected abstract void createDriver(boolean headless);

    public WebDriver getWebDriver(boolean headless) {
        if (driver == null) {
            createDriver(headless);
        }
        return driver;
    }

    public void quitWebDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public AppiumDriver getAppiumDriver() {
        if (appiumDriver == null) {
            createDriver();
        }
        return appiumDriver;
    }

    public void quitAppiumDriver() {
        if (appiumDriver != null) {
            appiumDriver.quit();
            appiumDriver = null;
        }
    }

    public AndroidDriver getAndroidDriver() {
        if (androidDriver == null) {
            createDriver();
        }
        return androidDriver;
    }

    public void quitAndroidDriver() {
        if (androidDriver != null) {
            androidDriver.quit();
            androidDriver = null;
        }
    }
}
