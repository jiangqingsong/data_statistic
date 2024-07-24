package com.seres.data_statistic.service;

import com.alibaba.fastjson.JSONObject;
import com.seres.data_statistic.constains.CorrelationType;
import com.seres.data_statistic.model.ColumnData;
import com.seres.data_statistic.utils.Statistic;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description: 相关性分析
 * @date 2024/7/24 19:58
 */
@Data
public class CorrelationService {
    private static final Logger logger = LoggerFactory.getLogger(CorrelationService.class);
    public JSONObject analyzeCorrelations(List<ColumnData> columns, CorrelationType correlationType) {
        JSONObject result = new JSONObject();

        // 获取列名列表
        List<String> columnNames = new ArrayList<>();
        for (ColumnData column : columns) {
            columnNames.add(column.getColumnName());
        }

        // 计算所有列对的相关性
        for (int i = 0; i < columns.size(); i++) {
            for (int j = i + 1; j < columns.size(); j++) {
                ColumnData col1 = columns.get(i);
                ColumnData col2 = columns.get(j);

                String name1 = col1.getColumnName();
                String name2 = col2.getColumnName();

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
                    case KENDALL:
                        correlationValue = Statistic.kendallTauB(data1, data2);
                        correlationName = "Kendall’s tau-b";
                        break;
                }

                // 创建列对的相关性结果 JSON 对象
                JSONObject correlationResult = new JSONObject();
                correlationResult.put(correlationName, correlationValue);

                // 将列对的相关性结果放入最终结果中
                String key = String.format("相关性(%s, %s)", name1, name2);
                result.put(key, correlationResult);
            }
        }

        return result;
    }
}
