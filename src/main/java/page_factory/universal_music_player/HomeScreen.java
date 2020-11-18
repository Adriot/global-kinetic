package page_factory.universal_music_player;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.web.driver.commons.SeleniumBasePage;

public class HomeScreen extends SeleniumBasePage {
    @FindBy(id = "com.example.android.uamp:id/toolbar")
    private WebElement toolbar;

    @FindBy(className = "android.widget.ImageButton")
    private WebElement humbugerMenu;

    @FindBy(className = "android.widget.TextView")
    private WebElement appTitle;

    @FindBy(id = "com.example.android.uamp:id/title")
    private WebElement genres;

    @FindBy(id = "com.example.android.uamp:id/description")
    private WebElement description;

    public HomeScreen(AppiumDriver<MobileElement> appiumDriver) {
        super(appiumDriver);
    }

    public void wailForVisibilityOfToolBar() {
        waitForVisibilityOfElement(toolbar);
    }

    public String getAppTitleText() {
        return appTitle.getText();
    }

    public void clickHumbugerMenu() {
        humbugerMenu.click();
    }

    public String getGenresText() {
        return genres.getText();
    }

    public GenreScreen clickGenres() {
        genres.click();
        return new GenreScreen(getAppiumDriver());
    }

    public String getDescriptionText() {
        return description.getText();
    }

    public void clickDescription() {
        description.click();
    }
}
