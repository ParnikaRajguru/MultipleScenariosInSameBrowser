package com.deno.utils;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    public static String getCellData(String filePath, int rowNum, int colNum) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis);

            // ✅ Always use first sheet
            Sheet sheet = workbook.getSheetAt(0);

            Row row = sheet.getRow(rowNum);
            if (row == null) {
                throw new RuntimeException("Row not found at index: " + rowNum);
            }

            Cell cell = row.getCell(colNum);
            if (cell == null) {
                throw new RuntimeException("Cell not found at column index: " + colNum);
            }

            DataFormatter formatter = new DataFormatter();
            String value = formatter.formatCellValue(cell);

            workbook.close();
            fis.close();

            return value;

        } catch (Exception e) {
            throw new RuntimeException("Excel read failed: " + e.getMessage(), e);
        }
    }

    // ✅ Helper to get last data row
    public static int getLastRowCount(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            int rowCount = sheet.getLastRowNum();
            workbook.close();
            fis.close();

            return rowCount;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}