package com.seres.data_statistic.dto;

import lombok.Data;

import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/24 20:26
 */
@Data
public class DataNormalDTO {
    private String colName;
    private double[] data;
}
