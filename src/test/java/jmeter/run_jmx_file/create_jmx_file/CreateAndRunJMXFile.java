package jmeter.run_jmx_file.create_jmx_file;

import jmeter.JMeterDriver;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static files.Config.reportSourcePath;

public class CreateAndRunJMXFile {
    @Test
    public void testJMeterTestPlan() throws IOException {
        JMeterDriver jMeterDriver = new JMeterDriver();
        jMeterDriver.setupJMeterUtils();

        try {
            String reportPath = reportSourcePath + "/jmeter/create_jmx_file " + getDateTimeStamp();
            HashMap userDefinedVariables = new HashMap<>();
            userDefinedVariables.put("serverName", "172.18.189.142");
            userDefinedVariables.put("port", "8443");
            userDefinedVariables.put("path", "/navwellness/UI/ui.navwellness.start");
            jMeterDriver.configureUserDefinedVariable("User Defined Variables", true, userDefinedVariables);

            String domain = "${serverName}";
            String port = "${port}";
            String path = "${path}";
            String postBody = "{\"type\":\"fester.ui.get\",\"id\":\"ui.navwellness.start\",\"parent\":{\"id\":\"me\"}," +
                    "\"channel\":{\"vodsstandin\":false,\"vodsmemo\":false,\"channel\":\"PHONE_APP\",\"version\":\"26\"," +
                    "\"skin\":\"FNB\",\"country\":15,\"subVersion\":\"\"},\"formValues\":[{\"key\":\"redirectString\"," +
                    "\"value\":\"123456\"}],\"customer\":{\"ucn\":${ucn},\"company\":15,\"authenticated\":true," +
                    "\"firstName\":\"\",\"lastName\":\"\",\"idNumber\":\"\",\"communicationDetailsList\":[],\"trusted\"" +
                    ":false},\"browser\":{\"onlineCookie\":\"\"},\"device\":{\"platform\":\"MOBI\",\"model\":" +
                    "\"Samsung Galaxy S6 Edge\",\"gsm\":true,\"fingerprintSupported\":false,\"uniqueid\":" +
                    "\"g7e/Acem5BXUbhL8CYkDCQPlPcABbDxBCJh508LlhwmlEB4xT2NxrIWRV3dKigMN9PUIwEDVEfkbwdm440eu1zzhWYi1Yp5E" +
                    "sy+gGNkr2Ta3Zpm1BGaw+OqCjuayiPrVPrGRPx1/fMvihmBHvtHS7Qb+sZlAa+K1bS6FQKwDtSXcmhsM3CRxwS2N0JIAqDnRMk" +
                    "RUU8oqD7UzC06V9zCGsxNmFQVhzIXdmra/MM42uHETRn231d1Z4Z3BlsIIruFBK07mMycw6rknvPhrYiDS3dp9bAzx2m9l1NV0" +
                    "HrNFbWiqjqArV1M3p7SfPmKDvge022tih3tHSgsZsgIB7z+24w\\u003d\\u003d\",\"resolution\":\"mdpi\",\"height" +
                    "\":0,\"width\":0,\"jailbrokenResultsProcessed\":false,\"jailbroken\":false,\"nfcSupported\":false,\"" +
                    "nfcEnabled\":false,\"bluetoothSupported\":false,\"bluetoothEnabled\":false,\"lockscreenEnabled\"" +
                    ":false},\"location\":{\"latitude\":-26.119029,\"longitude\":27.950555,\"altitude\":0.0},\"moreData\"" +
                    ":{},\"sessionID\":\"LQ4eUcPhd87HZ5T9IQdPAQgo\",\"requestID\":\"3f383911-ff48-4e11-b331-feff9f094f77\"" +
                    ",\"serverIP\":\"\",\"pushData\":\"\",\"widget\":{\"dropped\":false,\"resizable\":false},\"hid\":{\"" +
                    "accessNumber\":\"\",\"isEndpointSetup\":false},\"hidStatus\":{\"responsecode\":\"\",\"responsemessage" +
                    "\":\"\",\"accessNumber\":\"\"}}";

            jMeterDriver.configureReportGeneratorPath(
                    "CSV Data Set Config",
                    true, "C:\\adroit\\jmeter poc\\alisTestData.csv", "",
                    "ucn", false, ",", false,
                    true, false, "shareMode.all");

            HTTPSamplerProxy httpSamplerProxy = jMeterDriver.configureHTTPSamplerProxy(
                    domain, port, path, "POST", "https", "Test 172.18.189.142",
                    "", postBody, "");

            jMeterDriver.configureLoopController(5, true);
            jMeterDriver.configureThreadGroup("Sample Thread Group", 5,5,
                    "", "", "",
                    "", "", "");

            jMeterDriver.configureTestPlan("Create JMeter Script From Java Code");
            jMeterDriver.constructTestPlan();
            jMeterDriver.addSummariser();
            jMeterDriver.addLogger(
                    reportPath + "\\report\\report.csv", "Simple Data Writer", "ResultCollector",
                    "SimpleDataWriter");
            jMeterDriver.addLogger(
                    reportPath + "\\report\\report.jtl", "View Results Tree", "ResultCollector",
                    "ViewResultsFullVisualizer");
            jMeterDriver.addLogger(
                    "", "Summary Report", "ResultCollector", "SummaryReport");


            // Run Test Plan
            jMeterDriver.configureJMeterEngine();
            jMeterDriver.runJMeterEngine();
            jMeterDriver.configureReportGeneratorPath(reportPath + "\\report\\html");
            jMeterDriver.generateHtmlReport(reportPath + "\\report\\report.csv");
            jMeterDriver.saveTestPlanTree(reportPath + "\\report\\jmeter_api_sample.jmx");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getDateTimeStamp() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        return format.format(now);
    }
}
