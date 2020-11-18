package web_automation;

import common.testing.Scenario;
import common.testing.TestCase;
import common.testing.reporting.ReportWriter;
import common.testing.reporting.TestResultReportFlag;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import page_factory.global_kinetic.CareerContactPage;
import page_factory.global_kinetic.CareersPage;
import page_factory.global_kinetic.HomePage;
import selenium.web.driver.DriverManagerFactory;
import selenium.web.driver.DriverType;
import selenium.web.driver.managers.DriverManager;

import java.io.File;
import java.util.Arrays;

import static files.Config.reportSourcePath;

public class GlobalKineticCareers {
    private static final String[] reportableFields = {"Test Case No", "Scenario Description", "Result", "Comment"};
    private static TestCase testCase;
    private static ReportWriter reportWriter = new ReportWriter(reportableFields);

    @BeforeClass
    public static void setup() {
        testCase = new TestCase("Global Kinetic Careers Test",
                "src\\test\\resources\\web_automation_data_config.properties",
                "GLOBAL_KINETIC_TEST_DATA_WORKBOOK",
                "GLOBAL_KINETIC_TEST_DATA_SHEET",
                "Test Case No",
                "Test Case No,Scenario Description,Result,Comment");


        try {
            reportWriter.createReport("Global Kinetic Careers");
            testCase.loadDataExcel();
            testCase.loadTestCaseScenarios();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            // reportWriter.saveReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void cleanup() {
        try {
            reportWriter.saveReport();
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

    @Test(dataProvider = "Excel")
    @Description("Global Kinetic Careers")
    @Story("Get In Touch")
    public void scenarioTests(Scenario scenario) {
        DriverManager driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
        WebDriver webDriver = driverManager.getWebDriver(false);
        webDriver.get("https://globalkinetic.com");

        try {
            String testCaseNo = scenario.getCellValue("Test Case No");
            String scenarioDescription = scenario.getCellValue("Scenario Description");
            String name = scenario.getCellValue("Name");
            String email = scenario.getCellValue("Email");
            String phone = scenario.getCellValue("Phone");
            String role = scenario.getCellValue("Role");
            String differentSubject = scenario.getCellValue("Different Subject");
            String message = scenario.getCellValue("Message");
            String uploadFilePath = scenario.getCellValue("CV File Path");

            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Test Case No"), testCaseNo, TestResultReportFlag.DEFAULT);
            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Scenario Description"), scenarioDescription, TestResultReportFlag.DEFAULT);

            HomePage homePage = new HomePage(webDriver);
            homePage.switchToWindowAndMaximize(0);
            homePage.waitForVisibilityOfNewsLetterPopup();
            homePage.waitForVisibilityOfCareers();
            Thread.sleep(1000);
            CareersPage careersPage = homePage.clickOnCareers();
            Thread.sleep(1000);
            careersPage.waitForVisibilityOfOpenPositions();
            CareerContactPage careerContactPage = careersPage.clickGetInTouch();
            Thread.sleep(1000);
            careerContactPage.waitForVisibilityOfGetInTouch();

            if (!name.isEmpty()) {
                careerContactPage.inputName(name);
                Thread.sleep(1000);
            }

            if (!email.isEmpty()) {
                careerContactPage.inputEmail(email);
                Thread.sleep(1000);
            }

            if (!phone.isEmpty()) {
                careerContactPage.inputTelephoneNumber(phone);
                Thread.sleep(1000);
            }

            if (!role.isEmpty()) {
                careerContactPage.selectRole(role);
                Thread.sleep(1000);
            }

            if (!differentSubject.isEmpty()) {
                careerContactPage.inputDifferentSubject(differentSubject);
                Thread.sleep(1000);
            }

            if (!message.isEmpty()) {
                careerContactPage.inputLittleMore(message);
                Thread.sleep(1000);
            }

            if (!uploadFilePath.isEmpty()) {
                File file = new File(uploadFilePath);
                File absoluteFile = file.getAbsoluteFile();
                boolean exists = absoluteFile.exists();
                if (exists) {
                    String path = absoluteFile.getPath();
                    careerContactPage.attachCV(path);
                    Thread.sleep(1000);
                }
            }

            careerContactPage.takeScreenshot(reportSourcePath + "/screenshots/Global Kinetic Careers " + careerContactPage.getDateTimeStamp());
            careerContactPage.clickSend();

            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Result"), "Pass", TestResultReportFlag.SUCCESS);
            reportWriter.increamentReportRowIndex();
        } catch (Exception e) {
            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Result"), "Fail", TestResultReportFlag.DEFAULT);
            reportWriter.writeToReport(Arrays.asList(reportableFields).indexOf("Comment"), e.getMessage(), TestResultReportFlag.DEFAULT);
            reportWriter.increamentReportRowIndex();
        } finally {
            webDriver.quit();
        }
    }
}
