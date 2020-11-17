package selenium.web.driver.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.HashMap;

public class ChromeDriverManager extends DriverManager {
    @Override
    protected void createDriver() {
        createDriver(false);
    }

    @Override
    protected void createDriver(boolean headless) {
        desiredCapabilities = desiredCapabilities != null ? desiredCapabilities : new DesiredCapabilities() ;
        ChromeOptions options = new ChromeOptions();
        String switches = "--ignore-certificate-errors," +
                          "--disable-popup-blocking," +
                          "--disable-translate," +
                          "--incognito,--disable-extensions";
        String[] switchList = switches.split(",");

        options.setCapability("chrome.switches", Arrays.asList(switchList));
        options.addArguments("test-type");

        HashMap<String, Object> chromePreferences = new HashMap<>();
        chromePreferences.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", chromePreferences);

        if (headless) {
            options.addArguments("--headless");
        }

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
    }
}
