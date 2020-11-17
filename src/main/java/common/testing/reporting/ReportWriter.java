package common.testing.reporting;

import files.FilePropertiesConfig;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Properties;

import static files.Config.reportSourcePath;

public class ReportWriter {
    private HashMap<String, Integer> reportFields;
    private String[] reportableFields;
    private Report report;
    private int reportRowIndex = 0;
    private String reportPath;
    private String screenShotFileName;

    public ReportWriter() {
    }

    public ReportWriter(String[] reportableFields) {
        this.reportableFields = reportableFields;
        setReportFields();
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public HashMap<String, Integer> getReportFields() {
        return reportFields;
    }

    public void setReportFields() {
        Integer index = 0;
        reportFields = new HashMap<>();

        for (String field : reportableFields) {
            reportFields.put(field, index++);
        }
    }

    /**
     * This is meant to create all the required reportable fields
     * @param reportableFields
     */
    public void setReportFields(String[] reportableFields) {
        Integer index = 0;
        reportFields = new HashMap<>();

        for (String field : reportableFields) {
            reportFields.put(field, index++);
        }
    }

    public String getScreenShotFileName() {
        return screenShotFileName;
    }

    public void setScreenShotFileName(String screenShotFileName) {
        this.screenShotFileName = screenShotFileName;
    }

    public void createReport() {
        FilePropertiesConfig propertiesSetup = new FilePropertiesConfig();
        propertiesSetup.loadProperties();
        Properties properties = propertiesSetup.getProperties();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String systemTime = format.format(now);
        setReportPath(reportSourcePath + "/" + systemTime + " " + properties.getProperty("FUNCTION_NAME") + " " + properties.getProperty("PRODUCT_NAME") + ".xlsx");
        report = new Report(reportPath, "Test Results", reportFields);
        report.createReportWorkbook();
    }

    public void createReport(String specificFileName) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String systemTime = format.format(now);
        setReportPath(reportSourcePath + "/" + systemTime + " " + specificFileName + ".xlsx");
        report = new Report(reportPath, "Test Results", reportFields);
        report.createReportWorkbook();
    }

    public void writeToReport(int fieldIndex, String value, TestResultReportFlag reportFlag) {
        report.write(fieldIndex, value, reportFlag);
    }

    public void increamentReportRowIndex() {
        reportRowIndex = report.getReportRowIndex() + 1;
        report.setReportRowIndex(reportRowIndex);
    }

    public void saveReport() throws IOException {
        report.writeReportFile();
    }

}
