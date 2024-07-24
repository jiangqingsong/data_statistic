package com.seres.data_statistic.vo;

import lombok.Data;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/24 19:49
 */
@Data
public class DataOverviewVO {
    private String colName;
    private Double boxPlotMin;
    private Double q1;
    private Double q3;
    private Double max;
    private Double coefficientOfVariation;
    private Double kurtosis;
    private Integer sampleCount;
    private Double min;
    private Double median;
    private Integer missingCount;
    private Double variance;
    private Double mean;
    private Double skewness;
    private Double standardDeviation;
    private Double boxPlotMax;
    private Integer uniqueCount;

}
