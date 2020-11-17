package files.transform;

import cucumber.api.DataTable;
import cucumber.api.Transformer;
import cucumber.runtime.ParameterInfo;
import cucumber.runtime.table.TableConverter;
import cucumber.runtime.xstream.LocalizedXStreams;
import files.ExcelReader;
import files.FilePropertiesConfig;
import gherkin.formatter.model.Comment;
import gherkin.formatter.model.DataTableRow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static files.Config.dataSourcePath;

public class ExcelToDataTable extends Transformer<DataTable> {
    private Logger log  = LogManager.getLogger(ExcelToDataTable.class);
    private String filePath;
    private String sheetName;
    private int sheetIndex;
    private DataTable dataTable;
    private FilePropertiesConfig propertiesSetup;

    public ExcelToDataTable() {
    }

    public ExcelToDataTable(String filePath) {
        this.filePath = filePath;
    }

    public ExcelToDataTable(String filePath, String sheetName) {
        this.filePath = filePath;
        this.sheetName = sheetName;
    }

    public ExcelToDataTable(String filePath, int sheetIndex) {
        this.filePath = filePath;
        this.sheetIndex = sheetIndex;
    }

    public String getFilePath() {
        log.info("Getting File Path: " + filePath);
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSheetName() {
        log.info("Getting Sheet Name:"+ sheetName);
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getSheetIndex() {
        log.info("Getting Sheet Index: " + sheetIndex);
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public DataTable getDataTable() {
        log.info("Getting Data Table.");
        return dataTable;
    }

    private List<List<String>> getExcelData(ExcelReader reader) throws Exception {
        log.info("Getting Excel Data");
        List<List<String>> excelData = new LinkedList<>();

        try {
            excelData = reader.getSheetData();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while Getting Excel Data (getExcelData): " + e.getMessage());
            throw new Exception("Error while Getting Excel Data (getExcelData): " + e.getMessage());
        }
        return excelData;
    }

    private List<DataTableRow> getDataTableRows(List<List<String>> excelData) {
        log.info("Getting Data Table Rows");
        try {
            List<DataTableRow> dataTableRows = new LinkedList<>();
            int line = 1;

            for(List<String> list : excelData){
                Comment comment = new Comment("", line);
                DataTableRow tableRow = new DataTableRow(Arrays.asList(comment), list, line++);
                dataTableRows.add(tableRow);
            }
            return dataTableRows;
        } catch (Exception e) {
            log.error("Getting Data Table Rows: " + e.getMessage());
            e.printStackTrace();
        }
        // TODO: Or throw an exception if chaining won't be too much.
        return null;
    }

    private DataTable getDataTable(List<DataTableRow> dataTableRows) throws Exception {
        log.info("Getting Data Table: ");
        try {
            ParameterInfo parameterInfo = new ParameterInfo(Object.class, null, null, null);
            TableConverter tableConverter = new TableConverter(new LocalizedXStreams(Thread.currentThread().getContextClassLoader()).get(Locale.getDefault()), parameterInfo);

            return new DataTable(dataTableRows, tableConverter);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while getting Data Table: " + e.getMessage());
            throw new Exception("Error while getting Data Table: " + e.getMessage());
        }
    }

    public void transformData() {
        log.info("Transforming Data.");

        try {
            ExcelReader reader = sheetName == null ? new ExcelReader(filePath, sheetIndex) : new ExcelReader(filePath, sheetName);
            List<List<String>> excelData = getExcelData(reader);
            List<DataTableRow> dataTableRows = getDataTableRows(excelData);
            dataTable = getDataTable(dataTableRows);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public DataTable transform(String dataSheetProperties) {
        log.info("Transforming Data.");
        try {
            Properties properties = setupFilePropertiesConfig();

            String[] dataSheetPropertiesList = dataSheetProperties.split(",");
            String filePathProperty = dataSheetPropertiesList[0];
            String sheetNameProperty = dataSheetPropertiesList[1];
            filePath = dataSourcePath + "/" +  properties.getProperty(filePathProperty);
            sheetName = properties.getProperty(sheetNameProperty);
            log.debug("File Path: " + filePath);
            log.debug("Sheet Name: " + sheetName);

            ExcelReader reader = new ExcelReader(filePath, sheetName);
            List<List<String>> excelData = getExcelData(reader);
            log.debug("Excel Data: " + excelData);

            List<DataTableRow> dataTableRows = null;
            if (excelData != null) {
                log.info("Excel Data Not Null: true");
                dataTableRows = getDataTableRows(excelData);
            }
            log.info("Done Transforming Data.");
            return getDataTable(dataTableRows);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        // TODO: Or throw an exception if chaining won't be too much.
        return null;
    }

    private Properties setupFilePropertiesConfig() {
        if (propertiesSetup == null) {
            propertiesSetup = new FilePropertiesConfig();
            propertiesSetup.loadProperties();
        }
        return propertiesSetup.getProperties();
    }

    public Properties setupFilePropertiesConfig(String propertiesFilePath) {
        if (propertiesSetup == null) {
            propertiesSetup = new FilePropertiesConfig(propertiesFilePath);
            propertiesSetup.loadProperties();
        }
        return propertiesSetup.getProperties();
    }

}
