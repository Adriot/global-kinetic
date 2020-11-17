package common.testing;

import java.util.Arrays;
import java.util.List;

public class Scenario {
    private Object[] headers;
    private List<String> dataEntry;
    private String result;
    private String failureReason;
    private String generalComment;

    public Scenario(Object[] headers, List<String> dataEntry) {
        this.headers = headers;
        this.dataEntry = dataEntry;
    }

    public Object[] getHeaders() {
        return headers;
    }

    public void setHeaders(Object[] headers) {
        this.headers = headers;
    }

    public List<String> getDataEntry() {
        return dataEntry;
    }

    public void setDataEntry(List<String> dataEntry) {
        this.dataEntry = dataEntry;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getGeneralComment() {
        return generalComment;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }

    private int getHeaderIndex(Object[] headers, String header) {
        return Arrays.asList(headers).indexOf(header);
    }

    public String getCellValue(Object[] headers, List<String> dataEntry, String field) {
        this.headers = headers;
        this.dataEntry = dataEntry;
        return getCellValue(field);
    }

    public String getCellValue(String field) {
        return getHeaderIndex(headers, field) != -1 ? dataEntry.get(getHeaderIndex(headers, field)) : "";
    }
}
