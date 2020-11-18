package page_factory.global_kinetic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.web.driver.commons.SeleniumBasePage;

public class HomePage extends SeleniumBasePage {
    @FindBy(linkText = "Careers")
    private WebElement careers;

    @FindBy(xpath = "//*[@id=\"PopupSignupForm_0\"]/div[2]/div[1]")
    private WebElement newsLetterPopupClose;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void waitForVisibilityOfNewsLetterPopup() throws Exception {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 120);
            webDriverWait.until(ExpectedConditions.visibilityOf(newsLetterPopupClose));
            closeNewsLetterPopup();
        } catch (Exception e) {
            // throw new Exception("Error while waitng for news letter popup");
        }
    }

    public void waitForVisibilityOfCareers() {
        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 60);
        webDriverWait.until(ExpectedConditions.visibilityOf(careers));
    }

    public CareersPage clickOnCareers() {
        try {
            closeNewsLetterPopup();
        } catch (Exception e) {
            // e.printStackTrace();
        }

        moveToWebElementAndClick(careers);
        return new CareersPage(getDriver());
    }

    public void closeNewsLetterPopup() {
        moveToWebElementAndClick(newsLetterPopupClose);
    }
}
