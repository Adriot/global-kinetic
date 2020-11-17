package selenium.web.driver;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import selenium.web.driver.managers.DriverManager;

public class DriverManagerFactoryTests {
    @Test
    public void chromeDriverManagerTest() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
        WebDriver webDriver = driverManager.getWebDriver(false);
        webDriver.get("https://www.google.com");
        String title = webDriver.getTitle();
        System.out.println("Title: " + title);
        webDriver.quit();
    }

    @Test
    public void chromeDriverManagerHeadlessTest() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
        WebDriver webDriver = driverManager.getWebDriver(true);
        webDriver.get("https://www.google.com");
        String title = webDriver.getTitle();
        System.out.println("Title: " + title);
        webDriver.quit();
    }

    @Test
    public void fireFoxDriverManagerTest() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.FIREFOX);
        WebDriver webDriver = driverManager.getWebDriver(false);
        webDriver.get("https://www.google.com");
        String title = webDriver.getTitle();
        System.out.println("Title: " + title);
        webDriver.quit();
    }

    @Test
    public void fireFoxDriverManagerHeadlessTest() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.FIREFOX);
        WebDriver webDriver = driverManager.getWebDriver(true);
        webDriver.get("https://www.google.com");
        String title = webDriver.getTitle();
        System.out.println("Title: " + title);
        webDriver.quit();
    }


    @Test
    public void phantomJsDriverManagerTest() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.PHANTOMJS);
        WebDriver webDriver = driverManager.getWebDriver(false);
        webDriver.get("https://www.google.com");
        String title = webDriver.getTitle();
        System.out.println("Title: " + title);
        webDriver.quit();
    }


    @Test
    public void edgeDriverManagerTest() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.EDGE);
        WebDriver webDriver = driverManager.getWebDriver(false);
        webDriver.get("https://www.google.com");
        String title = webDriver.getTitle();
        System.out.println("Title: " + title);
        webDriver.quit();
    }


    @Test
    public void internetExplorerDriverManagerTest() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.IEXPLORER);
        WebDriver webDriver = driverManager.getWebDriver(false);
        webDriver.get("https://www.google.com");
        String title = webDriver.getTitle();
        System.out.println("Title: " + title);
        webDriver.quit();
    }

}
