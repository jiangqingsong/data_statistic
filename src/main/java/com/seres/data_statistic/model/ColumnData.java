package com.seres.data_statistic.model;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/23 12:49
 */
public class ColumnData {
    private String columnName;
    private Number[] data;

    public ColumnData(String columnName, Number[] data) {
        this.columnName = columnName;
        this.data = data;
    }

    public String getColumnName() {
        return columnName;
    }

    public Number[] getData() {
        return data;
    }
}
