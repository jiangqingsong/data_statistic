package com.seres.data_statistic.model;

import lombok.Data;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/23 12:49
 */
@Data
public class ColumnData {
    private String columnName;
    private Number[] data;

}
