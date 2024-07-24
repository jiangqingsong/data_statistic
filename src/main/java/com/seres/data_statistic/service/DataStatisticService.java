package com.seres.data_statistic.service;


import com.seres.data_statistic.dto.ColData;
import com.seres.data_statistic.dto.DataNormalDTO;
import com.seres.data_statistic.model.ColumnData2;
import com.seres.data_statistic.utils.NormalityTestUtils;
import com.seres.data_statistic.utils.Statistic;
import com.seres.data_statistic.vo.DataDescMutVO;
import com.seres.data_statistic.vo.DataDescVO;
import com.seres.data_statistic.vo.NormalTestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:描述性分析：
 * 1、数据概览
 * 2、描述性统计
 * 3、正态性检验
 * @date 2024/7/23 12:11
 */
@Service
public class DataStatisticService {
    private static final Logger logger = LoggerFactory.getLogger(DataStatisticService.class);


    /**
    * @Description 描述性统计（输入为定量）
    * @Param
    * @Return
    */
    public List<DataDescMutVO> dataDesc(List<ColData> columns) {


        List<DataDescMutVO> list = new ArrayList<>();

        for (ColData column : columns) {
            String columnName = column.getColName();
            Number[] data = column.getData();

            // 使用 Statistics 工具类进行描述性统计分析
            DataDescVO dataDesc = Statistic.analyze(data);
            DataDescMutVO descMutVO = new DataDescMutVO();
            descMutVO.setColName(columnName);
            descMutVO.setDataDesc(dataDesc);
            list.add(descMutVO);
        }

        return list;
    }

    /**
     * 正态性分析
     * @param columns 数据列列表
     * @return JSON 字符串
     */
    public List<NormalTestVO> normalityAnalysis(List<DataNormalDTO> columns) {
        // 创建 JSON 数组来存储每列数据的分析结果

        List<NormalTestVO> result = new ArrayList<>();
        // 遍历每一列数据并进行分析
        for (DataNormalDTO column : columns) {
            // 分析当前列的数据
            NormalTestVO analysisResult = NormalityTestUtils.normalityAnalysis(column.getData());
            analysisResult.setColName(column.getColName());
            result.add(analysisResult);
        }

        return result;
    }
}
