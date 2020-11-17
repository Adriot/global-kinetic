package files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class RecordReader {
    private Logger log  = LogManager.getLogger(RecordReader.class);
    private Object[] headers;
    private String field;
    private List<String> dataEntry;

    public RecordReader() {
    }

    public RecordReader(Object[] headers, String field, List<String> dataEntry) {
        this.headers = headers;
        this.field = field;
        this.dataEntry = dataEntry;
    }

    public Object[] getHeaders() {
        return headers;
    }

    public void setHeaders(Object[] headers) {
        this.headers = headers;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getDataEntry() {
        return dataEntry;
    }

    public void setDataEntry(List<String> dataEntry) {
        this.dataEntry = dataEntry;
    }

    private int getHeaderIndex(Object[] headers, String header) {
        return Arrays.asList(headers).indexOf(header);
    }

    private String getCellValue(String field) throws Exception {
        try {
            return getHeaderIndex(headers, field) != -1 ? dataEntry.get(getHeaderIndex(headers, field)) : "";
        } catch (Exception e) {
            log.error("Error while getting: " + field);
            throw new Exception("Error while getting: " + field);
        }
    }

    public String getCellValue(Object[] headers, List<String> dataEntry, String field) throws Exception {
        try {
            return getHeaderIndex(headers, field) != -1 ? dataEntry.get(getHeaderIndex(headers, field)) : "";
        } catch (Exception e) {
            log.error("Error while getting: " + field);
            throw new Exception("Error while getting: " + field);
        }
    }

}
