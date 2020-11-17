package files;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    private String filePath;
    private String sheetName;
    private int sheetIndex;
    private XSSFWorkbook workbook;

    public ExcelWriter() {
    }

    public ExcelWriter(String filePath) {
        this.filePath = filePath;
    }

    public ExcelWriter(String filePath, String sheetName) {
        this.filePath = filePath;
        this.sheetName = sheetName;
    }

    public ExcelWriter(String filePath, int sheetIndex) {
        this.filePath = filePath;
        this.sheetIndex = sheetIndex;
    }

    public ExcelWriter(String filePath, String sheetName, XSSFWorkbook workbook) {
        this.filePath = filePath;
        this.sheetName = sheetName;
        this.workbook = workbook;
    }

    public ExcelWriter(String filePath, int sheetIndex, XSSFWorkbook workbook) {
        this.filePath = filePath;
        this.sheetIndex = sheetIndex;
        this.workbook = workbook;
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

    public void createWorkbook() {
        workbook = new XSSFWorkbook();
    }

    public XSSFSheet addSheet() {
        if (workbook != null) {
            if (workbook.getSheet(sheetName) == null) {
                return workbook.createSheet(sheetName);
            } else {
                return workbook.getSheet(sheetName);
            }
        }
        return null;
    }

    public void writeToSheet(int rowNumber, int columnNumber, Object value) {
        XSSFSheet sheet = workbook.getSheet(sheetName) == null ? addSheet() : workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNumber) == null ? sheet.createRow(rowNumber) : sheet.getRow(rowNumber);
        XSSFCell cell = row.createCell(columnNumber) == null ? row.createCell(columnNumber) : row.getCell(columnNumber);
        cell.setCellValue((String) value);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);
        style.setFont(font);

        cell.setCellStyle(style);
    }

    public void writeErrorToSheet(int rowNumber, int columnNumber, Object value) {
        XSSFSheet sheet = workbook.getSheet(sheetName) == null ? addSheet() : workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNumber) == null ? sheet.createRow(rowNumber) : sheet.getRow(rowNumber);
        XSSFCell cell = row.createCell(columnNumber) == null ? row.createCell(columnNumber) : row.getCell(columnNumber);
        cell.setCellValue((String) value);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);
        font.setColor((short) 2);
        style.setFont(font);

        cell.setCellStyle(style);
    }

    public void writeWarningToSheet(int rowNumber, int columnNumber, Object value) {
        XSSFSheet sheet = workbook.getSheet(sheetName) == null ? addSheet() : workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNumber) == null ? sheet.createRow(rowNumber) : sheet.getRow(rowNumber);
        XSSFCell cell = row.createCell(columnNumber) == null ? row.createCell(columnNumber) : row.getCell(columnNumber);
        cell.setCellValue((String) value);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);
        font.setColor((short) 4);
        style.setFont(font);

        cell.setCellStyle(style);
    }

    public void writeSuccessToSheet(int rowNumber, int columnNumber, Object value) {
        XSSFSheet sheet = workbook.getSheet(sheetName) == null ? addSheet() : workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNumber) == null ? sheet.createRow(rowNumber) : sheet.getRow(rowNumber);
        XSSFCell cell = row.createCell(columnNumber) == null ? row.createCell(columnNumber) : row.getCell(columnNumber);
        cell.setCellValue((String) value);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);
        font.setColor((short) 3);
        style.setFont(font);

        cell.setCellStyle(style);
    }

    public void writeHeardersToSheet(int rowNumber, int columnNumber, Object value) {
        XSSFSheet sheet = workbook.getSheet(sheetName) == null ? addSheet() : workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNumber) == null ? sheet.createRow(rowNumber) : sheet.getRow(rowNumber);
        XSSFCell cell = row.createCell(columnNumber) == null ? row.createCell(columnNumber) : row.getCell(columnNumber);
        cell.setCellValue((String) value);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.DOUBLE);
        style.setBorderBottom(BorderStyle.DOUBLE);
        style.setBorderLeft(BorderStyle.DOUBLE);
        style.setBorderRight(BorderStyle.DOUBLE);

        XSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        style.setFont(font);

        cell.setCellStyle(style);
    }

    public  void adjustColumnSizes() {
        XSSFSheet sheet = workbook.getSheet(sheetName) == null ? addSheet() : workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(0) == null ? sheet.createRow(0) : sheet.getRow(0);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            sheet.autoSizeColumn((short) i);
        }
    }

    public void createFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        workbook.write(fileOutputStream);
    }
}
