package com.seres.data_statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/24 15:57
 */
@Data
public class RequestDTO {
    private List<ColDataTypeDTO> columns;
}
