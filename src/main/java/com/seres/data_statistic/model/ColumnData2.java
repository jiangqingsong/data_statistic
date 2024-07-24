package com.seres.data_statistic.model;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/23 16:15
 */
public class ColumnData2 {
    private String colName;
    private double[] data;

    public ColumnData2(String colName, double[] data) {
        this.colName = colName;
        this.data = data;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }
}
