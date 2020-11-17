package selenium.web.driver.commons.page.factory;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.web.driver.commons.SeleniumBasePage;

public class GoogleSearchPage extends SeleniumBasePage {
    @FindBy(xpath = "//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input")
    private WebElement searchInput;

    @FindBy(id = "//*[@id=\"tsf\"]/div[2]/div[1]/div[3]/center/input[1]")
    private WebElement search;

    public GoogleSearchPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void inputSearchText(String searchText) {
        inputText(searchInput, searchText);
    }

    public void clickSearch() {
        search.click();
    }

    public void pressEscapeOnSearch() {
        pressKey(searchInput, Keys.ESCAPE);
    }

    public void pressEnterOnSearch() {
        pressKey(searchInput, Keys.ENTER);
    }

}
