package page_factory.global_kinetic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.web.driver.commons.SeleniumBasePage;

import java.util.List;

public class CareerContactPage extends SeleniumBasePage {
    @FindBy(xpath = "//*[@id=\"panel-gb6196-5fb56a62cf98c-0-0-0\"]/div/div/h1")
    private WebElement getInTouch;

    @FindBy(xpath = "//*[@id=\"wpcf7-f6079-p6196-o1\"]/form/div[2]/div[2]/div[1]/div[1]/p/span[2]/input")
    private WebElement yourName;

    @FindBy(xpath = "//*[@id=\"wpcf7-f6079-p6196-o1\"]/form/div[2]/div[2]/div[1]/div[2]/p/span[2]/input")
    private WebElement yourEmail;

    @FindBy(xpath = "//*[@id=\"wpcf7-f6079-p6196-o1\"]/form/div[2]/div[2]/div[2]/div[1]/p/span[2]/input")
    private WebElement yourTelephoneNumber;

    @FindBy(xpath = "//*[@id=\"wpcf7-f6079-p6196-o1\"]/form/div[2]/div[2]/div[3]/div[1]/span[2]/select")
    private WebElement roleSelect;

    @FindBy(xpath = "//*[@id=\"wpcf7-f6079-p6196-o1\"]/form/div[2]/div[2]/div[3]/div[1]/span[2]/select/option")
    private List<WebElement> roleOptions;

    @FindBy(xpath = "//*[@id=\"wpcf7-f6079-p6196-o1\"]/form/div[2]/div[2]/div[3]/div[2]/p/span[2]/input")
    private WebElement differentSubject;

    @FindBy(xpath = "//*[@id=\"wpcf7-f6079-p6196-o1\"]/form/div[2]/div[2]/div[4]/div/p/span[2]/textarea")
    private WebElement littleMore;

    @FindBy(xpath = "//*[@id=\"wpcf7-f6079-p6196-o1\"]/form/div[2]/div[2]/div[5]/div/p/span[2]/input")
    private WebElement attachCVInput;

    @FindBy(xpath = "//*[@id=\"wpcf7-f6079-p6196-o1\"]/form/div[2]/div[2]/div[6]/div/div/p/input")
    private WebElement send;

    public CareerContactPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void waitForVisibilityOfGetInTouch() {
        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 60);
        webDriverWait.until(ExpectedConditions.visibilityOf(yourName));
    }

    public void inputName(String name) {
        yourName.sendKeys(name);
    }

    public void inputEmail(String email) {
        yourEmail.sendKeys(email);
    }

    public void inputTelephoneNumber(String telephoneNumber) {
        yourTelephoneNumber.sendKeys(telephoneNumber);
    }

    public void selectRole(String role) throws Exception {
        try {
            roleSelect.click();
            Thread.sleep(1000);
            WebElement roleOption = getElementFromListByText(roleOptions, role);
            if (roleOption != null) {
                roleOption.click();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while selection role: " + role);
        }
    }

    public void inputDifferentSubject(String subject) {
        clearAndInputText(differentSubject, subject);
    }

    public void inputLittleMore(String message) {
        littleMore.sendKeys(message);
    }

    public void attachCV(String filePath) {
        attachCVInput.sendKeys(filePath);
    }

    public void clickSend() {
        send.click();
    }
}
