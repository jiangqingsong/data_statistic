package com.seres.data_statistic.dto;

import lombok.Data;

import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description: 用于接收数据类型分析数据列
 * @date 2024/7/24 15:54
 */
@Data
public class DetermineCorrelationMethodDTO {
    private String colName;
    private List<Object> data;
}
