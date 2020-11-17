package jmeter.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class TestPlanSet {
    private Logger log  = LogManager.getLogger(TestPlanSet.class);
    private UserDefinedVariables userDefinedVariables;
    private List<TestPlanConfig> testPlanList = new LinkedList<>();
    private ResultCollectors resultCollectors;

    public TestPlanSet() {
    }

    public TestPlanSet(UserDefinedVariables userDefinedVariables, List<TestPlanConfig> testPlanList, ResultCollectors resultCollectors) {
        this.userDefinedVariables = userDefinedVariables;
        this.testPlanList = testPlanList;
        this.resultCollectors = resultCollectors;
    }

    public UserDefinedVariables getUserDefinedVariables() {
        return userDefinedVariables;
    }

    public void setUserDefinedVariables(UserDefinedVariables userDefinedVariables) {
        this.userDefinedVariables = userDefinedVariables;
    }

    public List<TestPlanConfig> getTestPlanList() {
        return testPlanList;
    }

    public void setTestPlanList(List<TestPlanConfig> testPlanList) {
        this.testPlanList = testPlanList;
    }

    public ResultCollectors getResultCollectors() {
        return resultCollectors;
    }

    public void setResultCollectors(ResultCollectors resultCollectors) {
        this.resultCollectors = resultCollectors;
    }

    public void addTestPlanConfig(TestPlanConfig testPlanConfig) throws Exception {
        try {
            testPlanList.add(testPlanConfig);
        } catch (Exception e) {
            log.error("Error while adding Test Plan Config: " + testPlanConfig.getTestPlanName());
            throw new Exception("Error while adding Test Plan Config: " + testPlanConfig.getTestPlanName());
        }
    }

}
