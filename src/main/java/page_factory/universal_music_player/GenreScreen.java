package page_factory.universal_music_player;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.web.driver.commons.SeleniumBasePage;

import java.util.List;

public class GenreScreen extends SeleniumBasePage {
    @FindBy(id = "com.example.android.uamp:id/title")
    private WebElement content;

    @FindBy(id = "com.example.android.uamp:id/title")
    private List<WebElement> genres;

    public GenreScreen(AppiumDriver<MobileElement> appiumDriver) {
        super(appiumDriver);
    }

    public void wailForVisibilityOfContent() {
        waitForVisibilityOfElement(content);
    }

    public SongListScreen clickGenre(Genre genre) throws Exception {
        if (genres.size() != 0) {
            clickElementFromListByText(genres, genre.toString());
        }
        return new SongListScreen(getAppiumDriver());
    }

}
