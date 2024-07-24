package com.seres.data_statistic.dto;

import com.seres.data_statistic.model.ColumnData;
import lombok.Data;

import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/24 15:57
 */
@Data
public class Request2DTO {
    private List<ColData> columns;
}
