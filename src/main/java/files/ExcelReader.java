package files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * HSSF (Horrible SpreadSheet Format) – reads and writes Microsoft Excel (XLS) format files. It can read files written by Excel 97 onwards; this file format is known as the BIFF 8 format. As the Excel file format is complex and contains a number of tricky characteristics, some of the more advanced features cannot be read.
 *
 * XSSF (XML SpreadSheet Format) – reads and writes Office Open XML (XLSX) format files. Similar feature set to HSSF, but for Office Open XML files.
 */
public class ExcelReader {
    private Logger log  = LogManager.getLogger(ExcelReader.class);
    private String filePath;
    private String sheetName;
    private int sheetIndex;
    private XSSFWorkbook workbook;
    XSSFFormulaEvaluator hssfFormulaEvaluator;

    public ExcelReader(String filePath, int sheetIndex) {
        this.filePath = filePath;
        this.sheetIndex = sheetIndex;
    }

    public ExcelReader(String filePath, String sheetName) {
        this.filePath = filePath;
        this.sheetName = sheetName;
    }

    public ExcelReader(String filePath, int sheetIndex, XSSFWorkbook workbook) {
        this.filePath = filePath;
        this.sheetIndex = sheetIndex;
        this.workbook = workbook;
        hssfFormulaEvaluator = new XSSFFormulaEvaluator(workbook);
    }

    public ExcelReader(String filePath, String sheetName, XSSFWorkbook workbook) {
        this.filePath = filePath;
        this.sheetName = sheetName;
        this.workbook = workbook;
        hssfFormulaEvaluator = new XSSFFormulaEvaluator(workbook);
    }

    public ExcelReader(String filePath, String sheetName, int sheetIndex, XSSFWorkbook workbook) {
        this.filePath = filePath;
        this.sheetName = sheetName;
        this.sheetIndex = sheetIndex;
        this.workbook = workbook;
        hssfFormulaEvaluator = new XSSFFormulaEvaluator(workbook);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    private void createNewWorkBook(String filePath) throws Exception {
        try {
            log.info("Creating New Work Book: " + filePath);
            File file = new File(filePath);
            boolean exists = file.exists();
            log.info("File Exists: " + exists);

            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                // workbook = XSSFWorkbookFactory.createWorkbook(file, true);
                // workbook = XSSFWorkbookFactory.createWorkbook(fileInputStream);
                this.workbook = new XSSFWorkbook(fileInputStream);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error On Creating XSSFWorkbook: " + e.getMessage());
                throw new Exception("Error On Creating XSSFWorkbook: " + e.getMessage());
            }

            boolean workbookNotNull = this.workbook != null;
            log.info("Workbook Not Null: " + workbookNotNull);
            hssfFormulaEvaluator = new XSSFFormulaEvaluator(workbook);
            boolean hssfFormulaEvaluatorNotNull = hssfFormulaEvaluator != null;
            log.info("HssfFormulaEvaluator Not Null: " + hssfFormulaEvaluatorNotNull);
        } catch (Exception e) {
            log.info("Error While Creating New Work Book: " + e.getMessage());
            throw new Exception("Error While Creating New Work Book: " + e.getMessage());
        }
        // evaluateFormulae();
    }

    private void evaluateFormulae() {
        hssfFormulaEvaluator.evaluateAll();
    }

    private XSSFSheet getWorkBookSheet(String sheetName) throws Exception {
        try {
            log.info("Getting Work Book Sheet: " + sheetName);
            return workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while getting Work Book Sheet. " + e.getMessage());
            throw new Exception("Error while getting Work Book Sheet. " + e.getMessage());
        }
    }

    private XSSFSheet getWorkBookSheet(int sheetIndex) throws Exception {
        try {
            log.info("Getting Work Book Sheet: " + sheetIndex);
            return workbook.getSheetAt(sheetIndex);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while getting Work Book Sheet: " + e.getMessage());
            throw new Exception("Error while getting Work Book Sheet: " + e.getMessage());
        }
    }

    private void prepareOutterList(XSSFSheet sheet, List<List<String>> outerList) {
        boolean isHeaderRow = true;
        int numberOfColumns = 0;
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            List<String> innerList = new LinkedList<>();
            XSSFRow xssfRow = sheet.getRow(i);

            if (isHeaderRow) {
                numberOfColumns = xssfRow.getLastCellNum();
                isHeaderRow = false;
            }

            if (xssfRow != null) {
                for (int j = 0; j < numberOfColumns; j++) {
                    prepareInnerList(innerList, xssfRow, j);
                }
                outerList.add(Collections.unmodifiableList(innerList));
            }
        }
    }

    private void prepareInnerList(List<String> innerList, XSSFRow xssfRow, int j) {
        XSSFCell cell = xssfRow.getCell(j);
        if (cell == null) {
            innerList.add("");
        } else {
            switch (cell.getCellType()) {
                case BOOLEAN:
                    innerList.add(String.valueOf(cell.getBooleanCellValue()));
                    break;
                case ERROR:
                    innerList.add(String.valueOf(cell.getErrorCellString()));
                    break;
                case FORMULA:
                    CellValue evaluatedCellValue = hssfFormulaEvaluator.evaluate(cell);
                    String value = evaluatedCellValue.formatAsString();
                    try {
                        // TODO: Have a property to store all the expected formulae names so we can handle them accordingly.
                        if (cell.getCellFormula().contains("TODAY") || cell.getCellFormula().contains("Age Calc") || cell.getCellFormula().contains("AgeCalculation")) {
                            long excelDateMilliSeconds = (long) evaluatedCellValue.getNumberValue();
                            Date javaDate = DateUtil.getJavaDate(excelDateMilliSeconds);
                            value = new SimpleDateFormat("dd-MM-YYYY").format(javaDate);
                        }
                        innerList.add(value);
                    } catch (Exception e) {
                        log.error("Error while getting actual value (e.g. Date) from data sheet value: " + value + " . Formula: " + cell.getCellFormula());
                        innerList.add(value);
                    }
                    break;
                case NUMERIC:
                    innerList.add(String.valueOf(cell.getNumericCellValue()));
                    break;
                case STRING:
                    innerList.add(cell.getStringCellValue());
                    break;
                case BLANK:
                    innerList.add("");
                    break;
                default:
                    throw new IllegalStateException("Bad cell type (" + cell.getCellType() + ")");
            }
        }
    }

    private List<List<String>> getSheetData(XSSFSheet sheet) {
        List<List<String>> outerList = new LinkedList<>();
        prepareOutterList(sheet, outerList);
        return Collections.unmodifiableList(outerList);
    }

    public List<List<String>> getSheetData() throws Exception {
        log.info("Getting Sheet Data.");
        XSSFSheet sheet = null;
        List<List<String>> outerList = new LinkedList<>();

        try {
            if (workbook == null) {
                log.info("Workbook is null: true.");
                createNewWorkBook(filePath);
            }

            boolean workBookNull = workbook == null;
            log.info("Work Book Null: " + workBookNull);
            log.info("sheetName: " + sheetName + ", sheetIndex: " + sheetIndex);
            if (!workBookNull) {
                sheet = sheetName != null ? getWorkBookSheet(sheetName) : getWorkBookSheet(sheetIndex);
            }

            boolean sheetNull = sheet == null;
            if (!sheetNull) {
                log.debug("Sheet Null: false");
                outerList = getSheetData(sheet);
                return outerList;
            } else {
                log.debug("Sheet Null: true");
                throw new Exception("Excel Sheet Is Null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error on getSheetData: " + e.getMessage());
            throw new Exception("Error on getSheetData: " + e.getMessage());
        } finally {
            workbook.close();
        }
    }
}
