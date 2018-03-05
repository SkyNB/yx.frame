package com.lnet.frame.excel.writer;

import com.lnet.frame.excel.util.CellUtils;
import com.lnet.frame.excel.util.ExcelFormat;
import com.lnet.frame.excel.util.WorkbookUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.*;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

public final class ExcelWriter {

    public static <T> void write(OutputStream output, ExcelFormat format, List<T> data, List<String> headers, Function<T, List<Object>> rowMapper) throws IOException {

        try (Workbook workbook = WorkbookUtils.createWorkbook(format)) {
            Sheet sheet = workbook.createSheet("data");

            short borderWidth = 1;
            short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();
            short blue = IndexedColors.GREY_25_PERCENT.getIndex();
            short black = IndexedColors.BLACK.getIndex();
            short white = IndexedColors.WHITE.getIndex();
            CellStyle headerStyle = WorkbookUtils.createBorderedStyle(workbook, borderWidth, borderColor, blue, black);
            CellStyle cellStyle = WorkbookUtils.createBorderedStyle(workbook, borderWidth, borderColor, white, black);

            int cursor = 0;

            if (headers.size() > 0) {
                Row header = sheet.createRow(cursor);
                writeRow(header, headers, headerStyle);
                cursor++;
            }

            // 写入每行
            for (T item : data) {
                Row row = sheet.createRow(cursor);
                cursor++;
                try {
                    writeRow(row, rowMapper.apply(item), cellStyle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 自动列宽
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            // 固定列头
            sheet.createFreezePane(0,1);
            workbook.write(output);
            output.flush();
        }
    }


    public static <T> void write(OutputStream output, ExcelFormat format, List<T> data) throws IOException {

        if (data == null || data.size() == 0) return;

        List<String> headers = new ArrayList<>();

        T first = data.get(0);
        Class clazz = first.getClass();
        boolean isMap = Map.class.isAssignableFrom(clazz);

        if (isMap) {
            ((Map) first).keySet().forEach(m -> headers.add(m.toString()));
        } else {
            PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(first);
            for (PropertyDescriptor prop :
                    props) {
                headers.add(prop.getName());
            }
            headers.remove("class");
        }
        write(output, format, data, headers, item -> {
            try {
                Collection values;
                if (item instanceof Map) {
                    values = ((Map) item).values();
                } else {
                    Map<String, Object> properties = new HashMap<>();
                    PropertyUtils.copyProperties(properties, item);
                    properties.remove("class");
                    values = properties.values();
                }
                return (values instanceof List) ? (List) values : new ArrayList(values);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }

        });

    }


    private static <T> void writeRow(Row row, List<T> values, CellStyle cellStyle) {
        //CellStyle cellStyle = createBorderedStyle(row.getSheet().getWorkbook());

        int columns = values.size();
        for (int i = 0; i < columns; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            CellUtils.setValue(cell, values.get(i));

        }
    }


}
