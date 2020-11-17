package selenium.web.driver.files.transform;

import cucumber.api.DataTable;
import files.transform.FlatFileToDataTable;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlatFileToDataTableTests {
    @Test
    public void excelToDataTableTestDefaultConfigFile() {
        FlatFileToDataTable flatFileToDataTable = new FlatFileToDataTable();
        DataTable dataTable = flatFileToDataTable.transform("FLAT_FILE_TEST_DATA");
        Assert.assertNotNull(dataTable);
    }

    @Test
    public void excelToDataTableTestDifferentConfigFile() {
        FlatFileToDataTable excelToDataTable = new FlatFileToDataTable();
        excelToDataTable.setupFilePropertiesConfig("src\\test\\resources\\data_config.properties");
        DataTable dataTable = excelToDataTable.transform("FLAT_FILE_TEST_DATA");
        Assert.assertNotNull(dataTable);
    }
}
