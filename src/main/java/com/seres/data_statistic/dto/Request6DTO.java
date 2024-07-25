package com.seres.data_statistic.dto;

import lombok.Data;

import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/24 19:52
 */
@Data
public class Request6DTO {
    private List<CorrelationDTO> columns;
}
