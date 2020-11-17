package jmeter.models;

import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;

public class BoundaryExtractorWrapper {
    private HTTPSamplerProxy httpSamplerProxyScenario;
    private BoundaryExtractorProperties boundaryExtractorProperties;

    public BoundaryExtractorWrapper(HTTPSamplerProxy httpSamplerProxyScenario, BoundaryExtractorProperties boundaryExtractorProperties) {
        this.httpSamplerProxyScenario = httpSamplerProxyScenario;
        this.boundaryExtractorProperties = boundaryExtractorProperties;
    }

    public HTTPSamplerProxy getHttpSamplerProxyScenario() {
        return httpSamplerProxyScenario;
    }

    public BoundaryExtractorProperties getBoundaryExtractorProperties() {
        return boundaryExtractorProperties;
    }
}
