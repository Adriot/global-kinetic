package jmeter;

import jmeter.models.ResultCollectors;
import jmeter.models.TestLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

public class JMeterDriverRunJMX {
    private Logger log  = LogManager.getLogger(JMeterDriverRunJMX.class);
    private JMeterDriver jMeterDriver;
    private ApplicationPropertiesConfig applicationPropertiesConfig = new ApplicationPropertiesConfig();
    private Properties properties;
    private String reportingPath;
    private File jmxFile;
    private ResultCollectors resultCollectors;

    public JMeterDriverRunJMX(File jmxFile, ResultCollectors resultCollectors) {
        this.jmxFile = jmxFile;
        this.resultCollectors = resultCollectors;
        applicationPropertiesConfig.loadProperties();
        this.properties = applicationPropertiesConfig.getProperties();
        reportingPath = applicationPropertiesConfig.getReportingPath();
    }

    public void runJMXFile(String reportFolder) throws Exception {
        try {
            log.info("Running JMX File");
            String dateTimeFormat = "yyyy dd MM_HH mm ss";
            String dateTimeStamp = getDateTimeStamp(dateTimeFormat);
            String testPlanReportPath = reportingPath + "/" + reportFolder + "/" + dateTimeStamp;
            createDirectory(testPlanReportPath);

            jMeterDriver = new JMeterDriver(jmxFile);
            jMeterDriver.setupJMeterUtils(true);
            jMeterDriver.addSummariser();

            String csvFile = "", jtlFile = "";
            if (resultCollectors != null) {
                List<TestLogger> loggerList = resultCollectors.getLoggerList();
                if (loggerList != null) {
                    for (TestLogger testLogger : loggerList) {
                        String name = testLogger.getName();
                        String fileName = testLogger.getFileName();
                        String testClass = testLogger.getTestClass();
                        String guiClass = testLogger.getGuiClass();
                        jMeterDriver.addLogger(testPlanReportPath + "/" + fileName, name, testClass, guiClass);

                        if (!fileName.isEmpty()) {
                            if (fileName.contains(".csv")) {
                                csvFile = fileName;
                            } else if (fileName.contains(".jtl")) {
                                jtlFile = fileName;
                            }
                        }
                    }
                }
            }

            jMeterDriver.runJMXFile();
            jMeterDriver.configureReportGeneratorPath(testPlanReportPath + "/html dashboard");

            if (!csvFile.isEmpty()) {
                jMeterDriver.generateHtmlReport(testPlanReportPath + "/" + csvFile);
            } else {
                jMeterDriver.generateHtmlReport(testPlanReportPath + "/" + jtlFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while running JMX File: " + e.getMessage());
            throw new Exception("Error while running JMX File: " + e.getMessage());
        }
    }

    private String getDateTimeStamp(String dateTimeFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    private void createDirectory(String testPlanReportPath) throws Exception {
        Path path = Paths.get(testPlanReportPath);
        boolean exists = Files.exists(path);
        if (!exists) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                throw new Exception("Error while creating Directory: " + testPlanReportPath + "\t" + e.getMessage());
            }
        }
    }

}