package mobile_automation;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.testng.annotations.Test;
import page_factory.universal_music_player.*;
import selenium.web.driver.DriverManagerFactory;
import selenium.web.driver.DriverType;
import selenium.web.driver.managers.DriverManager;

public class GlobalKineticUniversalMusicPlayer {
    @Test
    public void universalMusicPlayerTest() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.APPIUM);
        AppiumDriver driver = driverManager.getAppiumDriver();
        Assert.assertNotNull(driver);

        try {
            HomeScreen homeScreen = new HomeScreen(driver);
            homeScreen.wailForVisibilityOfToolBar();
            String appTitleText = homeScreen.getAppTitleText();
            System.out.println("App Title: " + appTitleText);
            String genresText = homeScreen.getGenresText();
            String descriptionText = homeScreen.getDescriptionText();
            System.out.println("Genre: " + genresText + ", Description: " + descriptionText);
            GenreScreen genreScreen = homeScreen.clickGenres();
            Thread.sleep(2000);
            genreScreen.wailForVisibilityOfContent();
            SongListScreen songListScreen = genreScreen.clickGenre(Genre.JAZZ_AND_BLUES);
            Thread.sleep(2000);
            songListScreen.wailForVisibilityOfContent();
            songListScreen.clickSong("The Messenger");
            Thread.sleep(2000);
            songListScreen.clickPlayPause();
            Thread.sleep(2000);
            PlayScreen playScreen = songListScreen.clickAlbumArt();
            Thread.sleep(2000);
            playScreen.wailForVisibilityOfContent();
            playScreen.clickPlapyPause();
            Thread.sleep(2000);

            int numberOfSongs = songListScreen.getNumberOfSongs();
            for (int i = 0; i < numberOfSongs; i++) {
                String songTile = playScreen.getSongTile();
                String description = playScreen.getDescription();
                System.out.println("Title: " + songTile + " , Description: " + description);
                playScreen.clickNext();
                Thread.sleep(1000);
            }

            for (int i = 0; i < numberOfSongs; i++) {
                String songTile = playScreen.getSongTile();
                String description = playScreen.getDescription();
                System.out.println("Title: " + songTile + " , Description: " + description);
                playScreen.clickPrev();
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driverManager.quitAppiumDriver();
        }
    }
}
