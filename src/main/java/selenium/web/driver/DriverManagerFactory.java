package selenium.web.driver;

import selenium.web.driver.managers.*;

public class DriverManagerFactory {
    public static DriverManager getDriverManager(DriverType driverType) {
        DriverManager driverManager;
        switch (driverType) {
            case CHROME:
                driverManager = new ChromeDriverManager();
                break;
            case FIREFOX:
                driverManager = new FireFoxDriverManager();
                break;
            case OPERA:
                driverManager = new OperaDriverManager();
                break;
            case EDGE:
                driverManager = new EdgeDriverManager();
                break;
            case PHANTOMJS:
                driverManager = new PhantomJsDriverManager();
                break;
            case IEXPLORER:
                driverManager = new InternetExplorerDriverManager();
                break;
            case APPIUM:
                driverManager = new AppiumDriverManager();
                break;
            default:
                driverManager = new ChromeDriverManager();
        }
        return driverManager;
    }
}
