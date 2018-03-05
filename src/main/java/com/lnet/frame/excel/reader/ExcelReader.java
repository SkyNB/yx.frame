package com.lnet.frame.excel.reader;

import com.lnet.frame.excel.util.CellUtils;
import com.lnet.frame.excel.util.ExcelFormat;
import com.lnet.frame.excel.util.WorkbookUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class ExcelReader {

    private static final int maxReading = 10000;

    public static <T> List<T> readByColumnName(InputStream input, ExcelFormat format, int sheetIndex, int headerRowIndex, Function<ExcelRow, T> rowMapper) throws IOException {
        List<T> result = new ArrayList<>();
        try (Workbook workbook = WorkbookUtils.openWorkbook(input, format)) {
            if (workbook == null) return result;

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int lastRow = sheet.getLastRowNum() > maxReading ? maxReading : sheet.getLastRowNum();
            if (lastRow < 1 || lastRow < headerRowIndex) throw new FileFormatException("行数为空");

            Row header = sheet.getRow(headerRowIndex);
            short lastColumn = header.getLastCellNum();

            Map<String, Short> columnMappings = new HashMap<>(lastColumn);

            // 读取列头
            for (short i = 0; i < lastColumn; i++) {
                Cell cell = header.getCell(i);
                String column = CellUtils.getForString(cell);
                columnMappings.put(column, i);
            }

            // 读取每行
            for (int i = headerRowIndex + 1; i <= lastRow; i++) {
                ExcelRow row = new ExcelRow(sheet.getRow(i), columnMappings);
                try {
                    T mappedValue = rowMapper.apply(row);
                    result.add(mappedValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static <T> List<T> readByColumnName(InputStream input, ExcelFormat format, int sheetIndex, int headerRowIndex, final Class<T> clazz) throws IOException {
        Function<ExcelRow, T> rowMapper = excelRow -> excelRow.map(clazz);
        return readByColumnName(input, format, sheetIndex, headerRowIndex, rowMapper);
    }

}
