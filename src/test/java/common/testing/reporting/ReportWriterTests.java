package common.testing.reporting;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

public class ReportWriterTests {
    @Test
    public void reportWriterTest() {
        try {
            String[] reportableFields = {"Reportable Field One", "Reportable Field Two", "Reportable Field Three"};
            ReportWriter reportWriter = new ReportWriter(reportableFields);
            reportWriter.createReport("Test Report");

            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Reportable Field One"), "Test One", TestResultReportFlag.DEFAULT);
            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Reportable Field Two"), "Test Two", TestResultReportFlag.DEFAULT);
            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Reportable Field Three"), "Test Three", TestResultReportFlag.DEFAULT);
            reportWriter.increamentReportRowIndex();

            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Reportable Field One"), "Test One", TestResultReportFlag.DEFAULT);
            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Reportable Field Two"), "Test Two", TestResultReportFlag.DEFAULT);
            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Reportable Field Three"), "Test Three", TestResultReportFlag.DEFAULT);
            reportWriter.increamentReportRowIndex();

            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Reportable Field One"), "Test One", TestResultReportFlag.DEFAULT);
            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Reportable Field Two"), "Test Two", TestResultReportFlag.DEFAULT);
            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Reportable Field Three"), "Test Three", TestResultReportFlag.DEFAULT);
            reportWriter.increamentReportRowIndex();

            reportWriter.saveReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
