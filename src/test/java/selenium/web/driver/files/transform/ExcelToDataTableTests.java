package selenium.web.driver.files.transform;

import cucumber.api.DataTable;
import files.transform.ExcelToDataTable;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExcelToDataTableTests {
    @Test
    public void excelToDataTableTestDefaultConfigFile() {
        ExcelToDataTable excelToDataTable = new ExcelToDataTable();
        DataTable dataTable = excelToDataTable.transform("DATA_SHEET,SHEET_NAME");
        Assert.assertNotNull(dataTable);
    }

    @Test
    public void excelToDataTableTestDifferentConfigFile() {
        ExcelToDataTable excelToDataTable = new ExcelToDataTable();
        excelToDataTable.setupFilePropertiesConfig("src\\test\\resources\\data_config.properties");
        DataTable dataTable = excelToDataTable.transform("TEST_DATA_WORKBOOK,TEST_DATA_SHEET");
        Assert.assertNotNull(dataTable);
    }
}
