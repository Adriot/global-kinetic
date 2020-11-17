package selenium.web.driver.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverManager extends DriverManager {
    @Override
    protected void createDriver() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
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
