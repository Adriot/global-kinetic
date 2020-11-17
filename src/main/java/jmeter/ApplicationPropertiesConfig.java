package jmeter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationPropertiesConfig implements Config {
    private Logger log  = LogManager.getLogger(ApplicationPropertiesConfig.class);
    protected Properties properties;
    private String propertiesFilePath;

    public ApplicationPropertiesConfig() {
        log.info("Creating PropertiesSetup instance");
    }

    public ApplicationPropertiesConfig(String propertiesFilePath) {
        this();
        this.propertiesFilePath = propertiesFilePath;
    }

    public ApplicationPropertiesConfig(Properties properties, String propertiesFilePath) {
        this(propertiesFilePath);
        this.properties = properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getPropertiesFilePath() {
        log.info("Getting Properties File Path.");
        return propertiesFilePath;
    }

    public void setPropertiesFilePath(String propertiesFilePath) {
        this.propertiesFilePath = propertiesFilePath;
    }

    public void loadProperties() {
        log.info("Loading Properties");
        InputStream inputStream = null;

        if (properties == null) {
            properties = new Properties();
        }

        if (propertiesFilePath == null || propertiesFilePath.isEmpty()) {
            propertiesFilePath = propertiesFile;
        }

        try {
            inputStream = new FileInputStream(new File(propertiesFilePath));
            properties.load(inputStream);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getHome() {
        return propertiesFile;
    }

    @Override
    public Properties getProperties() {
        log.info("Getting Properties");
        return properties;
    }

    public String getReportingPath() {
        return reportSourcePath;
    }
}
