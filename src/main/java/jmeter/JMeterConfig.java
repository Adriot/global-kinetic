package jmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

public class JMeterConfig extends ApplicationPropertiesConfig {
    private File jMeterHome;
    private File jMeterProperties;

    public JMeterConfig() {
    }

    public JMeterConfig(String propertiesFilePath) {
        super(propertiesFilePath);
    }

    public JMeterConfig(Properties properties, String propertiesFilePath) {
        super(properties, propertiesFilePath);
    }

    public File getjMeterHome() {
        return jMeterHome;
    }

    public File getjMeterProperties() {
        return jMeterProperties;
    }

    public String getjMeterHomePath() throws FileNotFoundException {
        if (jMeterHome != null) {
            return jMeterHome.getPath();
        } else {
            throw new FileNotFoundException("JMeter Home File Path Not Found.");
        }
    }

    public String getjMeterPropertiesPath() throws FileNotFoundException {
        if (jMeterProperties != null) {
            return jMeterProperties.getPath();
        } else {
            throw new FileNotFoundException("JMeter Properties File Path Not Found.");
        }
    }

    private String getJMeterHomePath() {
        loadProperties();
        String apacheJMeterPath = properties.getProperty("APACHE_JMETER");
        if (apacheJMeterPath.isEmpty()) {
            apacheJMeterPath = apacheJMeterEngine;
        }
        return apacheJMeterPath;
    }

    public void setjMeterHome() {
        String jMeterHomePath = getJMeterHomePath();
        jMeterHome = new File(jMeterHomePath);
    }

    public void setjMeterProperties() throws FileNotFoundException {
        String jMeterPropertiesPath = getjMeterHomePath() + fileSeparator + appBin + fileSeparator + jMeterPropertiesFile;
        jMeterProperties = new File(jMeterPropertiesPath);
    }
}
