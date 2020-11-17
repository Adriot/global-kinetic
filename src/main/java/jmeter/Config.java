package jmeter;

import java.util.Properties;

/**
 * This is an interface that will initialize all the needed constants to be used by the framework.
 */
public interface Config {
    String fileSeparator = System.getProperty("file.separator");
    String appBin = "bin";
    String jMeterPropertiesFile = "jmeter.properties";
    String dataSourcePath = "src/test/resources/data";
    String reportSourcePath = "src/test/resources/reporting";
    String propertiesFile = "src/main/resources/config.properties";
    String apacheJMeterEngine = "jmeter_engine/apache-jmeter-5.2.1";
    String getHome();
    Properties getProperties();
}