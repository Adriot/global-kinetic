package selenium.web.driver.managers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class DriverManager {
    private Logger log  = LogManager.getLogger(DriverManager.class);
    protected WebDriver driver;
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
}
