package selenium.web.driver.commons;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestListener;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This is a Selenium Base Class That Will Serve As A Base For Pages On The Project Using The Core.
 * It Uses The Page Factory And Houses Basic Common Functionality For Actions To Abstract From Selenium.
 */
public class SeleniumBasePage implements ITestListener {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private static final int TIMEOUT = 60;

    public SeleniumBasePage(WebDriver webDriver) {
        driver = webDriver;
        wait = new WebDriverWait(webDriver, TIMEOUT);
        actions = new Actions(webDriver);
        PageFactory.initElements(webDriver,this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public Actions getActions() {
        return actions;
    }

    protected void waitForVisibilityOfElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForVisibilityOfElement(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    protected void waitForInvisibilityOfElement(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForInvisibilityOfElement(WebElement webElement) {
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    protected void waitForTextToAppear(By locator, String text) {
        wait.until(ExpectedConditions.textToBe(locator, text));
    }

    protected void waitForTextToAppear(WebElement webElement, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(webElement, text));
    }

    protected void waitForTextToDisappear(WebElement webElement, String text) {
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(webElement, text)));
    }

    protected void moveToWebElement(WebElement webElement) {
        actions.moveToElement(webElement).build().perform();
    }

    protected void moveToWebElementAndClick(WebElement webElement) {
        actions.moveToElement(webElement).click().build().perform();
    }

    protected void moveToWebElementAndDoubleClick(WebElement webElement) {
        actions.moveToElement(webElement).doubleClick().build().perform();
    }

    protected String getDateTimeStamp(String formatPattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(formatPattern);
        LocalDateTime now = LocalDateTime.now();
        return format.format(now);
    }

    protected void maximizeWindow() {
        driver.manage().window().maximize();
    }

    protected void switchToWindow(int windowIndex) {
        Object currentWindow = driver.getWindowHandles().toArray()[windowIndex];
        driver.switchTo().window((String) currentWindow);
    }

    public void switchToWindowAndMaximize(int windowIndex) {
        Object currentWindow = driver.getWindowHandles().toArray()[windowIndex];
        driver.switchTo().window((String) currentWindow);
        driver.manage().window().maximize();
    }

    public void switchToWindowAndClose(int windowIndex) {
        Object currentWindow = driver.getWindowHandles().toArray()[windowIndex];
        WebDriver window = driver.switchTo().window((String) currentWindow);
        window.close();
    }

    protected void inputText(WebElement webElement, String inputText) {
        webElement.sendKeys(inputText);
    }

    protected void clearAndInputText(WebElement webElement, String inputText) {
        webElement.clear();
        inputText(webElement, inputText);
    }

    protected WebElement getItemFromList(WebElement itemsList, String itemsXpathPrefix, String selection, String itemsXpathSurfix) throws Exception {
        try {
            WebElement matchingItem = null;
            if (!selection.isEmpty()) {
                String xpath = itemsXpathPrefix + selection + itemsXpathSurfix;
                List<WebElement> elements = itemsList.findElements(By.xpath(xpath));
                int size = elements.size();
                System.out.println("Number Of Matching Elements is: " + size);
                if (size > 0) {
                    matchingItem = elements.get(0);
                }
            }
            return matchingItem;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    protected void clickItemOnList(WebElement itemsList, String itemsXpathPrefix, String selection, String itemsXpathSurfix) throws Exception {
        try {
            if (!selection.isEmpty()) {
                String xpath = itemsXpathPrefix + selection + itemsXpathSurfix;
                List<WebElement> elements = itemsList.findElements(By.xpath(xpath));
                int size = elements.size();
                System.out.println("Number Of Matching Elements is: " + size);
                if (size > 0) {
                    WebElement element = elements.get(0);
                    element.click();
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    protected void clickAndSelect(WebElement dropdown, WebElement dropdownItems, String itemsXpathPrefix, String selection, String itemsXpathSurfix) throws Exception {
        try {
            if (!selection.isEmpty()) {
                String xpath = itemsXpathPrefix + selection + itemsXpathSurfix;
                dropdown.click();
                List<WebElement> elements = dropdownItems.findElements(By.xpath(xpath));
                int size = elements.size();
                System.out.println("Number Of Matching Elements is: " + size);
                if (size > 0) {
                    WebElement element = elements.get(0);
                    element.click();
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    protected int getNumberOfMatchingItems(String xpath) throws Exception {
        try {
            List<WebElement> elements = driver.findElements(By.xpath(xpath));
            return elements.size();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    protected int getNumberOfMatchingItems(WebElement itemList, String xpath) throws Exception {
        try {
            List<WebElement> elements = itemList.findElements(By.xpath(xpath));
            return elements.size();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    protected WebElement getElementFromListByText(List<WebElement> elementList, String locatorMatchText) throws Exception {
        try {
            for (WebElement webElement : elementList) {
                String text = webElement.getText();
                if (text.equalsIgnoreCase(locatorMatchText)) {
                    return webElement;
                }
            }
            return null;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    protected void clickElementFromListByText(List<WebElement> elementList, String locatorMatchText) throws Exception {
        try {
            for (WebElement webElement : elementList) {
                String text = webElement.getText();
                if (text.equalsIgnoreCase(locatorMatchText)) {
                    webElement.click();
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    protected void pressKey(WebElement webElement, Keys keys) {
        webElement.sendKeys(keys);
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] takeScreenshot(String fileNamePath) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) this.driver;
            File screenshotAsFile = screenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotAsFile, new File(fileNamePath + ".png"));
            return screenshot.getScreenshotAs(OutputType.BYTES);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Attachment(value = "{}", type = "text/plain")
    public String allureTextLog(String message) {
        return message;
    }

    public String getDateTimeStamp() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        return format.format(now);
    }

}
