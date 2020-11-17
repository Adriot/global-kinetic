package files.transform;

import cucumber.api.DataTable;
import cucumber.api.Transformer;
import cucumber.runtime.ParameterInfo;
import cucumber.runtime.table.TableConverter;
import cucumber.runtime.xstream.LocalizedXStreams;
import files.FilePropertiesConfig;
import files.FlatFileReader;
import gherkin.formatter.model.Comment;
import gherkin.formatter.model.DataTableRow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static files.Config.dataSourcePath;

public class FlatFileToDataTable extends Transformer<DataTable> {
    private Logger log  = LogManager.getLogger(FlatFileToDataTable.class);
    private String filePath;
    private DataTable dataTable;
    private FilePropertiesConfig propertiesSetup;

    public FlatFileToDataTable() {
    }

    public FlatFileToDataTable(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        log.info("Getting File Path: " + filePath);
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public DataTable getDataTable() {
        log.info("Getting Data Table.");
        return dataTable;
    }

    private List<DataTableRow> getDataTableRows(List<List<String>> dataSet) {
        log.info("Getting Data Table Rows");
        try {
            List<DataTableRow> dataTableRows = new LinkedList<>();
            int line = 1;

            for(List<String> list : dataSet){
                Comment comment = new Comment("", line);
                DataTableRow tableRow = new DataTableRow(Arrays.asList(comment), list, line++);
                dataTableRows.add(tableRow);
            }
            return dataTableRows;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        // TODO: Or throw an exception if chaining won't be too much.
        return null;
    }

    private DataTable getDataTable(List<DataTableRow> dataTableRows) {
        log.info("Getting Data Table");
        try {
            ParameterInfo parameterInfo = new ParameterInfo(Object.class, null, null, null);
            TableConverter tableConverter = new TableConverter(new LocalizedXStreams(Thread.currentThread().getContextClassLoader()).get(Locale.getDefault()), parameterInfo);

            return new DataTable(dataTableRows, tableConverter);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        // TODO: Or throw an exception if chaining won't be too much.
        return null;
    }

    @Override
    public DataTable transform(String dataSheetProperties) {
        log.info("Transforming Data.");
        try {
            Properties properties = setupFilePropertiesConfig();

            filePath = dataSourcePath + "/" +  properties.getProperty(dataSheetProperties);
            log.debug("File Path: " + filePath);

            FlatFileReader reader = new FlatFileReader(filePath);
            List<List<String>> data = reader.getDataSet();
            List<DataTableRow> dataTableRows = getDataTableRows(data);
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
