package jmeter.models;

public class TestLogger {
    private String name;
    private String fileName;
    private String testClass;
    private String guiClass;

    public TestLogger(String name, String fileName, String testClass, String guiClass) {
        this.name = name;
        this.fileName = fileName;
        this.testClass = testClass;
        this.guiClass = guiClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public String getGuiClass() {
        return guiClass;
    }

    public void setGuiClass(String guiClass) {
        this.guiClass = guiClass;
    }

}
