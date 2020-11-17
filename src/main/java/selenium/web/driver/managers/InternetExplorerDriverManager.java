package selenium.web.driver.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class InternetExplorerDriverManager extends DriverManager {
    @Override
    protected void createDriver() {
        WebDriverManager.iedriver().setup();
        driver = new InternetExplorerDriver();
    }

    @Override
    protected void createDriver(boolean headless) {
        if (headless) {
            // TODO
        } else {
            createDriver();
        }
    }
}
