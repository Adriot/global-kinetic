package jmeter;

import jmeter.models.BoundaryExtractorProperties;
import jmeter.models.BoundaryExtractorWrapper;
import jmeter.models.ResponseAssertionWrapper;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.gui.AssertionGui;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.extractor.BoundaryExtractor;
import org.apache.jmeter.extractor.gui.BoundaryExtractorGui;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.SimpleDataWriter;
import org.apache.jmeter.visualizers.SummaryReport;
import org.apache.jmeter.visualizers.ViewResultsFullVisualizer;
import org.apache.jorphan.collections.HashTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import static org.apache.jmeter.JMeter.JMETER_REPORT_OUTPUT_DIR_PROPERTY;

public class JMeterDriver {
    private Logger log  = LogManager.getLogger(JMeterDriver.class);
    private JMeterConfig jMeterConfig = new JMeterConfig();
    private StandardJMeterEngine jMeterEngine = new StandardJMeterEngine();
    private HashTree testPlanTree = new HashTree();
    private HTTPSamplerProxy httpSamplerProxy;
    private List<HTTPSamplerProxy> httpSamplerProxyList = new ArrayList<>();
    private List<ResponseAssertionWrapper> responseAssertions = new ArrayList<>();
    private List<BoundaryExtractorWrapper> boundaryExtractorWrappers = new ArrayList<>();
    private LoopController loopController = new LoopController();
    private ThreadGroup threadGroup = new ThreadGroup();
    private TestPlan testPlan;
    private HashTree threadGroupHashTree;
    private Summariser summariser;
    private String summariserName;
    private File jMeterHome;
    private File jMeterProperties;
    private CSVDataSet csvDataSet = new CSVDataSet();
    private Arguments arguments = new Arguments();
    SummaryReport summaryReport;
    private File jmxFile;

    public JMeterDriver() {
        log.info("Setting up JMeter.");
        try {
            log.info("Setting JMeter Home.");
            jMeterConfig.setjMeterHome();
            log.info("Setting JMeter Properties.");
            jMeterConfig.setjMeterProperties();
            log.info("Getting JMeter Home File.");
            jMeterHome = jMeterConfig.getjMeterHome();
            log.info("Getting JMeter Properties File.");
            jMeterProperties = jMeterConfig.getjMeterProperties();
        } catch (Exception e) {
            log.error("Error while setting up JMeter.");
        }
    }

    public JMeterDriver(File jmxFile) {
        this();
        this.jmxFile = jmxFile;
    }

    public void setupJMeterUtils() {
        if (jMeterHome.exists()) {
            String jMeterHomePath = null;
            try {
                jMeterHomePath = jMeterConfig.getjMeterHomePath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (jMeterProperties.exists()) {
                String jMeterPropertiesPath = null;
                try {
                    jMeterPropertiesPath = jMeterConfig.getjMeterPropertiesPath();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                JMeterUtils.setJMeterHome(jMeterHomePath);
                JMeterUtils.loadJMeterProperties(jMeterPropertiesPath);
                JMeterUtils.initLogging();
                JMeterUtils.initLocale();
            }
        }
    }

    public void setupJMeterUtils(boolean setupJMXFile) throws Exception {
        setupJMeterUtils();

        if (setupJMXFile) {
            if (jmxFile != null) {
                setupJMXFile(jmxFile);
            }
        }
    }

    public void setupJMXFile(File jmxFile) throws Exception {
        try {
            log.info("Setting up JMeter JMX File");
            SaveService.loadProperties();
            testPlanTree = SaveService.loadTree(jmxFile);
            jMeterEngine.configure(testPlanTree);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while setting up JMeter JMX File: " + e.getMessage());
            throw new Exception("Error while setting up JMeter JMX File: " + e.getMessage());
        }
    }

    public void runJMXFile() throws Exception {
        try {
            log.info("Running JMX File.");
            jMeterEngine.run();
        } catch (Exception e) {
            log.error("Error while running JMX File: " + e.getMessage());
            throw new Exception("Error while running JMX File: " + e.getMessage());
        }
    }

    private void setHTTPSamplerProxyDomain(String domain) throws Exception {
        try {
            log.info("Setting up HTTPSamplerProxy Domain.");
            httpSamplerProxy.setDomain(domain);
        } catch (Exception e) {
            log.error("Error while trying to set HTTPSamplerProxy Domain.");
            throw new Exception("Error while trying to set HTTPSamplerProxy Domain.");
        }
    }

    private void setHTTPSamplerProxyPort(String port) throws Exception {
        try {
            log.info("Setting up HTTPSamplerProxy Domain: " + port);
            // httpSamplerProxy.setPort(port);
            httpSamplerProxy.setProperty("HTTPSampler.port", port);
        } catch (Exception e) {
            log.error("Error while trying to set HTTPSamplerProxy Domain.");
            throw new Exception("Error while trying to set HTTPSamplerProxy Domain.");
        }
    }

    private void setHTTPSamplerProxyPath(String path) throws Exception {
        try {
            log.info("Setting up HTTPSamplerProxy Path.");
            httpSamplerProxy.setPath(path);
        } catch (Exception e) {
            log.error("Error while trying to set HTTPSamplerProxy Path.");
            throw new Exception("Error while trying to set HTTPSamplerProxy Path.");
        }
    }

    private void setHTTPSamplerProxyMethod(String method) throws Exception {
        try {
            log.info("Setting up HTTPSamplerProxy Method.");
            httpSamplerProxy.setMethod(method);
        } catch (Exception e) {
            log.error("Error while trying to set HTTPSamplerProxy Method.");
            throw new Exception("Error while trying to set HTTPSamplerProxy Method.");
        }
    }

    private void setHTTPSamplerProxyProtocol(String protocol) throws Exception {
        try {
            log.info("Setting up HTTPSamplerProxy Protocol.");
            httpSamplerProxy.setProtocol(protocol);
        } catch (Exception e) {
            log.error("Error while trying to set HTTPSamplerProxy Protocol.");
            throw new Exception("Error while trying to set HTTPSamplerProxy Protocol.");
        }
    }

    private void setHTTPSamplerProxyName(String name) throws Exception {
        try {
            log.info("Setting up HTTPSamplerProxy Name.");
            httpSamplerProxy.setName(name);
        } catch (Exception e) {
            log.error("Error while trying to set HTTPSamplerProxy Name.");
            throw new Exception("Error while trying to set HTTPSamplerProxy Name.");
        }
    }

    private void addHTTPSamplerProxyNonEncodedArgument(String name, String value, String metaData) throws Exception {
        try {
            log.info("Setting up HTTPSamplerProxy NonEncodedArgument.");
            httpSamplerProxy.addNonEncodedArgument(name, value, metaData);
            httpSamplerProxy.setPostBodyRaw(true);
        } catch (Exception e) {
            log.error("Error while trying to set HTTPSamplerProxy NonEncodedArgument.");
            throw new Exception("Error while trying to set HTTPSamplerProxy NonEncodedArgument.");
        }
    }

    public void configureHTTPSamplerProxy(String domain, String port, String path, String method, String protocol, String name) throws Exception {
        setHTTPSamplerProxyDomain(domain);
        setHTTPSamplerProxyPort(port);
        setHTTPSamplerProxyPath(path);
        setHTTPSamplerProxyMethod(method);
        if (!protocol.isEmpty()) {
            setHTTPSamplerProxyProtocol(protocol);
        }
        setHTTPSamplerProxyName(name);
        try {
            httpSamplerProxy.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
            httpSamplerProxy.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        } catch (Exception e) {
            log.error("Error while configuring HTTPSamplerProxy.");
            throw new Exception("Error while configuring HTTPSamplerProxy.");
        }
    }

    public HTTPSamplerProxy configureHTTPSamplerProxy(String domain, String port, String path, String method, String protocol, String name,
                                                      String nonEncodedArgumentName, String nonEncodedArgumentValue,
                                                      String nonEncodedArgumentMetaData) throws Exception {
        httpSamplerProxy = new HTTPSamplerProxy();
        configureHTTPSamplerProxy(domain, port, path, method, protocol, name);
        addHTTPSamplerProxyNonEncodedArgument(nonEncodedArgumentName, nonEncodedArgumentValue, nonEncodedArgumentMetaData);
        try {
            httpSamplerProxy.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
            httpSamplerProxy.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

            // addResponseAssertion();

            httpSamplerProxyList.add(httpSamplerProxy);
            return httpSamplerProxy;
        } catch (Exception e) {
            log.error("Error while configuring HTTPSamplerProxy.");
            throw new Exception("Error while configuring HTTPSamplerProxy.");
        }
    }

    public void addResponseAssertions(ResponseAssertionWrapper responseAssertion) throws Exception {
        try {
            responseAssertions.add(responseAssertion);
        } catch (Exception e) {
            log.error("Error while trying to Add ResponseAssertionWrapper. " + e.getMessage());
            throw new Exception("Error while trying to Add ResponseAssertionWrapper. " + e.getMessage());
        }
    }

    public void configureResponseAssertion(HTTPSamplerProxy scenario, String assertionName, String type, String testString, String testField) throws Exception {
        // TODO: Refactor Assertions To Use This Method As Entry With Logic Of Which One To Call.
        ResponseAssertion responseAssertion = new ResponseAssertion();
        try {
            responseAssertion.setProperty(TestElement.GUI_CLASS, AssertionGui.class.getName());
            responseAssertion.setProperty(TestElement.TEST_CLASS, ResponseAssertion.class.getName());
            responseAssertion.setName(assertionName);
            responseAssertion.setEnabled(true);

            if (!type.isEmpty()) {
                String[] types = type.split(",");

                for (String singleType : types) {
                    if (singleType.equalsIgnoreCase("Contains")) {
                        responseAssertion.setToContainsType();
                    } else if (singleType.equalsIgnoreCase("Not")) {
                        responseAssertion.setToNotType();
                    } else if (singleType.equalsIgnoreCase("Equals")) {
                        responseAssertion.setToEqualsType();
                    } else if (singleType.equalsIgnoreCase("Match")) {
                        responseAssertion.setToMatchType();
                    } else if (singleType.equalsIgnoreCase("OR")) {
                        responseAssertion.setToOrType();
                    } else if (singleType.equalsIgnoreCase("Substring")) {
                        responseAssertion.setToSubstringType();
                    }
                }
            }

            if (testField.equalsIgnoreCase("RequestData")) {
                responseAssertion.setTestFieldRequestData();
            } else if (testField.equalsIgnoreCase("RequestHeaders")) {
                responseAssertion.setTestFieldRequestHeaders();
            } else if (testField.equalsIgnoreCase("ResponseCode")) {
                responseAssertion.setTestFieldResponseCode();
            } else if (testField.equalsIgnoreCase("ResponseData")) {
                responseAssertion.setTestFieldResponseData();
            } else if (testField.equalsIgnoreCase("ResponseHeaders")) {
                responseAssertion.setTestFieldResponseHeaders();
            } else if (testField.equalsIgnoreCase("ResponseMessage")) {
                responseAssertion.setTestFieldResponseMessage();
            } else if (testField.equalsIgnoreCase("ResponseDataAsDocument")) {
                responseAssertion.setTestFieldResponseDataAsDocument();
            }

            if (!testString.isEmpty()) {
                responseAssertion.addTestString(testString);
            }

            threadGroupHashTree.add(scenario, responseAssertion);
        } catch (Exception e) {
            log.error("Error while configuring ResponseAssertion.");
            throw new Exception("Error while configuring ResponseAssertion.");
        }
    }

    private void setLoopControllerLoops(Integer loops) throws Exception {
        try {
            log.info("Setting up LoopController Loops: " + loops);
            loopController.setLoops(loops);
        } catch (Exception e) {
            log.error("Error while trying to set LoopController Loops.");
            throw new Exception("Error while trying to set LoopController Loops.");
        }
    }

    private void setLoopControllerFirst(Boolean first) throws Exception {
        try {
            log.info("Setting up LoopController First: " + first);
            loopController.setFirst(first);
        } catch (Exception e) {
            log.error("Error while trying to set LoopController First.");
            throw new Exception("Error while trying to set LoopController First.");
        }
    }

    public void configureLoopController(Integer loops, Boolean first) throws Exception {
        setLoopControllerLoops(loops);
        setLoopControllerFirst(first);
        try {
            loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
            loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
            loopController.initialize();
        } catch (Exception e) {
            log.error("Error while configuring Loop Controller.");
            throw new Exception("Error while configuring Loop Controller.");
        }
    }

    private void setThreadGroupName(String name) throws Exception {
        try {
            log.info("Setting up ThreadGroup Name: " + name);
            threadGroup.setName(name);
        } catch (Exception e) {
            log.error("Error while trying to set ThreadGroup Name.");
            throw new Exception("Error while trying to set ThreadGroup Name.");
        }
    }

    private void setThreadGroupNumThreads(Integer numThreads) throws Exception {
        try {
            log.info("Setting up ThreadGroup NumThreads: " + numThreads);
            threadGroup.setNumThreads(numThreads);
        } catch (Exception e) {
            log.error("Error while trying to set ThreadGroup NumThreads.");
            throw new Exception("Error while trying to set ThreadGroup NumThreads.");
        }
    }

    private void setThreadGroupRampUp(Integer rampUp) throws Exception {
        try {
            log.info("Setting up ThreadGroup RampUp: " + rampUp);
            threadGroup.setRampUp(rampUp);
        } catch (Exception e) {
            log.error("Error while trying to set ThreadGroup RampUp.");
            throw new Exception("Error while trying to set ThreadGroup RampUp.");
        }
    }

    public void configureThreadGroup(String name, Integer numThreads, Integer rampUp, String threadGroupOnSampleError, String threadGroupOnRampTime, String threadGroupScheduler, String threadGroupDuration, String threadGroupDelay, String threadGroupSameUserOnNextIteration) throws Exception {
        setThreadGroupName(name);
        setThreadGroupNumThreads(numThreads);
        setThreadGroupRampUp(rampUp);
        try {
            threadGroup.setSamplerController(loopController);
            threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
            threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

            threadGroup.setProperty("ThreadGroup.on_sample_error", threadGroupOnSampleError);
            threadGroup.setProperty("ThreadGroup.ramp_time", threadGroupOnRampTime);

            if (!threadGroupScheduler.isEmpty()) {
                if (threadGroupScheduler.equalsIgnoreCase("true")) {
                    threadGroup.setScheduler(true);
                } else {
                    threadGroup.setScheduler(true);
                }
                // threadGroup.setProperty("ThreadGroup.scheduler", threadGroupScheduler);
            }

            threadGroup.setProperty("ThreadGroup.duration", threadGroupDuration);
            threadGroup.setProperty("ThreadGroup.delay", threadGroupDelay);

            if (!threadGroupSameUserOnNextIteration.isEmpty()) {
                if (threadGroupSameUserOnNextIteration.equalsIgnoreCase("true")) {
                    threadGroup.setProperty("ThreadGroup.same_user_on_next_iteration", true);
                } else {
                    threadGroup.setProperty("ThreadGroup.same_user_on_next_iteration", false);
                }
            }
        } catch (Exception e) {
            log.error("Error while configuring ThreadGroup.");
            throw new Exception("Error while configuring ThreadGroup.");
        }
    }

    public void configureTestPlan(String name) throws Exception {
        try {
            testPlan = new TestPlan(name);
            testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
            testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
            testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());
        } catch (Exception e) {
            log.error("Error while configuring Test Plan.");
            throw new Exception("Error while configuring Test Plan.");
        }
    }

    public void constructTestPlan() throws Exception {
        try {
            testPlanTree.add(testPlan);
            threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
            threadGroupHashTree.add(arguments);
            for (HTTPSamplerProxy scenario: httpSamplerProxyList) {
                threadGroupHashTree.add(scenario);
            }

            threadGroupHashTree.add(csvDataSet);

            HeaderManager headerManager = new HeaderManager();
            headerManager.setProperty(TestElement.NAME, "HTTP Header Manager");
            headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
            headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
            Header header = new Header();
            header.setName("content-type");
            header.setValue("txt");
            headerManager.add(header);
            HashTree headerManagerHashTree = new HashTree();
            headerManagerHashTree.add(headerManager);
            threadGroupHashTree.add(headerManagerHashTree);
        } catch (Exception e) {
            log.error("Error while constructing Test Plan.");
            throw new Exception("Error while constructing Test Plan.");
        }
    }

    public void addScenarioResponseAssertions() throws Exception {
        try {
            for (ResponseAssertionWrapper responseAssertion : responseAssertions) {
                HTTPSamplerProxy httpSamplerProxyScenario = responseAssertion.getHttpSamplerProxyScenario();
                String responseAssertionName = responseAssertion.getName();
                String responseAssertionType = responseAssertion.getType();
                String testString = responseAssertion.getTestString();
                String testField = responseAssertion.getTestField();
                configureResponseAssertion(httpSamplerProxyScenario, responseAssertionName, responseAssertionType, testString, testField);
            }
        } catch (Exception e) {
            log.error("Error while adding scenarion response assertions. " + e.getMessage());
            throw  new Exception("Error while adding scenarion response assertions. " + e.getMessage());
        }
    }

    public void saveTestPlanTree(String name) throws Exception {
        try {
            SaveService.saveTree(testPlanTree, new FileOutputStream(name));
        } catch (Exception e) {
            log.error("Error while saving Test Plan Tree.");
            throw new Exception("Error while Saving Test Plan Tree.");
        }
    }

    public void addSummariser() throws Exception {
        try {
            summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
            if (summariserName.length() > 0) {
                summariser = new Summariser(summariserName);
            }
        } catch (Exception e) {
            log.error("Error while Adding Summariser.");
            throw new Exception("Error while Adding Summariser.");
        }
    }

    public void addSummaryReport() throws Exception {
        try {
            summaryReport = new SummaryReport();
            summaryReport.setName("Summary Report");
            summaryReport.setFile("testSummaryReport");
        } catch (Exception e) {
            log.error("Error while Adding SummaryReport.");
            throw new Exception("Error while Adding SummaryReport.");
        }
    }

    public void configureSummaryReport() throws Exception {
        try {
            addSummaryReport();
            // summaryReport.setProperty(TestElement.TEST_CLASS, ResultCollector.class.getName());
            // summaryReport.setProperty(TestElement.GUI_CLASS, SummaryReport.class.getName());
        } catch (Exception e) {
            log.error("Error while configuring SummaryReport.");
            throw new Exception("Error while configuring SummaryReport.");
        }
    }

    public void addLogger(String logFile) throws Exception {
        try {
            ResultCollector logger = new ResultCollector(summariser);
            logger.setFilename(logFile);
            logger.setEnabled(true);
            testPlanTree.add(testPlanTree.getArray()[0], logger);
        } catch (Exception e) {
            log.error("Error while Adding Logger.");
            throw new Exception("Error while Adding Logger.");
        }
    }

    public void addLogger(String file, String name, String testClass, String giuClass) throws Exception {
        try {
            ResultCollector logger = new ResultCollector(summariser);

            if (!file.isEmpty()) {
                logger.setFilename(file);
            }

            logger.setEnabled(true);
            logger.setName(name);

            if (testClass.equalsIgnoreCase("ResultCollector")) {
                logger.setProperty(TestElement.TEST_CLASS, ResultCollector.class.getName());
            }

            if (giuClass.equalsIgnoreCase("SimpleDataWriter")) {
                logger.setProperty(TestElement.GUI_CLASS, SimpleDataWriter.class.getName());
            } else if (giuClass.equalsIgnoreCase("ViewResultsFullVisualizer")) {
                logger.setProperty(TestElement.GUI_CLASS, ViewResultsFullVisualizer.class.getName());
            } else if (giuClass.equalsIgnoreCase("SummaryReport")) {
                logger.setProperty(TestElement.GUI_CLASS, SummaryReport.class.getName());
            }

            testPlanTree.add(testPlanTree.getArray()[0], logger);
        } catch (Exception e) {
            log.error("Error while Adding Logger.");
            throw new Exception("Error while Adding Logger.");
        }
    }

    public void addUserDefinedVariables(HashMap<String, String> userDefinedVariables) throws Exception {
        try {
            Set<Map.Entry<String, String>> userDefinedVariablesList = userDefinedVariables.entrySet();
            for (Map.Entry<String, String> userDefinedVariable : userDefinedVariablesList) {
                String variableName = userDefinedVariable.getKey();
                String value = userDefinedVariable.getValue();
                arguments.addArgument(variableName, value, "=");
            }
        } catch (Exception e) {
            log.error("Error while Adding User Defined Variables.");
            throw new Exception("Error while Adding User Defined Variables.");
        }
    }

    public Map<String, String> getTestPlanUserDefinedVariables() throws Exception {
        try {
            return testPlan.getUserDefinedVariables();
        } catch (Exception e) {
            log.error("Error while Getting User Defined Variables.");
            throw new Exception("Error while Getting User Defined Variables.");
        }
    }

    public String getTestPlanUserDefinedVariable(String variableName) throws Exception {
        try {
            Map<String, String> userDefinedVariables = testPlan.getUserDefinedVariables();
            Set<Map.Entry<String, String>> userDefinedVariablesList = userDefinedVariables.entrySet();
            for (Map.Entry<String, String> userDefinedVariable : userDefinedVariablesList) {
                String variableKey = userDefinedVariable.getKey();
                if (variableKey.equalsIgnoreCase(variableName)) {
                    return userDefinedVariable.getValue();
                }
            }
        } catch (Exception e) {
            log.error("Error while Getting User Defined Variable: " + variableName);
            throw new Exception("Error while Getting User Defined Variable: " + variableName);
        }
        return null;
    }

    public void addTestPlanParameter(String name, String value) throws Exception {
        try {
            testPlan.addParameter(name, value);
        } catch (Exception e) {
            log.error("Error while Adding Parameter: " + name + " -> " + value);
            throw new Exception("Error while Adding Parameter: " + name + " -> " + value);
        }
    }

    public void setTestPlanProperty(String name, Object value) throws Exception {
        try {
            if (value instanceof String) {
                testPlan.setProperty(name, (String) value);
            } else if (value instanceof Integer) {
                testPlan.setProperty(name, (Integer) value);
            } else if (value instanceof Boolean) {
                testPlan.setProperty(name, (Boolean) value);
            } else if (value instanceof Long) {
                testPlan.setProperty(name, (Long) value);
            } else {
                testPlan.setProperty(name, String.valueOf(value));
            }
        } catch (Exception e) {
            log.error("Error while Adding Parameter: " + name + " -> " + value);
            throw new Exception("Error while Adding Parameter: " + name + " -> " + value);
        }
    }

    public void configureUserDefinedVariable(String name, boolean enabled, HashMap<String, String> userDefinedVariables) throws Exception {
        arguments = new Arguments();
        arguments.setName(name);
        arguments.setEnabled(enabled);
        addUserDefinedVariables(userDefinedVariables);
        try {
            arguments.setProperty(TestElement.TEST_CLASS, Arguments.class.getName());
            arguments.setProperty(TestElement.GUI_CLASS, ArgumentsPanel.class.getName());
        } catch (Exception e) {
            log.error("Error while configuring User Defined Arguments.");
            throw new Exception("Error while configuring User Defined Arguments.");
        }
    }

    private void setCSVDataSetFileName(String filename) throws Exception {
        try {
            log.info("Setting up CSVDataSet filename.");
            // csvDataSet.setFilename(filename);
            csvDataSet.setProperty("filename", filename);
        } catch (Exception e) {
            log.error("Error while trying to set CSVDataSet filename.");
            throw new Exception("Error while trying to set CSVDataSet filename.");
        }
    }

    private void setCSVDataSetFileEncoding(String fileEncoding) throws Exception {
        try {
            log.info("Setting up CSVDataSet fileEncoding.");
            // csvDataSet.setFileEncoding(fileEncoding);
            csvDataSet.setProperty("fileEncoding", fileEncoding);
        } catch (Exception e) {
            log.error("Error while trying to set CSVDataSet fileEncoding.");
            throw new Exception("Error while trying to set CSVDataSet fileEncoding.");
        }
    }

    private void setCSVDataSetVariableNames(String variableNames) throws Exception {
        try {
            log.info("Setting up CSVDataSet variableNames.");
            // csvDataSet.setVariableNames(variableNames);
            csvDataSet.setProperty("variableNames", variableNames);
        } catch (Exception e) {
            log.error("Error while trying to set CSVDataSet variableNames.");
            throw new Exception("Error while trying to set CSVDataSet variableNames.");
        }
    }

    private void setCSVDataSetIgnoreFirstLine(boolean ignoreFirstLine) throws Exception {
        try {
            log.info("Setting up CSVDataSet ignoreFirstLine.");
            // csvDataSet.setIgnoreFirstLine(ignoreFirstLine);
            csvDataSet.setProperty("ignoreFirstLine", ignoreFirstLine);
        } catch (Exception e) {
            log.error("Error while trying to set CSVDataSet ignoreFirstLine.");
            throw new Exception("Error while trying to set CSVDataSet ignoreFirstLine.");
        }
    }

    private void setCSVDataSetDelimiter(String delimiter) throws Exception {
        try {
            log.info("Setting up CSVDataSet delimiter.");
            // csvDataSet.setDelimiter(delimiter);
            csvDataSet.setProperty("delimiter", delimiter);
        } catch (Exception e) {
            log.error("Error while trying to set CSVDataSet delimiter.");
            throw new Exception("Error while trying to set CSVDataSet delimiter.");
        }
    }

    private void setCSVDataSetQuotedData(boolean quotedData) throws Exception {
        try {
            log.info("Setting up CSVDataSet quotedData.");
            // csvDataSet.setQuotedData(quotedData);
            csvDataSet.setProperty("quotedData", quotedData);
        } catch (Exception e) {
            log.error("Error while trying to set CSVDataSet quotedData.");
            throw new Exception("Error while trying to set CSVDataSet quotedData.");
        }
    }

    private void setCSVDataSetRecycle(boolean recycle) throws Exception {
        try {
            log.info("Setting up CSVDataSet recycle.");
            // csvDataSet.setQuotedData(recycle);
            csvDataSet.setProperty("recycle", recycle);
        } catch (Exception e) {
            log.error("Error while trying to set CSVDataSet recycle.");
            throw new Exception("Error while trying to set CSVDataSet recycle.");
        }
    }

    private void setCSVDataSetStopThread(boolean stopThread) throws Exception {
        try {
            log.info("Setting up CSVDataSet stopThread.");
            // csvDataSet.setStopThread(stopThread);
            csvDataSet.setProperty("stopThread", stopThread);
        } catch (Exception e) {
            log.error("Error while trying to set CSVDataSet stopThread.");
            throw new Exception("Error while trying to set CSVDataSet stopThread.");
        }
    }

    private void setCSVDataSetShareMode(String shareMode) throws Exception {
        try {
            log.info("Setting up CSVDataSet shareMode.");
            // csvDataSet.setShareMode(shareMode);
            csvDataSet.setProperty("shareMode", shareMode);
        } catch (Exception e) {
            log.error("Error while trying to set CSVDataSet shareMode.");
            throw new Exception("Error while trying to set CSVDataSet shareMode.");
        }
    }

    public void configureReportGeneratorPath(String name, boolean enabled, String filename, String fileEncoding, String variableNames,
                                             boolean ignoreFirstLine, String delimiter, boolean quotedData, boolean recycle,
                                             boolean stopThread, String shareMode) throws Exception {
        try {
            csvDataSet = new CSVDataSet();
            csvDataSet.setName(name);
            csvDataSet.setEnabled(enabled);

            setCSVDataSetFileName(filename);

            if (!fileEncoding.isEmpty()) {
                setCSVDataSetFileEncoding(fileEncoding);
            }

            if (!variableNames.isEmpty()) {
                setCSVDataSetVariableNames(variableNames);
            }

            setCSVDataSetIgnoreFirstLine(ignoreFirstLine);

            if (!delimiter.isEmpty()) {
                setCSVDataSetDelimiter(delimiter);
            } else {
                setCSVDataSetDelimiter(",");
            }

            setCSVDataSetQuotedData(quotedData);
            setCSVDataSetRecycle(recycle);
            setCSVDataSetStopThread(stopThread);
            setCSVDataSetShareMode(shareMode);

            csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
            csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        } catch (Exception e) {
            log.error("Error while configuring CSV Data Set.");
            throw new Exception("Error while configuring CSV Data Set.");
        }
    }

    public void configureReportGeneratorPath(String path) throws Exception {
        try {
            JMeterUtils.setProperty(JMETER_REPORT_OUTPUT_DIR_PROPERTY, path);
        } catch (Exception e) {
            log.error("Error while configuring JMETER_REPORT_OUTPUT_DIR_PROPERTY.");
            throw new Exception("Error while configuring JMETER_REPORT_OUTPUT_DIR_PROPERTY.");
        }
    }

    public void generateHtmlReport(String logFile) throws Exception {
        try {
            ReportGenerator generator = new ReportGenerator(logFile,null);
            generator.generate();
        } catch (Exception e) {
            log.error("Error while running ReportGenerator.\t" + e.getMessage());
            throw new Exception("Error while running ReportGenerator.\t" + e.getMessage());
        }
    }

    public void configureJMeterEngine() throws Exception {
        try {
            log.info("Configuring JMeter Engine.");
            jMeterEngine.configure(testPlanTree);
        } catch (Exception e) {
            log.error("Error while Configuring JMeter Engine.\t" + e.getMessage());
            throw new Exception("Error while Configuring JMeter Engine.\t" + e.getMessage());
        }
    }

    public void runJMeterEngine() throws Exception {
        try {
            log.info("Running JMeter Engine.");
            jMeterEngine.run();

            System.out.println("Test completed. See " + jMeterConfig.getjMeterHomePath() + "/report.jtl file for results");
            System.out.println("JMeter .jmx script is available at " + jMeterConfig.getjMeterHomePath() + "/jmeter_api_sample.jmx");
            // System.exit(0);
        } catch (Exception e) {
            log.error("Error while Running JMeter Engine.");
            throw new Exception("Error while Running JMeter Engine.");
        }
    }

    public void configureBoundaryExtractor(HTTPSamplerProxy scenario, BoundaryExtractorProperties boundaryExtractorProperties) throws Exception {
        BoundaryExtractor boundaryExtractor = new BoundaryExtractor();
        try {
            log.info("Configuring Boundary Extractor: ");

            String name = boundaryExtractorProperties.getName();
            String refName = boundaryExtractorProperties.getRefName();
            String defaultValue = boundaryExtractorProperties.getDefaultValue();
            String leftBoundary = boundaryExtractorProperties.getLeftBoundary();
            String rightBoundary = boundaryExtractorProperties.getRightBoundary();
            String matchNumber = boundaryExtractorProperties.getMatchNumber();
            String enabled = boundaryExtractorProperties.getEnabled();
            HashMap stringProperties = boundaryExtractorProperties.getStringProperties();

            String testPlanName = boundaryExtractorProperties.getTestPlanName();
            String httpSamplerProxyName = boundaryExtractorProperties.getHttpSamplerProxyName();

            log.info("Boundary Extractor Name: " + name);
            log.info("Boundary Extractor Test Plan Name: " + testPlanName);
            log.info("Boundary Extractor Http Sampler Proxy Name: " + httpSamplerProxyName);

            boundaryExtractor.setProperty(TestElement.GUI_CLASS, BoundaryExtractorGui.class.getName());
            boundaryExtractor.setProperty(TestElement.TEST_CLASS, BoundaryExtractor.class.getName());

            if (!name.isEmpty()) {
                boundaryExtractor.setName(name);
            } else {
                boundaryExtractor.setName("Boundary Extractor");
            }

            if (!refName.isEmpty()) {
                boundaryExtractor.setRefName(refName);
            }

            if (!defaultValue.isEmpty()) {
                boundaryExtractor.setDefaultValue(defaultValue);
            } else {
                boundaryExtractor.setDefaultValue("");
            }

            if (!leftBoundary.isEmpty()) {
                boundaryExtractor.setLeftBoundary(leftBoundary);
            }

            if (!rightBoundary.isEmpty()) {
                boundaryExtractor.setRightBoundary(rightBoundary);
            }

            if (!matchNumber.isEmpty()) {
                boundaryExtractor.setMatchNumber(matchNumber);
            } else {
                boundaryExtractor.setMatchNumber("1");
            }

            if (!enabled.isEmpty()) {
                if (enabled.equalsIgnoreCase("true")) {
                    boundaryExtractor.setEnabled(true);
                } else {
                    boundaryExtractor.setEnabled(false);
                }
            }

            if (stringProperties != null) {
                Set stringPropertiesKeySet = stringProperties.keySet();

                for (Object stringPropertyKey : stringPropertiesKeySet) {
                    String stringPropertyValue = (String) stringProperties.get(stringPropertyKey);
                    boundaryExtractor.setProperty((String) stringPropertyKey, stringPropertyValue);
                }
            }

            threadGroupHashTree.add(scenario, boundaryExtractor);
        } catch (Exception e) {
            log.error("Error while Configuring Boundary Extractor.");
            throw new Exception("Error while Configuring Boundary Extractor.");
        }
    }

    public void addBoundaryExtractor(BoundaryExtractorWrapper boundaryExtractorWrapper) throws Exception {
        try {
            boundaryExtractorWrappers.add(boundaryExtractorWrapper);
        } catch (Exception e) {
            log.error("Error while trying to Add BoundaryExtractorWrapper. " + e.getMessage());
            throw new Exception("Error while trying to Add BoundaryExtractorWrapper. " + e.getMessage());
        }
    }

    public void loadBoundaryExtractors() throws Exception {
        try {
            for (BoundaryExtractorWrapper boundaryExtractorWrapper : boundaryExtractorWrappers) {
                HTTPSamplerProxy httpSamplerProxyScenario = boundaryExtractorWrapper.getHttpSamplerProxyScenario();
                BoundaryExtractorProperties boundaryExtractorProperties = boundaryExtractorWrapper.getBoundaryExtractorProperties();
                configureBoundaryExtractor(httpSamplerProxyScenario, boundaryExtractorProperties);
            }
        } catch (Exception e) {
            log.error("Error while adding scenario Boundary Extractors. " + e.getMessage());
            throw  new Exception("Error while adding scenario Boundary Extractor. " + e.getMessage());
        }
    }


}
