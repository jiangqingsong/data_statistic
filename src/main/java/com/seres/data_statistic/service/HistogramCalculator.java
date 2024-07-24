package com.seres.data_statistic.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
/**
 * @author jiangqs
 * @version 1.0
 * @Description: 计算数据各区间的频次
 * @date 2024/7/23 16:35
 */
@Service
public class HistogramCalculator {
    private static final Logger logger = LoggerFactory.getLogger(HistogramCalculator.class);
    /**
     * 计算数据的频次并输出各区域频次
     * @param data 输入的数据数组
     * @param numBins 区间的数量
     * @return 各区间及其频次的映射
     */
    public static Map<String, Integer> calculateHistogram(double[] data, int numBins) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("数据数组不能为空");
        }

        // 计算数据的最小值和最大值
        double min = Arrays.stream(data).min().orElse(Double.NaN);
        double max = Arrays.stream(data).max().orElse(Double.NaN);

        if (Double.isNaN(min) || Double.isNaN(max)) {
            throw new IllegalArgumentException("无法计算数据的最小值和最大值");
        }

        // 计算每个区间的宽度
        double binWidth = (max - min) / numBins;

        // 初始化区间频次映射
        Map<String, Integer> histogram = new HashMap<>();

        // 初始化区间
        for (int i = 0; i < numBins; i++) {
            double binStart = min + i * binWidth;
            double binEnd = binStart + binWidth;
            histogram.put(formatRange(binStart, binEnd), 0);
        }

        // 计算每个数据点所在的区间
        for (double value : data) {
            if (value == max) {
                // 特殊处理最大值，确保其在最后一个区间内
                histogram.put(formatRange(min + (numBins - 1) * binWidth, max + 1), histogram.getOrDefault(formatRange(min + (numBins - 1) * binWidth, max + 1), 0) + 1);
            } else {
                int binIndex = (int) Math.floor((value - min) / binWidth);
                if (binIndex >= numBins) {
                    binIndex = numBins - 1;
                }
                double binStart = min + binIndex * binWidth;
                double binEnd = binStart + binWidth;
                histogram.put(formatRange(binStart, binEnd), histogram.getOrDefault(formatRange(binStart, binEnd), 0) + 1);
            }
        }

        return histogram;
    }

    /**
     * 格式化区间范围为字符串
     * @param start 区间起始值
     * @param end 区间结束值
     * @return 格式化的区间范围
     */
    private static String formatRange(double start, double end) {
        return String.format("%.2f - %.2f", start, end);
    }

    public static void main(String[] args) {
        // 示例数据
        double[] data = {160, 165, 170, 175, 180, 185, 190};

        // 设定区间数量
        int numBins = 5;

        // 计算直方图
        Map<String, Integer> histogram = calculateHistogram(data, numBins);

        // 输出每个区间的频次
        for (Map.Entry<String, Integer> entry : histogram.entrySet()) {
            System.out.println("区间 " + entry.getKey() + " 的频次: " + entry.getValue());
        }
    }
}
