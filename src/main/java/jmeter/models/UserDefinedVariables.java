package jmeter.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class UserDefinedVariables {
    private Logger log  = LogManager.getLogger(UserDefinedVariables.class);
    private HashMap variableList = new HashMap<>();

    public UserDefinedVariables() {
    }

    public UserDefinedVariables(HashMap variableList) {
        this.variableList = variableList;
    }

    public HashMap getVariableList() {
        return variableList;
    }

    public void setVariableList(HashMap variableList) {
        this.variableList = variableList;
    }

    public void addVariable (String name, String value) throws Exception {
        try {
            variableList.put(name, value);
        } catch (Exception e) {
            log.error("Error while adding user defined variable: " + name + " -> " + value + "\t" + e.getMessage());
            throw new Exception("Error while adding user defined variable: " + name + " -> " + value + "\t" + e.getMessage());
        }
    }

}
