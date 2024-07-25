package com.seres.data_statistic.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/25 15:58
 */
@Data
public class CalculateHistogramVO {
    private String colName;
    private Map<String, Integer> data;
}
