package selenium.web.driver.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJsDriverManager extends DriverManager {
    @Override
    protected void createDriver() {
        desiredCapabilities = desiredCapabilities != null ? desiredCapabilities : new DesiredCapabilities();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("takesScreenshot", true);
        String [] phantomJsArgs = {"--web-security=no", "--ignore-ssl-errors=yes"};
        desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomJsArgs);
        WebDriverManager.phantomjs().setup();
        driver = new PhantomJSDriver(desiredCapabilities);
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
