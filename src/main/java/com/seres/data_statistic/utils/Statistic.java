package com.seres.data_statistic.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.stat.inference.TestUtils;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/23 12:18
 */
public class Statistic {

    /**
     * @description: 描述性统计分析
     * @date: 2024/7/23 19:44
     * @param: * @param: null
     * @return: * @return: null
     */
    public static <T extends Number> String analyze(T[] data) {
        // Filter out null values and convert input data to double array
        double[] doubleData = Arrays.stream(data)
                .filter(num -> num != null)
                .mapToDouble(Number::doubleValue)
                .toArray();

        // Calculate missing values
        long missingCount = Arrays.stream(data).filter(num -> num == null).count();

        // Calculate unique values
        Set<Double> uniqueValues = new HashSet<>();
        for (double num : doubleData) {
            uniqueValues.add(num);
        }

        // Create DescriptiveStatistics object
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double num : doubleData) {
            stats.addValue(num);
        }

        // Box plot values
        Percentile percentile = new Percentile();
        percentile.setData(doubleData);
        double q1 = percentile.evaluate(25);
        double q3 = percentile.evaluate(75);
        double iqr = q3 - q1;
        double boxMin = Arrays.stream(doubleData).filter(v -> v >= q1 - 1.5 * iqr).min().orElse(Double.NaN);
        double boxMax = Arrays.stream(doubleData).filter(v -> v <= q3 + 1.5 * iqr).max().orElse(Double.NaN);

        // Shapiro-Wilk test for normality using JDistlib
//        double swStatistic = NormalityTest.shapiroWilk(doubleData);

        // Calculate kurtosis and skewness
        Kurtosis kurtosis = new Kurtosis();
        Skewness skewness = new Skewness();

        // Create JSON object with descriptive statistics
        JSONObject json = new JSONObject();
        json.put("sampleCount", stats.getN());
        json.put("missingCount", missingCount);
        json.put("uniqueCount", uniqueValues.size());
        json.put("mean", stats.getMean());
        json.put("standardDeviation", stats.getStandardDeviation());
        json.put("max", stats.getMax());
        json.put("min", stats.getMin());
        json.put("median", stats.getPercentile(50));
        json.put("coefficientOfVariation", stats.getStandardDeviation() / stats.getMean());
        json.put("variance", stats.getVariance());
//        json.put("shapiroWilk", swStatistic);
        json.put("kurtosis", kurtosis.evaluate(doubleData));
        json.put("skewness", skewness.evaluate(doubleData));
        json.put("boxPlotMax", boxMax);
        json.put("boxPlotMin", boxMin);
        json.put("q1", q1);
        json.put("q3", q3);

        return json.toJSONString();
    }


    // 计算 Pearson 相关系数
    public static double pearsonCorrelation(double[] x, double[] y) {
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        return pearsonsCorrelation.correlation(x, y);
    }

    // 计算 Spearman 相关系数
    public static double spearmanCorrelation(double[] x, double[] y) {
        SpearmansCorrelation spearmansCorrelation = new SpearmansCorrelation();
        return spearmansCorrelation.correlation(x, y);
    }

    // 计算 Kendall’s tau-b 相关系数
    public static double kendallTauB(double[] x, double[] y) {
        return 0;
    }

    public static void main(String[] args) {
        Integer[] intData = {1, 2, 3, 4, 5, null, 5};
        Double[] doubleData = {1.0, 2.0, 3.0, 4.0, 5.0, null, 5.0};

        System.out.println("Analysis (int): " + analyze(intData));
        System.out.println("Analysis (double): " + analyze(doubleData));
    }
}
