package appium;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.testng.annotations.Test;
import selenium.web.driver.DriverManagerFactory;
import selenium.web.driver.DriverType;
import selenium.web.driver.managers.DriverManager;

public class AppiumDriverTests {
    @Test
    public void appiumDriverStartAppTest() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.APPIUM);
        AppiumDriver driver = driverManager.getAppiumDriver();
        Assert.assertNotNull(driver);
        driverManager.quitAppiumDriver();
        System.out.println("Application Started Successfully.");
    }
}
