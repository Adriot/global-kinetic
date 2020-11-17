package selenium.web.driver.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.opera.OperaDriver;

public class OperaDriverManager extends DriverManager {
    @Override
    protected void createDriver() {
        WebDriverManager.operadriver().setup();
        driver = new OperaDriver();
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
