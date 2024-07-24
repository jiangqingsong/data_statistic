package com.seres.data_statistic.service;

import com.seres.data_statistic.dto.DataOverviewDTO;
import com.seres.data_statistic.vo.DataOverviewVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/24 18:21
 */
@Service
public class DataOverviewService {


    /**
    * @Description 数据概览分析
    * @Param
    * @Return
    */
    public List<DataOverviewVO> analyzeData(List<DataOverviewDTO> columns) {
        List<DataOverviewVO> results = new ArrayList<>();

        for (DataOverviewDTO column : columns) {
            DataOverviewVO result = new DataOverviewVO();
            result.setColName(column.getColName());
            try {
                List<Double> numericData = column.getData().stream().map(Double::valueOf).collect(Collectors.toList());
                analyzeQuantitativeData(numericData, result);
            } catch (NumberFormatException e) {
                analyzeQualitativeData(column.getData(), result);
            }
            results.add(result);
        }

        return results;
    }


    private static void analyzeQuantitativeData(List<Double> data, DataOverviewVO result) {
        Collections.sort(data);
        result.setSampleCount(data.size());
        result.setMissingCount((int) data.stream().filter(Objects::isNull).count());
        result.setUniqueCount((int) data.stream().distinct().count());
        result.setMin(data.get(0));
        result.setMax(data.get(data.size() - 1));
        result.setMean(data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
        result.setMedian(data.get(data.size() / 2));
        result.setVariance(data.stream().mapToDouble(val -> Math.pow(val - result.getMean(), 2)).sum() / data.size());
        result.setStandardDeviation(Math.sqrt(result.getVariance()));
        result.setCoefficientOfVariation(result.getStandardDeviation() / result.getMean());
        result.setSkewness((3 * (result.getMean() - result.getMedian())) / result.getStandardDeviation());
        result.setKurtosis(data.stream().mapToDouble(val -> Math.pow((val - result.getMean()) / result.getStandardDeviation(), 4)).sum() / data.size() - 3);

        result.setQ1(data.get(data.size() / 4));
        result.setQ3(data.get(3 * data.size() / 4));
        result.setBoxPlotMin(result.getQ1() - 1.5 * (result.getQ3() - result.getQ1()));
        result.setBoxPlotMax(result.getQ3() + 1.5 * (result.getQ3() - result.getQ1()));
    }

    private static void analyzeQualitativeData(List<String> data, DataOverviewVO result) {
        result.setSampleCount(data.size());
        result.setMissingCount((int) data.stream().filter(Objects::isNull).count());
        result.setUniqueCount((int) data.stream().distinct().count());

        result.setBoxPlotMin(null);
        result.setQ1(null);
        result.setQ3(null);
        result.setMax(null);
        result.setCoefficientOfVariation(null);
        result.setKurtosis(null);
        result.setMin(null);
        result.setMedian(null);
        result.setVariance(null);
        result.setMean(null);
        result.setSkewness(null);
        result.setStandardDeviation(null);
        result.setBoxPlotMax(null);
    }
}
