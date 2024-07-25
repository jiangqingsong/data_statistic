package com.seres.data_statistic.vo;

import lombok.Data;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/25 15:21
 */
@Data
public class CorrelationPairResultVO {
    private String colName1;
    private String colName2;
    private Double correlation;
}
