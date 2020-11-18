package mobile_automation;
import files.FilePropertiesConfig;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import page_factory.universal_music_player.*;
import selenium.web.driver.DriverManagerFactory;
import selenium.web.driver.DriverType;
import selenium.web.driver.managers.DriverManager;

import java.util.Properties;

public class GlobalKineticUniversalMusicPlayer {
    static Properties properties;
    @BeforeClass
    public static void setup() {
        FilePropertiesConfig filePropertiesConfig = new FilePropertiesConfig(
                "src\\test\\resources\\universal_music_player_data_config.properties");
        filePropertiesConfig.loadProperties();
        properties = filePropertiesConfig.getProperties();
    }

    @Test
    public void universalMusicPlayerTest() {
        String genre = properties.getProperty("GENRE");
        String song = properties.getProperty("SONG");
        String duration = properties.getProperty("DURATION");
        int songDuration = Integer.valueOf(duration);
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
            SongListScreen songListScreen = genreScreen.clickGenre(Genre.valueOf(genre));
            Thread.sleep(2000);
            songListScreen.wailForVisibilityOfContent();
            songListScreen.clickSong(song);
            Thread.sleep(2000);
            songListScreen.clickPlayPause();
            Thread.sleep(2000);
            PlayScreen playScreen = songListScreen.clickAlbumArt();
            Thread.sleep(2000);
            playScreen.wailForVisibilityOfContent();
            playScreen.clickPlapyPause();
            Thread.sleep(songDuration);

            int numberOfSongs = songListScreen.getNumberOfSongs() - 1;
            for (int i = 0; i < numberOfSongs; i++) {
                String songTile = playScreen.getSongTile();
                String description = playScreen.getDescription();
                System.out.println("Title: " + songTile + " , Description: " + description);
                playScreen.clickNext();
                Thread.sleep(songDuration);
            }

            for (int i = 0; i < numberOfSongs; i++) {
                String songTile = playScreen.getSongTile();
                String description = playScreen.getDescription();
                System.out.println("Title: " + songTile + " , Description: " + description);
                playScreen.clickPrev();
                Thread.sleep(songDuration);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driverManager.quitAppiumDriver();
        }
    }
}
