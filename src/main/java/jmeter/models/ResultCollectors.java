package jmeter.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ResultCollectors {
    private Logger log  = LogManager.getLogger(ResultCollectors.class);
    private List<TestLogger> loggerList = new ArrayList<>();

    public ResultCollectors() {
    }

    public ResultCollectors(List<TestLogger> loggerList) {
        this.loggerList = loggerList;
    }

    public List<TestLogger> getLoggerList() {
        return loggerList;
    }

    public void setLoggerList(List<TestLogger> loggerList) {
        this.loggerList = loggerList;
    }

    public void addTestLogger(TestLogger testLogger) throws Exception {
        try {
            loggerList.add(testLogger);
        } catch (Exception e) {
            log.error("Error while adding user defined variable:\t" + e.getMessage());
            throw new Exception("Error while adding user defined variable:\t" + e.getMessage());
        }
    }

}
