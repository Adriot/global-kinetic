package common.testing;

import cucumber.api.DataTable;
import files.transform.ExcelToDataTable;
import gherkin.formatter.model.Row;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a @TestCase constructor for a Test Case with all its @Scenario's.
 * @dataTable is the data that we need for the entire Test Case.
 * @dataTable will be used load all the scenarios for the Test Case.
 * @workBookPath is a path to the targeted work book (excel)
 * @dataSheetName is the name of the target sheet from the work book.
 * @scenarioFlagField is a field that is used to determine whether a
 * scenario (row on the data sheet, or result set from the Database)
 * Should be added to the list of scenarios for an instance of the Test Case.
 * @reportableFields is the list of reportable field (fields to include on the data sheet).
 */

public class TestCase {
    private Logger log  = LogManager.getLogger(TestCase.class);
    private String testCaseName;
    private String testDataConfigFilePath;
    private DataTable dataTable;
    private Object[] headers;
    private List<Scenario> scenarios;
    private String workBookPath;
    private String dataSheetName;
    private String scenarioFlagField;
    private String reportableFields;

    public TestCase(String testCaseName, String testDataConfigFilePath, String workBookPath, String dataSheetName, String scenarioFlagField, String reportableFields) {
        this.testCaseName = testCaseName;
        this.testDataConfigFilePath = testDataConfigFilePath;
        this.workBookPath = workBookPath;
        this.dataSheetName = dataSheetName;
        this.scenarioFlagField = scenarioFlagField;
        this.reportableFields = reportableFields;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public String getTestDataConfigFilePath() {
        return testDataConfigFilePath;
    }

    public void setTestDataConfigFilePath(String testDataConfigFilePath) {
        this.testDataConfigFilePath = testDataConfigFilePath;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public String getWorkBookPath() {
        return workBookPath;
    }

    public void setWorkBookPath(String workBookPath) {
        this.workBookPath = workBookPath;
    }

    public String getDataSheetName() {
        return dataSheetName;
    }

    public void setDataSheetName(String dataSheetName) {
        this.dataSheetName = dataSheetName;
    }

    public String getScenarioFlagField() {
        return scenarioFlagField;
    }

    public void setScenarioFlagField(String scenarioFlagField) {
        this.scenarioFlagField = scenarioFlagField;
    }

    public String getReportableFields() {
        return reportableFields;
    }

    public void setReportableFields(String reportableFields) {
        this.reportableFields = reportableFields;
    }

    public void loadDataExcel(String workBookPath, String dataSheetName) throws Exception {
        this.workBookPath = workBookPath;
        this.dataSheetName = dataSheetName;
        loadDataExcel();
    }

    public void loadDataExcel() throws Exception {
        try {
            ExcelToDataTable excelToDataTable = new ExcelToDataTable();
            excelToDataTable.setupFilePropertiesConfig(testDataConfigFilePath);
            dataTable = excelToDataTable.transform(workBookPath + "," + dataSheetName);
        } catch (Exception e) {
            log.error("Error while loading data from an Excel file: " + e.getMessage());
            throw new Exception("Error while loading data from an Excel file: " + e.getMessage());
        }
    }

    public void loadTestCaseScenarios(String scenarioFlagField) throws Exception {
        this.scenarioFlagField = scenarioFlagField;
        loadTestCaseScenarios();
    }

    public void loadTestCaseScenarios() throws Exception {
        try {
            if (dataTable != null) {
                scenarios = new ArrayList<>();
                headers = dataTable.getGherkinRows().get(0).getCells().toArray();

                boolean gotScenarioFlagField = Arrays.asList(headers).contains(scenarioFlagField);
                if (gotScenarioFlagField) {
                    Scenario scenario;
                    List<String> dataEntry;
                    for (Row row : dataTable.getGherkinRows()) {
                        if (row.getLine() != 1) {
                            dataEntry = row.getCells();

                            String scenarioFlagFieldValue = getCellValue(headers, dataEntry, scenarioFlagField);
                            /**
                             * Please note that this is designed in a way that if @scenarioFlagFieldValue is empty
                             * then we exclude the scenario from the test case, meaning we don't add it to the list.
                             */
                            if (!scenarioFlagFieldValue.isEmpty()) {
                                scenario = new Scenario(headers, dataEntry);
                                scenarios.add(scenario);
                            }
                        }
                    }
                } else {
                    log.error("ScenarioFlagField: " + scenarioFlagField + " -> No Scenario Flagging Field Was Found. Please kindly set the Scenario Flag Field.");
                    throw new Exception("ScenarioFlagField: " + scenarioFlagField + " -> No Scenario Flagging Field Was Found. Please kindly set the Scenario Flag Field.");
                }
            } else {
                log.error("No Data Loaded On Data Table. i.e., Data Table may be null.");
                throw new Exception("No Data Loaded On Data Table. i.e., Data Table may be null.");
            }
        } catch (Exception e) {
            log.error("Error while loading test case scenarios: " + e.getMessage());
            throw new Exception("Error while loading test case scenarios: " + e.getMessage());
        }
    }

    public Object[][] getScenarioDataProvider() throws Exception {
        try {
            if (scenarios != null) {
                int size = scenarios.size();
                Object[][] scenariosBatch = new Object[size][1];

                int index = 0;
                for (Scenario scenario : scenarios) {
                    scenariosBatch[index++][0] = scenario;
                }
                return scenariosBatch;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Error while getting scenario data provider. " + e.getMessage());
            throw new Exception("Error while getting scenario data provider. " + e.getMessage());
        }
    }

    private int getHeaderIndex(Object[] headers, String header) {
        return Arrays.asList(headers).indexOf(header);
    }

    private String getCellValue(Object[] headers, List<String> dataEntry, String field) {
        return getHeaderIndex(headers, field) != -1 ? dataEntry.get(getHeaderIndex(headers, field)) : "";
    }
}
