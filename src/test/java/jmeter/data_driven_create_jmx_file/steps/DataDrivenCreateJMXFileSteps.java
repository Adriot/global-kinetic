package jmeter.data_driven_create_jmx_file.steps;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import files.FilePropertiesConfig;
import files.RecordReader;
import files.transform.ExcelToDataTable;
import gherkin.formatter.model.Row;
import jmeter.JMeterDriverTester;
import jmeter.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static files.Config.propertiesFile;

public class DataDrivenCreateJMXFileSteps {
    private Logger log  = LogManager.getLogger(DataDrivenCreateJMXFileSteps.class);
    private Properties properties;
    private DataTable userDefinedVariablesData;
    private DataTable boundaryExtractorsDataTable;
    private DataTable testPlanData;
    private DataTable loggersData;

    private RecordReader recordReader = new RecordReader();
    private UserDefinedVariables userDefinedVariables;
    private ResultCollectors resultCollectors;
    private TestPlanSet testPlanSet = new TestPlanSet();

    @Before("@DataDrivenCreateJMXFile")
    public void setup() {
        log.info("Running Setup");
        FilePropertiesConfig filePropertiesConfig = new FilePropertiesConfig(propertiesFile);
        filePropertiesConfig.loadProperties();
        properties = filePropertiesConfig.getProperties();
        log.info("Done Running Setup");
    }

    @After
    public void cleanup() {
    }

    @Then("^Get User Defined Variables Sheet to use at \"([^\"]*)\"$")
    public void getUserDefinedVariables(@Transform(ExcelToDataTable.class)
                                                DataTable dataTable
    ) throws Exception {
        this.userDefinedVariablesData = dataTable;
        log.info(userDefinedVariablesData);
        Object[] variableNames = userDefinedVariablesData.getGherkinRows().get(0).getCells().toArray();
        Object[] values = userDefinedVariablesData.getGherkinRows().get(1).getCells().toArray();

        userDefinedVariables = new UserDefinedVariables();
        String variableName, value;
        for (int i = 0; i < variableNames.length; i++) {
            variableName = (String) variableNames[i];
            value = (String) values[i];
            userDefinedVariables.addVariable(variableName, value);
        }

        testPlanSet.setUserDefinedVariables(userDefinedVariables);

    }

    @Then("^Get Boundary Extractors Sheet to use at \"([^\"]*)\"$")
    public void getBoundaryExtractors(@Transform(ExcelToDataTable.class)
                                              DataTable dataTable
    ) throws Exception {
        this.boundaryExtractorsDataTable = dataTable;
    }

    @Then("^Get Loggers List to add at \"([^\"]*)\"$")
    public void getLoggers(@Transform(ExcelToDataTable.class)
                                   DataTable dataTable
    ) throws Exception {
        this.loggersData = dataTable;
        log.info(loggersData);
        Object[] headers = loggersData.getGherkinRows().get(0).getCells().toArray();
        List<String> dataEntry;
        String fileName, name, testClass, guiClass;
        TestLogger testLogger;
        resultCollectors = new ResultCollectors();

        for (Row row : dataTable.getGherkinRows()) {
            if (row.getLine() != 1) {
                dataEntry = row.getCells();
                name = recordReader.getCellValue(headers, dataEntry,"Name");
                fileName = recordReader.getCellValue(headers, dataEntry,"File Name");
                testClass = recordReader.getCellValue(headers, dataEntry,"Test Class");
                guiClass = recordReader.getCellValue(headers, dataEntry,"Gui Class");

                if (!name.isEmpty()) {
                    testLogger = new TestLogger(name, fileName, testClass, guiClass);
                    resultCollectors.addTestLogger(testLogger);
                }
            }
        }

        testPlanSet.setResultCollectors(resultCollectors);

    }

    @Then("^Get Test Plan Data to use at \"([^\"]*)\"$")
    public void getTestPlanData(@Transform(ExcelToDataTable.class)
                                        DataTable dataTable
    ) throws Exception {
        this.testPlanData = dataTable;
        log.info(testPlanData);
        Object[] headers = testPlanData.getGherkinRows().get(0).getCells().toArray();
        List<String> dataEntry;
        String run, testPlanName, loopControllerLoops, loopControllerFirst,
                threadGroupName, threadGroupNumberOfThreads, threadGroupRampUp,
                threadGroupOnSampleError, threadGroupOnRampTime, threadGroupScheduler, threadGroupDuration, threadGroupDelay, threadGroupSameUserOnNextIteration,
                httpSamplerProxyName, httpSamplerProxyDomain, httpSamplerProxyPort, httpSamplerProxyPath, httpSamplerProxyMethod,
                httpSamplerProxyProtocol, httpSamplerProxyNonEncodedArgumentName, httpSamplerProxyNonEncodedArgumentValue,
                httpSamplerProxyNonEncodedArgumentMetaData, csvDataSetName, csvDataSetEnabled, csvDataSetFileName,csvDataSetFileEncoding,
                csvDataSetVariableNames, csvDataSetIgnoreFirstLine, csvDataSetDelimiter, csvDataSetQuotedData, csvDataSetRecycle,
                csvDataSetStopThread, csvDataSetShareMode, assertionName, assertionType, assertionTestString, assertionTestField;
        TestPlanConfig testPlanConfig;

        for (Row row : dataTable.getGherkinRows()) {
            if (row.getLine() != 1) {
                dataEntry = row.getCells();
                run = recordReader.getCellValue(headers, dataEntry,"Run");

                if (run.equalsIgnoreCase("Yes")) {
                    testPlanName = recordReader.getCellValue(headers, dataEntry,"Test Plan Name");
                    loopControllerLoops = recordReader.getCellValue(headers, dataEntry,"LoopController Loops");
                    loopControllerFirst = recordReader.getCellValue(headers, dataEntry,"LoopController First");
                    threadGroupName = recordReader.getCellValue(headers, dataEntry,"ThreadGroup Name");
                    threadGroupNumberOfThreads = recordReader.getCellValue(headers, dataEntry,"ThreadGroup Number Of Threads");
                    threadGroupRampUp = recordReader.getCellValue(headers, dataEntry,"ThreadGroup Ramp Up");

                    threadGroupOnSampleError = recordReader.getCellValue(headers, dataEntry,"ThreadGroup On Sample Error");
                    threadGroupOnRampTime = recordReader.getCellValue(headers, dataEntry,"ThreadGroup On Ramp Time");
                    threadGroupScheduler = recordReader.getCellValue(headers, dataEntry,"ThreadGroup Scheduler");
                    threadGroupDuration = recordReader.getCellValue(headers, dataEntry,"ThreadGroup Duration");
                    threadGroupDelay = recordReader.getCellValue(headers, dataEntry,"ThreadGroup Delay");
                    threadGroupSameUserOnNextIteration = recordReader.getCellValue(headers, dataEntry,"ThreadGroup Same User On Next Iteration");

                    httpSamplerProxyDomain = recordReader.getCellValue(headers, dataEntry,"HTTPSamplerProxy Domain");
                    httpSamplerProxyPort = recordReader.getCellValue(headers, dataEntry,"HTTPSamplerProxy Port");
                    httpSamplerProxyPath = recordReader.getCellValue(headers, dataEntry,"HTTPSamplerProxy Path");
                    httpSamplerProxyMethod = recordReader.getCellValue(headers, dataEntry,"HTTPSamplerProxy Method");
                    httpSamplerProxyProtocol = recordReader.getCellValue(headers, dataEntry,"HTTPSamplerProxy Protocol");
                    httpSamplerProxyName = recordReader.getCellValue(headers, dataEntry,"HTTPSamplerProxy Name");
                    httpSamplerProxyNonEncodedArgumentName = recordReader.getCellValue(headers, dataEntry,"HTTPSamplerProxy NonEncodedArgumentName");
                    httpSamplerProxyNonEncodedArgumentValue = recordReader.getCellValue(headers, dataEntry,"HTTPSamplerProxy NonEncodedArgumentValue");
                    httpSamplerProxyNonEncodedArgumentMetaData = recordReader.getCellValue(headers, dataEntry,"HTTPSamplerProxy NonEncodedArgumentMetaData");

                    csvDataSetName = recordReader.getCellValue(headers, dataEntry,"CsvDataSet Name");
                    csvDataSetEnabled = recordReader.getCellValue(headers, dataEntry,"CsvDataSet Enabled");
                    csvDataSetFileName = recordReader.getCellValue(headers, dataEntry,"CsvDataSet FileName");
                    csvDataSetFileEncoding = recordReader.getCellValue(headers, dataEntry,"CsvDataSet FileEncoding");
                    csvDataSetVariableNames = recordReader.getCellValue(headers, dataEntry,"CsvDataSet VariableNames");
                    csvDataSetIgnoreFirstLine = recordReader.getCellValue(headers, dataEntry,"CsvDataSet IgnoreFirstLine");
                    csvDataSetDelimiter = recordReader.getCellValue(headers, dataEntry,"CsvDataSet Delimiter");
                    csvDataSetQuotedData = recordReader.getCellValue(headers, dataEntry,"CsvDataSet QuotedData");
                    csvDataSetRecycle = recordReader.getCellValue(headers, dataEntry,"CsvDataSet Recycle");
                    csvDataSetStopThread = recordReader.getCellValue(headers, dataEntry,"CsvDataSet StopThread");
                    csvDataSetShareMode = recordReader.getCellValue(headers, dataEntry,"CsvDataSet ShareMode");

                    assertionName = recordReader.getCellValue(headers, dataEntry,"Assertion Name");
                    assertionType = recordReader.getCellValue(headers, dataEntry,"Assertion Type");
                    assertionTestString = recordReader.getCellValue(headers, dataEntry,"Assertion Test String");
                    assertionTestField = recordReader.getCellValue(headers, dataEntry,"Assertions Test Field");

                    Assertion assertion = null;
                    if (assertionName != null) {
                        assertion = new Assertion(assertionName, assertionType, assertionTestString, assertionTestField);
                    }

                    int loopControllerLoopsValue = 1, threadGroupNumberOfThreadsValue = 1, threadGroupRampUpValue = 1;
                    boolean loopControllerFirstValue = false, csvDataSetEnabledValue = false, csvDataSetIgnoreFirstLineValue = false,
                            csvDataSetQuotedDataValue = false, csvDataSetRecycleValue = false, csvDataSetStopThreadValue = false;

                    if (!loopControllerLoops.isEmpty()) {
                        if (loopControllerLoops.contains(".")) {
                            String[] split = loopControllerLoops.split("\\.");
                            try {
                                loopControllerLoopsValue = Integer.valueOf(split[0]);
                            } catch (Exception e) {
                                // TODO: Handle Exceptions, for now set default.
                                loopControllerLoopsValue = 1;
                            }
                        }
                    }

                    if (!threadGroupNumberOfThreads.isEmpty()) {
                        if (threadGroupNumberOfThreads.contains(".")) {
                            String[] split = threadGroupNumberOfThreads.split("\\.");
                            try {
                                threadGroupNumberOfThreadsValue = Integer.valueOf(split[0]);
                            } catch (Exception e) {
                                // TODO: Handle Exceptions, for now set default.
                                threadGroupNumberOfThreadsValue = 1;
                            }
                        }
                    }

                    if (!threadGroupRampUp.isEmpty()) {
                        if (threadGroupRampUp.contains(".")) {
                            String[] split = threadGroupRampUp.split("\\.");
                            try {
                                threadGroupRampUpValue = Integer.valueOf(split[0]);
                            } catch (Exception e) {
                                // TODO: Handle Exceptions, for now set default.
                                threadGroupRampUpValue = 1;
                            }
                        }
                    }

                    if (!loopControllerFirst.isEmpty()) {
                        try {
                            loopControllerFirstValue = Boolean.valueOf(loopControllerFirst);
                        } catch (Exception e) {
                            // TODO: handle Exceptions, for now set default.
                            loopControllerFirstValue = false;
                        }
                    }

                    if (!csvDataSetEnabled.isEmpty()) {
                        try {
                            csvDataSetEnabledValue = Boolean.valueOf(csvDataSetEnabled);
                        } catch (Exception e) {
                            // TODO: handle Exceptions, for now set default.
                            csvDataSetEnabledValue = false;
                        }
                    }

                    if (!csvDataSetIgnoreFirstLine.isEmpty()) {
                        try {
                            csvDataSetIgnoreFirstLineValue = Boolean.valueOf(csvDataSetIgnoreFirstLine);
                        } catch (Exception e) {
                            // TODO: handle Exceptions, for now set default.
                            csvDataSetIgnoreFirstLineValue = false;
                        }
                    }

                    if (!csvDataSetQuotedData.isEmpty()) {
                        try {
                            csvDataSetQuotedDataValue = Boolean.valueOf(csvDataSetQuotedData);
                        } catch (Exception e) {
                            // TODO: handle Exceptions, for now set default.
                            csvDataSetQuotedDataValue = false;
                        }
                    }

                    if (!csvDataSetRecycle.isEmpty()) {
                        try {
                            csvDataSetRecycleValue = Boolean.valueOf(csvDataSetRecycle);
                        } catch (Exception e) {
                            // TODO: handle Exceptions, for now set default.
                            csvDataSetRecycleValue = false;
                        }
                    }

                    if (!csvDataSetStopThread.isEmpty()) {
                        try {
                            csvDataSetStopThreadValue = Boolean.valueOf(csvDataSetStopThread);
                        } catch (Exception e) {
                            // TODO: handle Exceptions, for now set default.
                            csvDataSetStopThreadValue = false;
                        }
                    }

                    BoundaryExtractors boundaryExtractors = new BoundaryExtractors();

                    Object[] boundaryExtractorsHeaders = boundaryExtractorsDataTable.getGherkinRows().get(0).getCells().toArray();
                    loadBoundaryExtractors(boundaryExtractors, boundaryExtractorsHeaders);

                    if (!testPlanName.isEmpty()) {
                        testPlanConfig = new TestPlanConfig(testPlanName, loopControllerLoopsValue, loopControllerFirstValue,
                                threadGroupName, threadGroupNumberOfThreadsValue, threadGroupRampUpValue,
                                threadGroupOnSampleError, threadGroupOnRampTime, threadGroupScheduler, threadGroupDuration, threadGroupDelay, threadGroupSameUserOnNextIteration,
                                httpSamplerProxyName, httpSamplerProxyDomain, httpSamplerProxyPort,
                                httpSamplerProxyPath, httpSamplerProxyMethod, httpSamplerProxyProtocol,
                                httpSamplerProxyNonEncodedArgumentName, httpSamplerProxyNonEncodedArgumentValue, httpSamplerProxyNonEncodedArgumentMetaData,
                                csvDataSetName, csvDataSetEnabledValue, csvDataSetFileName, csvDataSetFileEncoding, csvDataSetVariableNames, csvDataSetIgnoreFirstLineValue,
                                csvDataSetDelimiter, csvDataSetQuotedDataValue, csvDataSetRecycleValue,
                                csvDataSetStopThreadValue, csvDataSetShareMode, boundaryExtractors);

                        if (assertion != null) {
                            testPlanConfig.setAssertion(assertion);
                        }

                        testPlanSet.addTestPlanConfig(testPlanConfig);
                    }
                }
            }
        }

    }

    private void loadBoundaryExtractors(BoundaryExtractors boundaryExtractors, Object[] boundaryExtractorsHeaders) throws Exception {
        List<String> boundaryExtractorsDateEntry;

        for (Row gherkinRows : boundaryExtractorsDataTable.getGherkinRows()) {
            if (gherkinRows.getLine() != 1) {
                boundaryExtractorsDateEntry = gherkinRows.getCells();
                String boundaryExtractorTestPlanName = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"Test Plan Name");
                String boundaryExtractorHTTPSamplerProxyName = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"HTTPSamplerProxy Name");
                String boundaryExtractorName = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"Name");
                String enabled = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"Enabled");
                String refName = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"Ref Name");
                String leftBoundary = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"Left Boundary");
                String rightBoundary = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"Right Boundary");
                String matchNumber = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"Match Number");
                String defaultValue = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"Default Value");
                String stringProperties = recordReader.getCellValue(boundaryExtractorsHeaders, boundaryExtractorsDateEntry,"String Properties");

                HashMap stringPropertiesHash = new HashMap();
                if (!stringProperties.isEmpty()) {
                    String[] stringPropertiesSplit = stringProperties.split("\"],\\[\"");
                    for (String stringProp: stringPropertiesSplit) {
                        String[] keyValue = stringProp.split("\",\"");
                        String keyRaw = keyValue[0];
                        String valueRaw = keyValue[1];

                        String key = keyRaw.replaceFirst("^\\[\"", "");
                        String value = valueRaw.replaceFirst("\"]$", "");

                        stringPropertiesHash.put(key, value);
                    }
                }

                BoundaryExtractorProperties boundaryExtractorProperties = new BoundaryExtractorProperties(
                        boundaryExtractorTestPlanName,
                        boundaryExtractorHTTPSamplerProxyName,
                        boundaryExtractorName,
                        enabled,
                        refName,
                        leftBoundary,
                        rightBoundary,
                        matchNumber,
                        defaultValue,
                        stringPropertiesHash);

                boundaryExtractors.addBoundaryExtractor(boundaryExtractorProperties);
            }
        }
    }

    @Then("^Run Test Plans$")
    public void runTestPlans() throws Throwable {
        JMeterDriverTester jMeterDriverTester = new JMeterDriverTester(testPlanSet);
        jMeterDriverTester.run();
    }

}
