package jmeter.models;

import java.util.HashMap;

public class BoundaryExtractorProperties {
    private String testPlanName;
    private String httpSamplerProxyName;
    private String name;
    private String enabled;
    private String refName;
    private String leftBoundary;
    private String rightBoundary;
    private String matchNumber;
    private String defaultValue;
    private HashMap stringProperties;

    public BoundaryExtractorProperties(String testPlanName, String httpSamplerProxyName, String name, String enabled, String refName, String leftBoundary, String rightBoundary, String matchNumber, String defaultValue, HashMap stringProperties) {
        this.testPlanName = testPlanName;
        this.httpSamplerProxyName = httpSamplerProxyName;
        this.name = name;
        this.enabled = enabled;
        this.refName = refName;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.matchNumber = matchNumber;
        this.defaultValue = defaultValue;
        this.stringProperties = stringProperties;
    }

    public String getTestPlanName() {
        return testPlanName;
    }

    public String getHttpSamplerProxyName() {
        return httpSamplerProxyName;
    }

    public String getName() {
        return name;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getRefName() {
        return refName;
    }

    public String getLeftBoundary() {
        return leftBoundary;
    }

    public String getRightBoundary() {
        return rightBoundary;
    }

    public String getMatchNumber() {
        return matchNumber;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public HashMap getStringProperties() {
        return stringProperties;
    }
}
