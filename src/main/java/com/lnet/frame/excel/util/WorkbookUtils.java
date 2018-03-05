package com.lnet.frame.excel.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class WorkbookUtils {


    public static Workbook createWorkbook(ExcelFormat format) throws IOException {
        switch (format) {
            case OFFICE2003:
                return new HSSFWorkbook();
            case OFFICE2007:
                return new XSSFWorkbook();
            default:
                throw new IllegalArgumentException("错误的文件类型");
        }
    }

    public static Workbook openWorkbook(InputStream file, ExcelFormat format) throws IOException {
        switch (format) {
            case OFFICE2003:
                return new HSSFWorkbook(file);
            case OFFICE2007:
                return new XSSFWorkbook(file);
            default:
                throw new IllegalArgumentException("错误的文件类型");
        }
    }

    public static void closeQuietly(Workbook workbook) {
        try {
            workbook.close();
        } catch (final IOException ignore) {
        }
    }

    public static CellStyle createBorderedStyle(Workbook wb, short borderWidth, short borderColor, short bgColor, short fontColor){

        CellStyle style = wb.createCellStyle();
        style.setBorderRight(borderWidth);
        style.setRightBorderColor(borderColor);
        style.setBorderBottom(borderWidth);
        style.setBottomBorderColor(borderColor);
        style.setBorderLeft(borderWidth);
        style.setLeftBorderColor(borderColor);
        style.setBorderTop(borderWidth);
        style.setTopBorderColor(borderColor);
        style.setFillBackgroundColor(bgColor);
        style.setFillForegroundColor(bgColor);
        Font font = wb.createFont();
        font.setColor(fontColor);
        style.setFont(font);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        return style;
    }

}
