package com.seres.data_statistic.service;

import com.seres.data_statistic.constains.CorrelationType;
import com.seres.data_statistic.dto.CorrelationDTO;
import com.seres.data_statistic.utils.Statistic;
import com.seres.data_statistic.vo.CorrelationPairResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description: 相关性分析
 * @date 2024/7/24 19:58
 */
@Service
public class CorrelationService {
    private static final Logger logger = LoggerFactory.getLogger(CorrelationService.class);

    /**
     * @Description 针对Pearson和Spearman相关性分析
     * @Param
     * @Return
     */
    public List<CorrelationPairResultVO> analyzeCorrelation(List<CorrelationDTO> columns, CorrelationType correlationType) {


        //结果封装返回
        List<CorrelationPairResultVO> resultVOS = new ArrayList<>();

        // 计算所有列对的相关性
        for (int i = 0; i < columns.size(); i++) {
            for (int j = i + 1; j < columns.size(); j++) {
                CorrelationPairResultVO vo = new CorrelationPairResultVO();
                CorrelationDTO col1 = columns.get(i);
                CorrelationDTO col2 = columns.get(j);

                String name1 = col1.getColName();
                String name2 = col2.getColName();

                // 将数据转换为 double 数组
                double[] data1 = Arrays.stream(col1.getData())
                        .filter(num -> num != null)
                        .mapToDouble(Number::doubleValue)
                        .toArray();
                double[] data2 = Arrays.stream(col2.getData())
                        .filter(num -> num != null)
                        .mapToDouble(Number::doubleValue)
                        .toArray();

                // 计算相关性系数
                double correlationValue = 0;
                String correlationName = "";

                switch (correlationType) {
                    case PEARSON:
                        correlationValue = Statistic.pearsonCorrelation(data1, data2);
                        correlationName = "Pearson";
                        break;
                    case SPEARMAN:
                        correlationValue = Statistic.spearmanCorrelation(data1, data2);
                        correlationName = "Spearman";
                        break;
                    /*case KENDALL:
                        correlationValue = Statistic.kendallTauB(data1, data2);
                        correlationName = "Kendall’s tau-b";
                        break;*/
                }


                // 将列对的相关性结果放入最终结果中
                vo.setColName1(name1);
                vo.setColName2(name2);
                vo.setCorrelation(correlationValue);
                resultVOS.add(vo);
            }
        }

        return resultVOS;
    }
}
