package files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class FilePropertiesConfig implements Config {
    private Logger log  = LogManager.getLogger(FilePropertiesConfig.class);
    protected Properties properties;
    private String propertiesFilePath;

    public FilePropertiesConfig() {
        log.info("Creating PropertiesSetup instance");
    }

    public FilePropertiesConfig(String propertiesFilePath) {
        this();
        this.propertiesFilePath = propertiesFilePath;
    }

    public FilePropertiesConfig(Properties properties) {
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

        setupProperties();

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

    public void loadProperties(boolean setupProperties) {
        log.info("Loading Properties");
        InputStream inputStream = null;

        if (setupProperties) {
            setupProperties();
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

    public void loadProperties(String propertiesFilePath) {
        log.info("Loading Properties");
        setPropertiesFilePath(propertiesFilePath);
        InputStream inputStream = null;

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

    public void loadProperties(boolean setupProperties, String propertiesFilePath) {
        log.info("Loading Properties");
        setPropertiesFilePath(propertiesFilePath);
        InputStream inputStream = null;

        if (setupProperties) {
            setupProperties();
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

    private void setupProperties() {
        if (properties == null) {
            properties = new Properties();

            if (propertiesFilePath == null || propertiesFilePath.isEmpty()) {
                propertiesFilePath = propertiesFile;
            }
        }
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
