package files;

import java.util.Properties;

/**
 * This is an interface that will initialize all the needed constants to be used by the framework.
 */
public interface Config {
    String dataSourcePath = "src/test/resources/data";
    String reportSourcePath = "src/test/resources/reporting";
    String propertiesFile = "src/test/resources/config.properties";
    Properties getProperties();
}
