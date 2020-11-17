package common.testing.reporting;

import files.ExcelWriter;

import java.io.IOException;
import java.util.HashMap;

public class Report {
    private String fileName;
    private String sheetName;
    private ExcelWriter excelWriter;
    private HashMap<String, Integer> headerIndexMap;
    private Integer reportRowIndex = 0;

    public Report() {
    }

    public Report(HashMap<String, Integer> headerIndexMap) {
        this.headerIndexMap = headerIndexMap;
    }

    public Report(ExcelWriter excelWriter) {
        this.excelWriter = excelWriter;
    }

    public Report(ExcelWriter excelWriter, HashMap<String, Integer> headerIndexMap) {
        this.excelWriter = excelWriter;
        this.headerIndexMap = headerIndexMap;
    }

    public Report(String fileName, String sheetName) {
        this.fileName = fileName;
        this.sheetName = sheetName;
    }

    public Report(String fileName, String sheetName, HashMap<String, Integer> headerIndexMap) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.headerIndexMap = headerIndexMap;
    }

    public Report(String fileName, String sheetName, ExcelWriter excelWriter) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.excelWriter = excelWriter;
    }

    public Report(String fileName, String sheetName, ExcelWriter excelWriter, HashMap<String, Integer> headerIndexMap) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.excelWriter = excelWriter;
        this.headerIndexMap = headerIndexMap;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public ExcelWriter getExcelWriter() {
        return excelWriter;
    }

    public void setExcelWriter(ExcelWriter excelWriter) {
        this.excelWriter = excelWriter;
    }

    public Integer getReportRowIndex() {
        return reportRowIndex;
    }

    public void setReportRowIndex(Integer reportRowIndex) {
        this.reportRowIndex = reportRowIndex;
    }

    public void createReportWorkbook() {
        if (excelWriter == null) {
            excelWriter = new ExcelWriter(fileName, sheetName);
        }
        excelWriter.createWorkbook();

        for (String field : headerIndexMap.keySet()) {
            excelWriter.writeHeardersToSheet(reportRowIndex, headerIndexMap.get(field), field);
        }
        excelWriter.adjustColumnSizes();
        reportRowIndex++;
    }

    public void write(int fieldIndex, String value, TestResultReportFlag testResultReportFlag) {
        switch (testResultReportFlag) {
            case SUCCESS:
                excelWriter.writeSuccessToSheet(reportRowIndex, fieldIndex, value);
                break;
            case WARNING:
                excelWriter.writeWarningToSheet(reportRowIndex, fieldIndex, value);
                break;
            case FAIL:
                excelWriter.writeErrorToSheet(reportRowIndex, fieldIndex, value);
                break;
            default:
                excelWriter.writeToSheet(reportRowIndex, fieldIndex, value);
                break;
        }
    }

    public void writeReportFile() throws IOException {
        if (excelWriter != null) {
            excelWriter.createFile();
        }
    }
}
