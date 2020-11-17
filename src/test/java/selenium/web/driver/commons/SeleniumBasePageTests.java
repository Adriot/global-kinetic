package selenium.web.driver.commons;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import selenium.web.driver.DriverManagerFactory;
import selenium.web.driver.DriverType;
import selenium.web.driver.commons.page.factory.GoogleSearchPage;
import selenium.web.driver.managers.DriverManager;

import static files.Config.reportSourcePath;

public class SeleniumBasePageTests {
    @Test
    public void chromeDriverManagerTest() {
        try {
            DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
            WebDriver webDriver = driverManager.getWebDriver(false);
            webDriver.get("https://www.google.com");
            String title = webDriver.getTitle();
            System.out.println("Title: " + title);
            Assert.assertEquals("Google", title);

            GoogleSearchPage googleSearchPage = new GoogleSearchPage(webDriver);
            googleSearchPage.takeScreenshot(reportSourcePath + "/screenshots/home page " + googleSearchPage.getDateTimeStamp());
            googleSearchPage.inputSearchText("Selenium Automation");
            Thread.sleep(5000);
            googleSearchPage.pressEscapeOnSearch();
            Thread.sleep(5000);
            googleSearchPage.pressEnterOnSearch();
            Thread.sleep(5000);
            googleSearchPage.takeScreenshot(reportSourcePath + "/screenshots/search results " + googleSearchPage.getDateTimeStamp());
            webDriver.quit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void chromeDriverManagerTestHeadless() {
        try {
            DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
            WebDriver webDriver = driverManager.getWebDriver(true);
            webDriver.get("https://www.google.com");
            String title = webDriver.getTitle();
            System.out.println("Title: " + title);
            Assert.assertEquals("Google", title);

            GoogleSearchPage googleSearchPage = new GoogleSearchPage(webDriver);
            googleSearchPage.takeScreenshot(reportSourcePath + "/screenshots/home page " + googleSearchPage.getDateTimeStamp());
            googleSearchPage.inputSearchText("Selenium Automation");
            Thread.sleep(5000);
            googleSearchPage.pressEscapeOnSearch();
            Thread.sleep(5000);
            googleSearchPage.pressEnterOnSearch();
            Thread.sleep(5000);
            googleSearchPage.takeScreenshot(reportSourcePath + "/screenshots/search results " + googleSearchPage.getDateTimeStamp());
            webDriver.quit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
