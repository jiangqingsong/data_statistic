package com.seres.data_statistic.service;

import com.seres.data_statistic.constains.CorrelationMethod;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

import java.util.HashSet;
import java.util.Set;


/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/23 20:08
 */
public class CorrelationMethodSelector {
    public static void main(String[] args) {
        // 示例数据
//        Object[] data1 = {1, 2, 3, 4, 5}; // 定量数据
        Object[] data2 = {"A", "B", "C", "A", "B"}; // 定类数据
        Object[] data1 = {"A", "B", "C", "A", "B"}; // 定类数据

        CorrelationMethod method = determineCorrelationMethod(data1, data2);
        System.out.println("推荐的相关性分析方法: " + method);
    }

    /**
     * 确定适合的数据相关性分析方法
     *
     * @param data1 第一列数据，可以是定量或定类数据
     * @param data2 第二列数据，可以是定量或定类数据
     * @return 推荐的相关性分析方法，使用 CorrelationMethod 枚举表示
     */
    public static CorrelationMethod determineCorrelationMethod(Object[] data1, Object[] data2) {
        boolean data1IsNumerical = isNumerical(data1);
        boolean data2IsNumerical = isNumerical(data2);
        boolean data1IsCategorical = isCategorical(data1);
        boolean data2IsCategorical = isCategorical(data2);

        // 如果两列数据都是定量数据
        if (data1IsNumerical && data2IsNumerical) {
            boolean data1IsNormal = isNormal(toDoubleArray(data1));
            boolean data2IsNormal = isNormal(toDoubleArray(data2));
            if (data1IsNormal && data2IsNormal) {
                return CorrelationMethod.PEARSON; // 使用 Pearson 相关性分析
            } else {
                return CorrelationMethod.SPEARMAN; // 使用 Spearman 相关性分析
            }
        }
        // 如果一列数据是定量数据，另一列是定类数据
        else if ((data1IsNumerical && data2IsCategorical) || (data1IsCategorical && data2IsNumerical)) {
            return CorrelationMethod.SPEARMAN; // 使用 Spearman 相关性分析
        }
        // 如果两列数据都是定类数据
        else if (data1IsCategorical && data2IsCategorical) {
            return CorrelationMethod.KENDALL; // 使用 Kendall's tau-b 相关性分析
        } else {
            return CorrelationMethod.KENDALL; // 无法确定数据类型
        }
    }

    /**
     * 判断数据是否为数值类型
     *
     * @param data 输入数据
     * @return 如果数据是数值类型，返回 true，否则返回 false
     */
    public static boolean isNumerical(Object[] data) {
        for (Object obj : data) {
            if (!(obj instanceof Number)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数据是否为定类数据
     *
     * @param data 输入数据
     * @return 如果数据是定类数据，返回 true，否则返回 false
     */
    public static boolean isCategorical(Object[] data) {
        Set<Object> uniqueValues = new HashSet<>();
        for (Object obj : data) {
            if (obj instanceof String || obj instanceof Character) {
                uniqueValues.add(obj);
            } else if (obj instanceof Number) {
                return false;
            }
        }
        // 如果数据中唯一值的数量小于总数量的一半，则认为是定类数据
        return uniqueValues.size() < data.length / 2;
    }

    /**
     * 判断数据是否符合正态分布
     *
     * @param data 输入数据，必须为数值类型
     * @return 如果数据符合正态分布，返回 true，否则返回 false
     */
    public static boolean isNormal(double[] data) {
        if (isConstant(data)) {
            return false;
        }
        KolmogorovSmirnovTest ksTest = new KolmogorovSmirnovTest();
        NormalDistribution normalDist = new NormalDistribution(mean(data), stddev(data));
        double pValue = ksTest.kolmogorovSmirnovTest(normalDist, data);
        return pValue > 0.05;
    }

    /**
     * 判断数据是否为常数数据
     *
     * @param data 输入数据，必须为数值类型
     * @return 如果数据所有值都相同，返回 true，否则返回 false
     */
    public static boolean isConstant(double[] data) {
        double firstValue = data[0];
        for (double value : data) {
            if (value != firstValue) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算数据的均值
     *
     * @param data 输入数据，必须为数值类型
     * @return 数据的均值
     */
    public static double mean(double[] data) {
        double sum = 0.0;
        for (double a : data) {
            sum += a;
        }
        return sum / data.length;
    }

    /**
     * 计算数据的标准差
     *
     * @param data 输入数据，必须为数值类型
     * @return 数据的标准差
     */
    public static double stddev(double[] data) {
        double mean = mean(data);
        double sum = 0.0;
        for (double a : data) {
            sum += (a - mean) * (a - mean);
        }
        return Math.sqrt(sum / (data.length - 1));
    }

    /**
     * 将 Object 类型数组转换为 double 类型数组
     *
     * @param data 输入数据
     * @return 转换后的 double 类型数组
     */
    public static double[] toDoubleArray(Object[] data) {
        double[] doubleArray = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            doubleArray[i] = ((Number) data[i]).doubleValue();
        }
        return doubleArray;
    }
}
