package com.seres.data_statistic.utils;


import com.alibaba.fastjson.JSONObject;
import com.seres.data_statistic.vo.NormalTestVO;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.Arrays;

/**
 * @author jiangqs
 * @version 1.0
 * @Description: 正态性检验工具类
 * @date 2024/7/23 15:46
 */
public class NormalityTestUtils {


    /**
     * 正态分析
     * @param data 数据数组
     * @return JSON 字符串
     */
    public static NormalTestVO normalityAnalysis(double[] data) {
        // 确保数据不为空
        if (data.length == 0) {
            throw new IllegalArgumentException("数据不能为空。");
        }

        // 计算描述性统计量
        DescriptiveStatistics stats = new DescriptiveStatistics(data);

        // 计算统计量
        double mean = stats.getMean();               // 平均值
        double median = stats.getPercentile(50);     // 中位数
        double stdDev = stats.getStandardDeviation(); // 标准差
        double skewness = stats.getSkewness();       // 偏度
        double kurtosis = stats.getKurtosis();       // 峰度

        // 进行 Shapiro-Wilk 检验
        double swW = performShapiroWilkTest(data);
        double swPValue = calculateShapiroWilkPValue(swW, data.length);

        // 进行 Kolmogorov-Smirnov 检验
        double ksD = performKolmogorovSmirnovTest(data, mean, stdDev);
        double ksPValue = calculateKolmogorovSmirnovPValue(ksD, data.length);

        NormalTestVO normalTestVO = new NormalTestVO();
        normalTestVO.setSampleSize(data.length);// 样本量
        normalTestVO.setMedian(median);// 中位数
        normalTestVO.setMean(mean);// 平均值
        normalTestVO.setStdDev(stdDev);// 标准差
        normalTestVO.setSkewness(skewness);// 偏度
        normalTestVO.setKurtosis(kurtosis);// 峰度
        normalTestVO.setSwW(swW);// Shapiro-Wilk 统计量
        normalTestVO.setSwPValue(swPValue);// Shapiro-Wilk 检验 p 值
        normalTestVO.setKsD(ksD);// Kolmogorov-Smirnov 统计量
        normalTestVO.setKsPValue(ksPValue); // Kolmogorov-Smirnov 检验 p 值

        return normalTestVO;
    }

    /**
     * 使用简化的算法进行 Shapiro-Wilk 检验
     * @param data 数据数组
     * @return Shapiro-Wilk 统计量（简化版）
     */
    private static double performShapiroWilkTest(double[] data) {
        int n = data.length;
        if (n < 3) {
            return Double.NaN; // 过少的数据无法进行有效的检验
        }

        // 对数据进行排序
        Arrays.sort(data);

        // 计算 W 统计量
        return calculateWStatistic(data);
    }

    /**
     * 计算 Shapiro-Wilk 统计量 W
     * @param data 数据数组
     * @return W 统计量
     */
    private static double calculateWStatistic(double[] data) {
        int n = data.length;
        double[] a = new double[n]; // Shapiro-Wilk 常数 (通常需要查表或使用预计算值)
        double[] z = new double[n];

        // 计算正态分布的累积分布函数值
        NormalDistribution normalDist = new NormalDistribution();
        for (int i = 0; i < n; i++) {
            z[i] = normalDist.cumulativeProbability(data[i]);
        }

        // 计算 W 统计量的简化示例
        // 实际计算需要查表或者使用准确的常数
        double W = 1.0;
        for (int i = 0; i < n; i++) {
            W -= Math.abs(z[i] - (i + 1.0) / n);
        }

        return W;
    }

    /**
     * 计算 Shapiro-Wilk 检验的 p 值（简化版）
     * @param W Shapiro-Wilk 统计量
     * @param n 样本量
     * @return p 值
     */
    private static double calculateShapiroWilkPValue(double W, int n) {
        // 这里使用简化的 p 值计算，实际应用中需要查表或复杂的公式
        // 这里是一个示意值，实际计算需要精确的统计方法
        return Math.max(0.0, 1 - Math.exp(-W * n)); // 这里只是一个示意，实际应用中需要更复杂的计算
    }

    /**
     * 使用简化的算法进行 Kolmogorov-Smirnov 检验
     * @param data 数据数组
     * @param mean 均值
     * @param stdDev 标准差
     * @return Kolmogorov-Smirnov 统计量
     */
    private static Double performKolmogorovSmirnovTest(double[] data, double mean, double stdDev) {
        int n = data.length;
        double[] sortedData = Arrays.copyOf(data, n);
        Arrays.sort(sortedData);

        Double D = null; // 使用 Double 对象，以便可以为 null

        try {
            // 检查标准差是否为零，避免异常
            if (stdDev <= 0) {
                return null; // 返回 null 表示无法计算
            }

            // 计算理论分布的累积分布函数值
            NormalDistribution normalDist = new NormalDistribution(mean, stdDev);
            double[] cdfValues = new double[n];
            for (int i = 0; i < n; i++) {
                cdfValues[i] = normalDist.cumulativeProbability(sortedData[i]);
            }

            // 计算经验分布函数值
            double[] empiricalCdf = new double[n];
            for (int i = 0; i < n; i++) {
                empiricalCdf[i] = (i + 1.0) / n;
            }

            // 计算 D 统计量（最大差值）
            D = 0.0;
            for (int i = 0; i < n; i++) {
                D = Math.max(D, Math.abs(empiricalCdf[i] - cdfValues[i]));
            }
        } catch (NotStrictlyPositiveException e) {
            // 捕获标准差不正的异常，并返回 null
            return null;
        }

        return D;
    }

    /**
     * 计算 Kolmogorov-Smirnov 检验的 p 值（简化版）
     * @param D Kolmogorov-Smirnov 统计量
     * @param n 样本量
     * @return p 值
     */
    private static double calculateKolmogorovSmirnovPValue(double D, int n) {
        // 计算 p 值的简化版本
        // 实际的 p 值计算需要使用更精确的统计方法
        // 这里使用近似的公式
        return Math.exp(-2 * D * D * n); // 这里只是一个示意，实际应用中需要更复杂的计算
    }

    public static void main(String[] args) {
        // 示例数据：身高（单位：厘米）
        double[] heightData = {160, 165, 170, 175, 180, 185, 190}; // 身高数据范围通常在150到200 cm之间

        // 示例数据：体重（单位：公斤）
        double[] weightData = {50, 55, 60, 65, 70, 75, 80}; // 体重数据范围通常在40到100 kg之间

        // 对每列数据进行分析并输出结果
        System.out.println("身高数据分析: " + NormalityTestUtils.normalityAnalysis(heightData));
        System.out.println("体重数据分析: " + NormalityTestUtils.normalityAnalysis(weightData));
    }
}
