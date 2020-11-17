package jmeter.models;

public class TestPlanConfig {
    private String testPlanName;
    private int loopControllerLoops;
    private boolean loopControllerFirst;
    private String threadGroupName;
    private int threadGroupNumberOfThreads;
    private int threadGroupRampUp;

    private String threadGroupOnSampleError;
    private String threadGroupOnRampTime;
    private String threadGroupScheduler;
    private String threadGroupDuration;
    private String threadGroupDelay;
    private String threadGroupSameUserOnNextIteration;

    private String httpSamplerProxyName;
    private String httpSamplerProxyDomain;
    private String httpSamplerProxyPort;
    private String httpSamplerProxyPath;
    private String httpSamplerProxyMethod;
    private String httpSamplerProxyProtocol;
    private String httpSamplerProxyNonEncodedArgumentName;
    private String httpSamplerProxyNonEncodedArgumentValue;
    private String httpSamplerProxyNonEncodedArgumentMetaData;

    private String csvDataSetName;
    private boolean csvDataSetEnabled;
    private String csvDataSetFileName;
    private String csvDataSetFileEncoding;
    private String csvDataSetVariableNames;
    private boolean csvDataSetIgnoreFirstLine;
    private String csvDataSetDelimiter;
    private boolean csvDataSetQuotedData;
    private boolean csvDataSetRecycle;
    private boolean csvDataSetStopThread;
    private String csvDataSetShareMode;

    private Assertion assertion;
    private BoundaryExtractors boundaryExtractors;

    public TestPlanConfig() {
    }

    public TestPlanConfig(String testPlanName, int loopControllerLoops, boolean loopControllerFirst, String threadGroupName,
                          int threadGroupNumberOfThreads, int threadGroupRampUp,
                          String threadGroupOnSampleError, String threadGroupOnRampTime, String threadGroupScheduler, String threadGroupDuration, String threadGroupDelay, String threadGroupSameUserOnNextIteration,
                          String httpSamplerProxyName, String httpSamplerProxyDomain, String httpSamplerProxyPort, String httpSamplerProxyPath,
                          String httpSamplerProxyMethod, String httpSamplerProxyProtocol, String httpSamplerProxyNonEncodedArgumentName,
                          String httpSamplerProxyNonEncodedArgumentValue, String httpSamplerProxyNonEncodedArgumentMetaData,
                          String csvDataSetName, boolean csvDataSetEnabled, String csvDataSetFileName, String csvDataSetFileEncoding,
                          String csvDataSetVariableNames, boolean csvDataSetIgnoreFirstLine, String csvDataSetDelimiter,
                          boolean csvDataSetQuotedData, boolean csvDataSetRecycle, boolean csvDataSetStopThread,
                          String csvDataSetShareMode, BoundaryExtractors boundaryExtractors) {
        this.testPlanName = testPlanName;
        this.loopControllerLoops = loopControllerLoops;
        this.loopControllerFirst = loopControllerFirst;
        this.threadGroupName = threadGroupName;
        this.threadGroupNumberOfThreads = threadGroupNumberOfThreads;
        this.threadGroupRampUp = threadGroupRampUp;

        this.threadGroupOnSampleError= threadGroupOnSampleError;
        this.threadGroupOnRampTime= threadGroupOnRampTime;
        this.threadGroupScheduler= threadGroupScheduler;
        this.threadGroupDuration= threadGroupDuration;
        this.threadGroupDelay= threadGroupDelay;
        this.threadGroupSameUserOnNextIteration= threadGroupSameUserOnNextIteration;

        this.httpSamplerProxyName = httpSamplerProxyName;
        this.httpSamplerProxyDomain = httpSamplerProxyDomain;
        this.httpSamplerProxyPort = httpSamplerProxyPort;
        this.httpSamplerProxyPath = httpSamplerProxyPath;
        this.httpSamplerProxyMethod = httpSamplerProxyMethod;
        this.httpSamplerProxyProtocol = httpSamplerProxyProtocol;
        this.httpSamplerProxyNonEncodedArgumentName = httpSamplerProxyNonEncodedArgumentName;
        this.httpSamplerProxyNonEncodedArgumentValue = httpSamplerProxyNonEncodedArgumentValue;
        this.httpSamplerProxyNonEncodedArgumentMetaData = httpSamplerProxyNonEncodedArgumentMetaData;
        this.csvDataSetName = csvDataSetName;
        this.csvDataSetEnabled = csvDataSetEnabled;
        this.csvDataSetFileName = csvDataSetFileName;
        this.csvDataSetFileEncoding = csvDataSetFileEncoding;
        this.csvDataSetVariableNames = csvDataSetVariableNames;
        this.csvDataSetIgnoreFirstLine = csvDataSetIgnoreFirstLine;
        this.csvDataSetDelimiter = csvDataSetDelimiter;
        this.csvDataSetQuotedData = csvDataSetQuotedData;
        this.csvDataSetRecycle = csvDataSetRecycle;
        this.csvDataSetStopThread = csvDataSetStopThread;
        this.csvDataSetShareMode = csvDataSetShareMode;
        this.boundaryExtractors = boundaryExtractors;
    }

    public String getTestPlanName() {
        return testPlanName;
    }

    public void setTestPlanName(String testPlanName) {
        this.testPlanName = testPlanName;
    }

    public int getLoopControllerLoops() {
        return loopControllerLoops;
    }

    public void setLoopControllerLoops(int loopControllerLoops) {
        this.loopControllerLoops = loopControllerLoops;
    }

    public boolean isLoopControllerFirst() {
        return loopControllerFirst;
    }

    public void setLoopControllerFirst(boolean loopControllerFirst) {
        this.loopControllerFirst = loopControllerFirst;
    }

    public String getThreadGroupName() {
        return threadGroupName;
    }

    public void setThreadGroupName(String threadGroupName) {
        this.threadGroupName = threadGroupName;
    }

    public int getThreadGroupNumberOfThreads() {
        return threadGroupNumberOfThreads;
    }

    public void setThreadGroupNumberOfThreads(int threadGroupNumberOfThreads) {
        this.threadGroupNumberOfThreads = threadGroupNumberOfThreads;
    }

    public int getThreadGroupRampUp() {
        return threadGroupRampUp;
    }

    public void setThreadGroupRampUp(int threadGroupRampUp) {
        this.threadGroupRampUp = threadGroupRampUp;
    }

    public String getThreadGroupOnSampleError() {
        return threadGroupOnSampleError;
    }

    public void setThreadGroupOnSampleError(String threadGroupOnSampleError) {
        this.threadGroupOnSampleError = threadGroupOnSampleError;
    }

    public String getThreadGroupOnRampTime() {
        return threadGroupOnRampTime;
    }

    public void setThreadGroupOnRampTime(String threadGroupOnRampTime) {
        this.threadGroupOnRampTime = threadGroupOnRampTime;
    }

    public String getThreadGroupScheduler() {
        return threadGroupScheduler;
    }

    public void setThreadGroupScheduler(String threadGroupScheduler) {
        this.threadGroupScheduler = threadGroupScheduler;
    }

    public String getThreadGroupDuration() {
        return threadGroupDuration;
    }

    public void setThreadGroupDuration(String threadGroupDuration) {
        this.threadGroupDuration = threadGroupDuration;
    }

    public String getThreadGroupDelay() {
        return threadGroupDelay;
    }

    public void setThreadGroupDelay(String threadGroupDelay) {
        this.threadGroupDelay = threadGroupDelay;
    }

    public String getThreadGroupSameUserOnNextIteration() {
        return threadGroupSameUserOnNextIteration;
    }

    public void setThreadGroupSameUserOnNextIteration(String threadGroupSameUserOnNextIteration) {
        this.threadGroupSameUserOnNextIteration = threadGroupSameUserOnNextIteration;
    }

    public String getHttpSamplerProxyName() {
        return httpSamplerProxyName;
    }

    public void setHttpSamplerProxyName(String httpSamplerProxyName) {
        this.httpSamplerProxyName = httpSamplerProxyName;
    }

    public String getHttpSamplerProxyDomain() {
        return httpSamplerProxyDomain;
    }

    public void setHttpSamplerProxyDomain(String httpSamplerProxyDomain) {
        this.httpSamplerProxyDomain = httpSamplerProxyDomain;
    }

    public String getHttpSamplerProxyPort() {
        return httpSamplerProxyPort;
    }

    public void setHttpSamplerProxyPort(String httpSamplerProxyPort) {
        this.httpSamplerProxyPort = httpSamplerProxyPort;
    }

    public String getHttpSamplerProxyPath() {
        return httpSamplerProxyPath;
    }

    public void setHttpSamplerProxyPath(String httpSamplerProxyPath) {
        this.httpSamplerProxyPath = httpSamplerProxyPath;
    }

    public String getHttpSamplerProxyMethod() {
        return httpSamplerProxyMethod;
    }

    public void setHttpSamplerProxyMethod(String httpSamplerProxyMethod) {
        this.httpSamplerProxyMethod = httpSamplerProxyMethod;
    }

    public String getHttpSamplerProxyProtocol() {
        return httpSamplerProxyProtocol;
    }

    public void setHttpSamplerProxyProtocol(String httpSamplerProxyProtocol) {
        this.httpSamplerProxyProtocol = httpSamplerProxyProtocol;
    }

    public String getHttpSamplerProxyNonEncodedArgumentName() {
        return httpSamplerProxyNonEncodedArgumentName;
    }

    public void setHttpSamplerProxyNonEncodedArgumentName(String httpSamplerProxyNonEncodedArgumentName) {
        this.httpSamplerProxyNonEncodedArgumentName = httpSamplerProxyNonEncodedArgumentName;
    }

    public String getHttpSamplerProxyNonEncodedArgumentValue() {
        return httpSamplerProxyNonEncodedArgumentValue;
    }

    public void setHttpSamplerProxyNonEncodedArgumentValue(String httpSamplerProxyNonEncodedArgumentValue) {
        this.httpSamplerProxyNonEncodedArgumentValue = httpSamplerProxyNonEncodedArgumentValue;
    }

    public String getHttpSamplerProxyNonEncodedArgumentMetaData() {
        return httpSamplerProxyNonEncodedArgumentMetaData;
    }

    public void setHttpSamplerProxyNonEncodedArgumentMetaData(String httpSamplerProxyNonEncodedArgumentMetaData) {
        this.httpSamplerProxyNonEncodedArgumentMetaData = httpSamplerProxyNonEncodedArgumentMetaData;
    }

    public String getCsvDataSetName() {
        return csvDataSetName;
    }

    public void setCsvDataSetName(String csvDataSetName) {
        this.csvDataSetName = csvDataSetName;
    }

    public boolean isCsvDataSetEnabled() {
        return csvDataSetEnabled;
    }

    public void setCsvDataSetEnabled(boolean csvDataSetEnabled) {
        this.csvDataSetEnabled = csvDataSetEnabled;
    }

    public String getCsvDataSetFileName() {
        return csvDataSetFileName;
    }

    public void setCsvDataSetFileName(String csvDataSetFileName) {
        this.csvDataSetFileName = csvDataSetFileName;
    }

    public String getCsvDataSetFileEncoding() {
        return csvDataSetFileEncoding;
    }

    public void setCsvDataSetFileEncoding(String csvDataSetFileEncoding) {
        this.csvDataSetFileEncoding = csvDataSetFileEncoding;
    }

    public String getCsvDataSetVariableNames() {
        return csvDataSetVariableNames;
    }

    public void setCsvDataSetVariableNames(String csvDataSetVariableNames) {
        this.csvDataSetVariableNames = csvDataSetVariableNames;
    }

    public boolean isCsvDataSetIgnoreFirstLine() {
        return csvDataSetIgnoreFirstLine;
    }

    public void setCsvDataSetIgnoreFirstLine(boolean csvDataSetIgnoreFirstLine) {
        this.csvDataSetIgnoreFirstLine = csvDataSetIgnoreFirstLine;
    }

    public String getCsvDataSetDelimiter() {
        return csvDataSetDelimiter;
    }

    public void setCsvDataSetDelimiter(String csvDataSetDelimiter) {
        this.csvDataSetDelimiter = csvDataSetDelimiter;
    }

    public boolean isCsvDataSetQuotedData() {
        return csvDataSetQuotedData;
    }

    public void setCsvDataSetQuotedData(boolean csvDataSetQuotedData) {
        this.csvDataSetQuotedData = csvDataSetQuotedData;
    }

    public boolean isCsvDataSetRecycle() {
        return csvDataSetRecycle;
    }

    public void setCsvDataSetRecycle(boolean csvDataSetRecycle) {
        this.csvDataSetRecycle = csvDataSetRecycle;
    }

    public boolean isCsvDataSetStopThread() {
        return csvDataSetStopThread;
    }

    public void setCsvDataSetStopThread(boolean csvDataSetStopThread) {
        this.csvDataSetStopThread = csvDataSetStopThread;
    }

    public String getCsvDataSetShareMode() {
        return csvDataSetShareMode;
    }

    public void setCsvDataSetShareMode(String csvDataSetShareMode) {
        this.csvDataSetShareMode = csvDataSetShareMode;
    }

    public Assertion getAssertion() {
        return assertion;
    }

    public void setAssertion(Assertion assertion) {
        this.assertion = assertion;
    }

    public BoundaryExtractors getBoundaryExtractors() {
        return boundaryExtractors;
    }

    public void setBoundaryExtractors(BoundaryExtractors boundaryExtractors) {
        this.boundaryExtractors = boundaryExtractors;
    }
}
