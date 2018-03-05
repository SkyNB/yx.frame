package com.lnet.frame.excel.reader;

import com.lnet.frame.excel.util.CellUtils;
import com.lnet.frame.util.BeanHelper;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;

public class ExcelRow {

    private final Row row;
    private final Map<String, Short> columns;

    public ExcelRow(Row row, Map<String, Short> columns) {
        this.row = row;
        this.columns = columns;
    }

    public Row getRow() {
        return row;
    }

    public Map<String, Short> getColumns() {
        return columns;
    }

    public int getRowNum() {
        return row.getRowNum();
    }

    public Object getColumnValue(String column) {
        Short index = columns.get(column);
        return getColumnValue(index);
    }

    public Object getColumnValue(Short index) {
        if (index == null || index > row.getLastCellNum()) return null;
        return CellUtils.getValue(row.getCell(index));
    }

    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        for (Map.Entry<String, Short> entry : columns.entrySet()) {
            values.put(entry.getKey(), getColumnValue(entry.getValue()));
        }
        return values;
    }

    public <E> E map(Class<E> clazz) {
        return BeanHelper.convert(getValues(), clazz);
    }
}
