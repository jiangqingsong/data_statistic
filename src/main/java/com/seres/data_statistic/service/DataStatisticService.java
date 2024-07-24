package com.seres.data_statistic.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seres.data_statistic.constains.CorrelationType;
import com.seres.data_statistic.model.ColumnData;
import com.seres.data_statistic.model.ColumnData2;
import com.seres.data_statistic.utils.DataAnalyzer;
import com.seres.data_statistic.utils.NormalityTestUtils;
import com.seres.data_statistic.utils.Statistic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/23 12:11
 */
public class DataStatisticService {
    public static JSONObject analyzeColumns(List<ColumnData> columns) {
        JSONObject result = new JSONObject();

        for (ColumnData column : columns) {
            String columnName = column.getColumnName();
            Number[] data = column.getData();

            // 使用 Statistics 工具类进行描述性统计分析
            String analysisResult = Statistic.analyze(data);

            // 将结果解析为 JSON 并放入最终结果中
            JSONObject columnResult = JSONObject.parseObject(analysisResult);
            result.put(columnName, columnResult);
        }

        return result;
    }


    public static JSONObject analyzeCorrelations(List<ColumnData> columns, CorrelationType correlationType) {
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


    /**
     * 处理 JSON 格式的数据（每列数据包含列名和数据），并判断每一列的数据类型
     *
     * @param jsonData JSON 格式的数据列
     * @return 每一列的数据类型，Map 的键是列名，值是列的数据类型
     */
    public static Map<String, String> analyzeDataColumns(String jsonData) {
        // 解析 JSON 数据为 JSONArray
        JSONArray jsonArray = JSON.parseArray(jsonData);

        // 如果数据为空，抛出异常
        if (jsonArray == null || jsonArray.isEmpty()) {
            throw new IllegalArgumentException("数据列不能为空");
        }

        // 使用流处理每一列的数据
        return jsonArray.stream()
                .map(JSONObject.class::cast) // 将 JSONArray 中的每个元素转换为 JSONObject
                .collect(Collectors.toMap(
                        jsonObject -> jsonObject.getString("columnName"), // 键是列名
                        jsonObject -> {
                            // 获取列数据并分析数据类型
                            List<String> data = jsonObject.getJSONArray("data").toJavaList(String.class);
                            String dataType = DataAnalyzer.analyzeData(data.toArray(new String[0]));
                            return dataType;
                        }
                ));
    }
    /**
     * 分析多个列数据并返回 JSON 字符串
     * @param columns 数据列列表
     * @return JSON 字符串
     */
    public String analyzeMultipleColumns(List<ColumnData2> columns) {
        // 创建 JSON 数组来存储每列数据的分析结果
        JSONArray results = new JSONArray();

        // 遍历每一列数据并进行分析
        for (ColumnData2 column : columns) {
            // 分析当前列的数据
            String analysisResult = NormalityTestUtils.analyzeColumn(column.getData());

            // 创建 JSON 对象来存储当前列的分析结果
            JSONObject result = new JSONObject();
            result.put("colName", column.getColName());
            result.put("analysis", JSONObject.parseObject(analysisResult));

            // 将当前列的分析结果添加到 JSON 数组中
            results.add(result);
        }

        // 返回整个 JSON 数组作为字符串
        return results.toJSONString();
    }

    public static void main(String[] args) {
        // 示例数据：创建多列数据
        List<ColumnData2> columns = new ArrayList<>();
        columns.add(new ColumnData2("身高", new double[]{160, 165, 170, 175, 180, 185, 190}));
        columns.add(new ColumnData2("体重", new double[]{50, 55, 60, 65, 70, 75, 80}));

        // 创建服务实例并分析多列数据
        DataStatisticService service = new DataStatisticService();
        String s = service.analyzeMultipleColumns(columns);

        // 输出分析结果
        System.out.println(s);
    }
}
