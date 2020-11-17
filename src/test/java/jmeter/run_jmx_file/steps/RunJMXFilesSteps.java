package jmeter.run_jmx_file.steps;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import files.RecordReader;
import files.transform.ExcelToDataTable;
import gherkin.formatter.model.Row;
import jmeter.ApplicationPropertiesConfig;
import jmeter.JMeterDriverRunJMX;
import jmeter.models.ResultCollectors;
import jmeter.models.TestLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Properties;

import static jmeter.Config.dataSourcePath;

public class RunJMXFilesSteps {
    private Logger log  = LogManager.getLogger(RunJMXFilesSteps.class);
    private Properties properties;
    private DataTable jmxData;
    private DataTable jmxLoggersData;
    private ResultCollectors jmxResultCollectors;
    private RecordReader recordReader = new RecordReader();

    @Before("@JMXFiles")
    public void setup() {
        log.info("Running Setup");
        ApplicationPropertiesConfig propertiesSetup = new ApplicationPropertiesConfig();
        propertiesSetup.loadProperties();
        properties = propertiesSetup.getProperties();
        log.info("Done Running Setup");
    }

    @After
    public void cleanup() {
    }

    @Then("^Get JMX Files Data to use at \"([^\"]*)\"$")
    public void getJMXFilesData(@Transform(ExcelToDataTable.class)
                                        DataTable dataTable
    ) throws Exception {
        this.jmxData = dataTable;
        log.info(jmxData);
    }

    @Then("^Get JMX Loggers Data to use at \"([^\"]*)\"$")
    public void getJMXLoggers(@Transform(ExcelToDataTable.class)
                                      DataTable dataTable
    ) throws Exception {
        try {
            this.jmxLoggersData = dataTable;
            log.info(jmxLoggersData);
            Object[] headers = jmxLoggersData.getGherkinRows().get(0).getCells().toArray();
            List<String> dataEntry;
            String fileName, name, testClass, guiClass;
            TestLogger testLogger;
            jmxResultCollectors = new ResultCollectors();

            for (Row row : dataTable.getGherkinRows()) {
                if (row.getLine() != 1) {
                    dataEntry = row.getCells();
                    name = recordReader.getCellValue(headers, dataEntry,"Name");
                    fileName = recordReader.getCellValue(headers, dataEntry,"File Name");
                    testClass = recordReader.getCellValue(headers, dataEntry,"Test Class");
                    guiClass = recordReader.getCellValue(headers, dataEntry,"Gui Class");

                    if (!name.isEmpty()) {
                        testLogger = new TestLogger(name, fileName, testClass, guiClass);
                        jmxResultCollectors.addTestLogger(testLogger);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("^Run JMX Files$")
    public void runJMXFiles() throws Throwable {
        try {
            Object[] headers = jmxData.getGherkinRows().get(0).getCells().toArray();
            List<String> dataEntry;
            String testPlanName, run, filePath, isProjectPath, jmxFilesPath = dataSourcePath + "/jmx_files/";

            File file;
            JMeterDriverRunJMX jMeterDriverRunJMX;
            for (Row row : jmxData.getGherkinRows()) {
                try {
                    if (row.getLine() != 1) {
                        dataEntry = row.getCells();
                        testPlanName = recordReader.getCellValue(headers, dataEntry,"Test Plan Name");
                        filePath = recordReader.getCellValue(headers, dataEntry,"File Path");
                        isProjectPath = recordReader.getCellValue(headers, dataEntry,"Is Project Path");
                        run = recordReader.getCellValue(headers, dataEntry,"Run");
                        boolean isProjectPathYes = isProjectPath.equalsIgnoreCase("Yes");
                        boolean isYes = run.equalsIgnoreCase("Yes");

                        if (isYes) {
                            // TODO: Check If File Exists
                            if (isProjectPathYes) {
                                file = new File(jmxFilesPath + filePath);
                            } else {
                                file = new File(filePath);
                            }

                            jMeterDriverRunJMX = new JMeterDriverRunJMX(file, jmxResultCollectors);
                            jMeterDriverRunJMX.runJMXFile(testPlanName);
                        }
                    }
                } catch (Exception e) {
                    log.error("Error Running JMX File On Row: " + row.getLine() + "\n" + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
