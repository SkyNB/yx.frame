package com.lnet.frame.excel.util;

import com.lnet.frame.util.DateHelper;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.Date;


public class CellUtils {

    public static boolean isNull(Cell cell) {
        return cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK;
    }

    public static Object getValue(Cell cell, int cellType) {

        switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();

            case Cell.CELL_TYPE_FORMULA:
                return getValue(cell, cell.getCachedFormulaResultType());

            case Cell.CELL_TYPE_BLANK:
                return null;

            case Cell.CELL_TYPE_ERROR:
                return null;

            default:
                return null;
        }
    }

    public static Object getValue(Cell cell) {
        if (isNull(cell)) return null;
        return getValue(cell, cell.getCellType());
    }

    public static String getForString(Cell cell) {
        Object val = getValue(cell);
        return val == null ? null : val.toString();
    }

    public static Date getForDate(Cell cell, String format) {

        Object val = getValue(cell);
        if (val == null) return null;
        if (val instanceof Date) return (Date) val;
        if (val instanceof String) return DateHelper.parse(val, format);
        if (val instanceof Number) return new Date((Long) ConvertUtils.convert(val, Long.class));

        return null;
    }

    public static Double getForDouble(Cell cell) {
        Object val = getValue(cell);
        if (val == null) return null;
        if (val instanceof Number) return (Double) ConvertUtils.convert(val, Double.class);
        if (val instanceof String) return Double.parseDouble(val.toString());

        return null;
    }


    public static void setValue(Cell cell, Object value) {
        if (cell == null || value == null) return;

        if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Date) {
            CreationHelper createHelper = cell.getSheet().getWorkbook().getCreationHelper();
            CellStyle theStyle = cell.getSheet().getWorkbook().createCellStyle();
            theStyle.cloneStyleFrom(cell.getCellStyle());
            cell.setCellStyle(theStyle);

            cell.getCellStyle().setDataFormat(createHelper.createDataFormat().getFormat("YYYY-MM-dd HH:mm"));
            cell.setCellValue((Date) value);

        } else if (value instanceof Number) {
            cell.setCellValue((Double) ConvertUtils.convert(value, Double.class));
        } else {
            cell.setCellValue(value.toString());
        }

    }

}
