package page_factory.universal_music_player;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.web.driver.commons.SeleniumBasePage;

import java.util.List;

public class SongListScreen extends SeleniumBasePage {
    @FindBy(id = "com.example.android.uamp:id/title")
    private WebElement content;

    @FindBy(id = "com.example.android.uamp:id/title")
    private List<WebElement> songs;

    @FindBy(id = "com.example.android.uamp:id/play_eq")
    private List<WebElement> playEqs;

    @FindBy(id = "com.example.android.uamp:id/play_pause")
    private WebElement playPause;

    @FindBy(id = "com.example.android.uamp:id/album_art")
    private WebElement albumArt;

    private int numberOfSongs;

    public SongListScreen(AppiumDriver<MobileElement> appiumDriver) {
        super(appiumDriver);
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public void wailForVisibilityOfContent() {
        waitForVisibilityOfElement(content);
    }

    public void clickSong(String title) throws Exception {
        numberOfSongs = songs.size();
        if (songs.size() != 0) {
            clickElementFromListByText(songs, title);
        }
    }

    public void clickPlayPause() {
        playPause.click();
    }

    public PlayScreen clickAlbumArt() {
        albumArt.click();
        return new PlayScreen(getAppiumDriver());
    }

}
