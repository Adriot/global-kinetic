package jmeter;

import jmeter.models.*;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class JMeterDriverTester {
    private JMeterDriver jMeterDriver = new JMeterDriver();
    private ApplicationPropertiesConfig applicationPropertiesConfig = new ApplicationPropertiesConfig();
    private Properties properties;
    private TestPlanSet testPlanSet;
    private String reportingPath;
    private HashMap testPlans = new HashMap<String, JMeterDriver>();

    public JMeterDriverTester(TestPlanSet testPlanSet) {
        this.testPlanSet = testPlanSet;
        jMeterDriver.setupJMeterUtils();
        applicationPropertiesConfig.loadProperties();
        this.properties = applicationPropertiesConfig.getProperties();
        reportingPath = applicationPropertiesConfig.getReportingPath();
    }

    public TestPlanSet getTestPlanSet() {
        return testPlanSet;
    }

    public void setTestPlanSet(TestPlanSet testPlanSet) {
        this.testPlanSet = testPlanSet;
    }

    public void run() throws Exception {
        UserDefinedVariables userDefinedVariables = testPlanSet.getUserDefinedVariables();
        HashMap userDefinedVariablesHashMap = userDefinedVariables.getVariableList();

        List<TestPlanConfig> testPlanList = testPlanSet.getTestPlanList();

        for (TestPlanConfig testPlanConfig : testPlanList) {
            String testPlanName = testPlanConfig.getTestPlanName();
            if (testPlans != null) {
                Set keySet = testPlans.keySet();
                boolean contains = keySet.contains(testPlanName);

                if (!contains) {
                    jMeterDriver = new JMeterDriver();
                    jMeterDriver.setupJMeterUtils();
                    jMeterDriver.configureUserDefinedVariable("User Defined Variables", true, userDefinedVariablesHashMap);
                    testPlans.put(testPlanName, jMeterDriver);
                } else {
                    jMeterDriver = (JMeterDriver) testPlans.get(testPlanName);
                }

                String csvDataSetName = testPlanConfig.getCsvDataSetName();
                boolean csvDataSetEnabled = testPlanConfig.isCsvDataSetEnabled();
                String csvDataSetFileName = testPlanConfig.getCsvDataSetFileName();
                String csvDataSetFileEncoding = testPlanConfig.getCsvDataSetFileEncoding();
                String csvDataSetVariableNames = testPlanConfig.getCsvDataSetVariableNames();
                boolean csvDataSetIgnoreFirstLine = testPlanConfig.isCsvDataSetIgnoreFirstLine();
                String csvDataSetDelimiter = testPlanConfig.getCsvDataSetDelimiter();
                boolean csvDataSetQuotedData = testPlanConfig.isCsvDataSetQuotedData();
                boolean csvDataSetRecycle = testPlanConfig.isCsvDataSetRecycle();
                boolean csvDataSetStopThread = testPlanConfig.isCsvDataSetStopThread();
                String csvDataSetShareMode = testPlanConfig.getCsvDataSetShareMode();

                jMeterDriver.configureReportGeneratorPath(csvDataSetName, csvDataSetEnabled, csvDataSetFileName, csvDataSetFileEncoding,
                        csvDataSetVariableNames, csvDataSetIgnoreFirstLine, csvDataSetDelimiter, csvDataSetQuotedData,
                        csvDataSetRecycle, csvDataSetStopThread, csvDataSetShareMode );

                String httpSamplerProxyDomain = testPlanConfig.getHttpSamplerProxyDomain();
                String httpSamplerProxyPort = testPlanConfig.getHttpSamplerProxyPort();
                String httpSamplerProxyPath = testPlanConfig.getHttpSamplerProxyPath();
                String httpSamplerProxyMethod = testPlanConfig.getHttpSamplerProxyMethod();
                String httpSamplerProxyProtocol = testPlanConfig.getHttpSamplerProxyProtocol();
                String httpSamplerProxyName = testPlanConfig.getHttpSamplerProxyName();
                String httpSamplerProxyNonEncodedArgumentName = testPlanConfig.getHttpSamplerProxyNonEncodedArgumentName();
                String httpSamplerProxyNonEncodedArgumentValue = testPlanConfig.getHttpSamplerProxyNonEncodedArgumentValue();
                String httpSamplerProxyNonEncodedArgumentMetaData = testPlanConfig.getHttpSamplerProxyNonEncodedArgumentMetaData();

                HTTPSamplerProxy httpSamplerProxyScenario = jMeterDriver.configureHTTPSamplerProxy(httpSamplerProxyDomain, httpSamplerProxyPort, httpSamplerProxyPath, httpSamplerProxyMethod, httpSamplerProxyProtocol, httpSamplerProxyName, httpSamplerProxyNonEncodedArgumentName, httpSamplerProxyNonEncodedArgumentValue, httpSamplerProxyNonEncodedArgumentMetaData);

                int loopControllerLoops = testPlanConfig.getLoopControllerLoops();
                boolean loopControllerFirst = testPlanConfig.isLoopControllerFirst();

                jMeterDriver.configureLoopController(loopControllerLoops, loopControllerFirst);

                String threadGroupName = testPlanConfig.getThreadGroupName();
                int threadGroupNumberOfThreads = testPlanConfig.getThreadGroupNumberOfThreads();
                int threadGroupRampUp = testPlanConfig.getThreadGroupRampUp();

                String threadGroupOnSampleError = testPlanConfig.getThreadGroupOnSampleError();
                String threadGroupOnRampTime = testPlanConfig.getThreadGroupOnRampTime();
                String threadGroupScheduler = testPlanConfig.getThreadGroupScheduler();
                String threadGroupDuration = testPlanConfig.getThreadGroupDuration();
                String threadGroupDelay = testPlanConfig.getThreadGroupDelay();
                String threadGroupSameUserOnNextIteration = testPlanConfig.getThreadGroupSameUserOnNextIteration();
                jMeterDriver.configureThreadGroup(threadGroupName, threadGroupNumberOfThreads,threadGroupRampUp, threadGroupOnSampleError, threadGroupOnRampTime, threadGroupScheduler, threadGroupDuration, threadGroupDelay, threadGroupSameUserOnNextIteration);

                Assertion assertion = testPlanConfig.getAssertion();
                if (assertion != null) {
                    String assertionName = assertion.getAssertionName();
                    String assertionType = assertion.getAssertionType();
                    String assertionTestString = assertion.getAssertionTestString();
                    String assertionTestField = assertion.getAssertionTestField();
                    ResponseAssertionWrapper responseAssertion = new ResponseAssertionWrapper(httpSamplerProxyScenario, assertionName, assertionType, assertionTestString, assertionTestField);
                    jMeterDriver.addResponseAssertions(responseAssertion);
                }

                BoundaryExtractors boundaryExtractors = testPlanConfig.getBoundaryExtractors();
                if (boundaryExtractors != null) {
                    List<BoundaryExtractorProperties> boundaryExtractorsListProperties = boundaryExtractors.getBoundaryExtractorProperties();
                    String boundaryExtractorTestPlanName, boundaryExtractorHttpSamplerProxyName;
                    for (BoundaryExtractorProperties boundaryExtractorProperties : boundaryExtractorsListProperties) {
                        boundaryExtractorTestPlanName = boundaryExtractorProperties.getTestPlanName();
                        boundaryExtractorHttpSamplerProxyName = boundaryExtractorProperties.getHttpSamplerProxyName();
                        boolean forTestPlanName = boundaryExtractorTestPlanName.equalsIgnoreCase(testPlanName);
                        boolean forHttpSamplerProxyName = boundaryExtractorHttpSamplerProxyName.equalsIgnoreCase(httpSamplerProxyName);

                        if (forTestPlanName && forHttpSamplerProxyName) {
                            BoundaryExtractorWrapper boundaryExtractorWrapper = new BoundaryExtractorWrapper(httpSamplerProxyScenario, boundaryExtractorProperties);
                            jMeterDriver.addBoundaryExtractor(boundaryExtractorWrapper);
                        }
                    }
                }
            }
        }

        if (testPlans != null) {
            Set testPlanKeySet = testPlans.keySet();
            for (Object key : testPlanKeySet) {
                String testPlanName = (String) key;
                jMeterDriver = (JMeterDriver) testPlans.get(testPlanName);

                jMeterDriver.configureTestPlan(testPlanName);
                jMeterDriver.constructTestPlan();
                jMeterDriver.addScenarioResponseAssertions();
                jMeterDriver.loadBoundaryExtractors();
                jMeterDriver.addSummariser();

                String dateTimeFormat = "yyyy dd MM_HH mm ss";
                String dateTimeStamp = getDateTimeStamp(dateTimeFormat);

                String testPlanReportPath = reportingPath + "/" + testPlanName + "/" + dateTimeStamp;
                String csvFile = "", jtlFile = "";
                createDirectory(testPlanReportPath);

                ResultCollectors resultCollectors = testPlanSet.getResultCollectors();
                List<TestLogger> loggerList = resultCollectors.getLoggerList();
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

                jMeterDriver.saveTestPlanTree(testPlanReportPath + "/" + testPlanName + ".jmx");

                // Run Test Plan
                try {
                    jMeterDriver.configureJMeterEngine();
                } catch (Exception e) {
                    // TODO: Handle Errors
                    e.printStackTrace();
                }

                try {
                    jMeterDriver.runJMeterEngine();
                    jMeterDriver.configureReportGeneratorPath(testPlanReportPath + "/html dashboard");

                    if (!csvFile.isEmpty()) {
                        jMeterDriver.generateHtmlReport(testPlanReportPath + "/" + csvFile);
                    } else {
                        jMeterDriver.generateHtmlReport(testPlanReportPath + "/" + jtlFile);
                    }
                } catch (Exception e) {
                    // TODO: Handle Errors
                    e.printStackTrace();
                }
            }
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