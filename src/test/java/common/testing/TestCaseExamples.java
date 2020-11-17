package common.testing;

import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCaseExamples {
    private static TestCase testCase;

    @BeforeClass
    public static void setup() {
        testCase = new TestCase("Automation Test Case",
                "src\\test\\resources\\data_config.properties",
                "TEST_DATA_WORKBOOK", "TEST_DATA_SHEET", "Test Case No",
                "Field 1,Field 2,Field 3,Field 4,Field 5");

        try {
            testCase.loadDataExcel();
            testCase.loadTestCaseScenarios();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DataProvider(name = "Excel")
    public Object[][] getTestCaseScenarios() {
        try {
            if (testCase != null) {
                Object[][] scenarioDataProvider = testCase.getScenarioDataProvider();
                return scenarioDataProvider;
            } else {
                throw new Exception("No Test Cases Loaded. ie., Test Case Is Null.");
            }
        } catch (Exception e) {
            // TODO: Handle Exception
            e.printStackTrace();
        }
        return null;
    }

    @Description("Test Case Example:")
    @Test(dataProvider = "Excel")
    public void scenarioTests(Scenario scenario) {
        System.out.println("Test Case No: " + scenario.getCellValue("Test Case No") + ", Scenario Description: " + scenario.getCellValue("Scenario Description"));
    }
}
