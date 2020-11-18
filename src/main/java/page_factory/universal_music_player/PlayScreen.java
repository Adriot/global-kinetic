package page_factory.universal_music_player;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.web.driver.commons.SeleniumBasePage;

public class PlayScreen extends SeleniumBasePage {
    @FindBy(id = "com.example.android.uamp:id/background_image")
    private WebElement content;

    @FindBy(id = "com.example.android.uamp:id/prev")
    private WebElement prev;

    @FindBy(id = "com.example.android.uamp:id/play_pause")
    private WebElement playPause;

    @FindBy(id = "com.example.android.uamp:id/next")
    private WebElement next;

    @FindBy(id = "com.example.android.uamp:id/line1")
    private WebElement songTitle;

    @FindBy(id = "com.example.android.uamp:id/line2")
    private WebElement description;

    public PlayScreen(AppiumDriver<MobileElement> appiumDriver) {
        super(appiumDriver);
    }

    public void wailForVisibilityOfContent() {
        waitForVisibilityOfElement(content);
    }

    public void clickPrev() throws InterruptedException {
        prev.click();
        Thread.sleep(1000);
    }

    public void clickPlapyPause() {
        playPause.click();
    }

    public void clickNext() throws InterruptedException {
        next.click();
        Thread.sleep(1000);
    }

    public String getSongTile() {
        return songTitle.getText();
    }

    public String getDescription() {
        return description.getText();
    }

}
