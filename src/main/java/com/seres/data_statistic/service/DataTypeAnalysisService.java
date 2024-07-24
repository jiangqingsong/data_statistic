package com.seres.data_statistic.service;

import com.seres.data_statistic.dto.ColDataDTO;
import com.seres.data_statistic.dto.ColDataTypeDTO;
import com.seres.data_statistic.utils.DataAnalyzer;
import com.seres.data_statistic.vo.DataTypeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description: 数据类型分析(定类or定量)
 * @date 2024/7/24 16:27
 */
@Service
public class DataTypeAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(DataTypeAnalysisService.class);
    public List<DataTypeVO> analyzeDataColumns(List<ColDataTypeDTO> dataColumns) {
        List<DataTypeVO> result = new ArrayList<>();

        for (ColDataTypeDTO column : dataColumns) {
            String[] data = column.getData().toArray(new String[0]);
            String analysisResult = DataAnalyzer.analyzeData(data);
            DataTypeVO dataType = new DataTypeVO(column.getColName(), analysisResult);
            result.add(dataType);
        }

        return result;
    }
}
