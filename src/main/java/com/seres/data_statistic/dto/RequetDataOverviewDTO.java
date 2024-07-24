package com.seres.data_statistic.dto;

import lombok.Data;

import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/24 18:19
 */
@Data
public class RequetDataOverviewDTO {
    private List<DataOverviewDTO> columns;
}
