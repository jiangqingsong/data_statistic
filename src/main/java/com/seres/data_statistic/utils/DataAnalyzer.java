package com.seres.data_statistic.utils;
import com.seres.data_statistic.constains.DataType;

import java.util.HashSet;
import java.util.Set;
/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/23 14:45
 */
public class DataAnalyzer {

    /**
     * 判断数据列是定类还是定量
     *
     * @param data 数据列，以字符串数组的形式传入
     * @return 数据列类型，"Categorical"（定类）或"Quantitative"（定量）
     */
    public static String analyzeData(String[] data) {
        // 检查数据是否为空或长度为0
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("数据列不能为空");
        }

        boolean hasNonNumeric = false; // 标记是否有非数值型数据
        Set<String> uniqueValues = new HashSet<>(); // 存储唯一的非数值型数据

        // 遍历数据列
        for (String value : data) {
            // 判断当前数据是否为数值型
            if (isNumeric(value)) {
                // 数值型数据，继续处理
                continue;
            } else {
                // 非数值型数据
                hasNonNumeric = true;
            }
            uniqueValues.add(value); // 记录唯一的非数值型数据
        }

        // 如果有非数值型数据并且唯一值数量相对较多，则认为是定类
        if (hasNonNumeric && uniqueValues.size() > data.length * 0.5) {
            return DataType.CATEGORICAL.getChineseName(); // 定类
        } else {
            return DataType.QUANTITATIVE.getChineseName(); // 定量
        }
    }

    /**
     * 判断一个字符串是否可以转换为数值型
     *
     * @param str 需要判断的字符串
     * @return 如果可以转换为数值型则返回 true，否则返回 false
     */
    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false; // 空字符串不能转换为数值
        }
        try {
            Double.parseDouble(str); // 尝试将字符串转换为双精度浮点数
            return true;
        } catch (NumberFormatException e) {
            return false; // 如果发生异常，说明不能转换为数值
        }
    }

    public static void main(String[] args) {
        // 测试
        String[] data1 = {"10", "20", "30", "40", "50"};
        String[] data2 = {"Red", "Blue", "Green", "Red", "Blue"};
        String[] data3 = {"10", "20", "Hello", "40", "50"};

        // 打印测试结果
        System.out.println("Data1 是: " + analyzeData(data1)); // 应该是 Quantitative（定量）
        System.out.println("Data2 是: " + analyzeData(data2)); // 应该是 Categorical（定类）
        System.out.println("Data3 是: " + analyzeData(data3)); // 应该是 Categorical（定类）
    }
}
