package com.seres.data_statistic.vo;

import lombok.Data;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/24 20:15
 */
@Data
public class NormalTestVO {
    private String colName;
    private int sampleSize;
    private double median;
    private double mean;
    private double stdDev;
    private double skewness;
    private double kurtosis;
    private double swW;
    private double swPValue;
    private double ksD;
    private double ksPValue;
}
