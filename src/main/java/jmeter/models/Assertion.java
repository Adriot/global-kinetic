package jmeter.models;

public class Assertion {
    private String assertionName;
    private String assertionType;
    private String assertionTestString;
    private String assertionTestField;

    public Assertion(String assertionName, String assertionType, String assertionTestString, String assertionTestField) {
        this.assertionName = assertionName;
        this.assertionType = assertionType;
        this.assertionTestString = assertionTestString;
        this.assertionTestField = assertionTestField;
    }

    public String getAssertionName() {
        return assertionName;
    }

    public String getAssertionType() {
        return assertionType;
    }

    public String getAssertionTestString() {
        return assertionTestString;
    }

    public String getAssertionTestField() {
        return assertionTestField;
    }
}
