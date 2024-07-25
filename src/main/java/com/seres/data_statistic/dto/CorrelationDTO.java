package com.seres.data_statistic.dto;

import lombok.Data;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/25 15:12
 */
@Data
public class CorrelationDTO {
    private String colName;
    private Number[] data;
}
