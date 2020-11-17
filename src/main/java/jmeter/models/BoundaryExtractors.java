package jmeter.models;

import java.util.ArrayList;
import java.util.List;

public class BoundaryExtractors {
    private List<BoundaryExtractorProperties> boundaryExtractorProperties = new ArrayList<>();

    public BoundaryExtractors() {
    }

    public void addBoundaryExtractor(BoundaryExtractorProperties boundaryExtractorProperties) {
        this.boundaryExtractorProperties.add(boundaryExtractorProperties);
    }

    public BoundaryExtractorProperties getBoundaryExtractors(int i) {
        return boundaryExtractorProperties.get(i);
    }

    public List<BoundaryExtractorProperties> getBoundaryExtractorProperties() {
        return boundaryExtractorProperties;
    }
}
