package page_factory.global_kinetic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.web.driver.commons.SeleniumBasePage;

public class CareersPage extends SeleniumBasePage {
    @FindBy(xpath = "//*[@id=\"panel-5498-0-1-0\"]/div/div/h1")
    private WebElement openPositions;

    @FindBy(xpath = "//*[@id=\"panel-5498-19-1-0\"]/div/div/a")
    private WebElement getInTouch;

    public CareersPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void waitForVisibilityOfOpenPositions() {
        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 60);
        webDriverWait.until(ExpectedConditions.visibilityOf(openPositions));
    }

    public CareerContactPage clickGetInTouch() {
        moveToWebElementAndClick(getInTouch);
        return new CareerContactPage(getDriver());
    }
}
