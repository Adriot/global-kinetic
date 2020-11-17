package jmeter.models;

import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;

public class ResponseAssertionWrapper {
    private HTTPSamplerProxy httpSamplerProxyScenario;
    private String name;
    private String type;
    private String testString;
    private String testField;

    public ResponseAssertionWrapper(HTTPSamplerProxy httpSamplerProxyScenario, String name, String type, String testString, String testField) {
        this.httpSamplerProxyScenario = httpSamplerProxyScenario;
        this.name = name;
        this.type = type;
        this.testString = testString;
        this.testField = testField;
    }

    public HTTPSamplerProxy getHttpSamplerProxyScenario() {
        return httpSamplerProxyScenario;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTestString() {
        return testString;
    }

    public String getTestField() {
        return testField;
    }
}
