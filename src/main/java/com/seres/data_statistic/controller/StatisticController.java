package com.seres.data_statistic.controller;

import com.seres.data_statistic.common.Result;
import com.seres.data_statistic.constains.CorrelationType;
import com.seres.data_statistic.dto.*;
import com.seres.data_statistic.service.*;
import com.seres.data_statistic.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jiangqs
 * @version 1.0
 * @Description:
 * @date 2024/7/24 14:24
 */

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private DataTypeAnalysisService dataTypeAnalysisService;

    @Autowired
    private DataStatisticService dataStatisticService;

    @Autowired
    private DataOverviewService dataOverviewService;

    @Autowired
    private CorrelationMethodSelectorService correlationMethodSelectorService;

    @Autowired
    private CorrelationService correlationService;

    @Autowired
    private HistogramCalculatorService histogramCalculatorService;


    @PostMapping("/dataTypeAnalysis")
    public Result<List<DataTypeVO>> analyzeDataColumns(@RequestBody RequestDTO data) {

        List<DataTypeVO> dataTypeS = dataTypeAnalysisService.analyzeDataColumns(data.getColumns());

        return new Result<>(200, "数据类型分析OK！", dataTypeS);
    }


    @PostMapping("/dataDesc")
    public Result<List<DataDescMutVO>> dataDesc(@RequestBody Request2DTO data) {

        List<DataDescMutVO> result = dataStatisticService.dataDesc(data.getColumns());

        return new Result<>(200, "数据描述性分析OK！", result);
    }

    @PostMapping("/dataOverview")
    public Result<List<DataOverviewVO>> dataOverview(@RequestBody Request3DTO data) {

        List<DataOverviewVO> result = dataOverviewService.analyzeData(data.getColumns());
        return new Result<>(200, "数据概览分析OK！", result);
    }

    @PostMapping("/dataNormal")
    public Result<List<NormalTestVO>> dataOverview(@RequestBody Request4DTO data) {

        List<NormalTestVO> result = dataStatisticService.normalityAnalysis(data.getColumns());
        return new Result<>(200, "数据正态性分析OK！", result);
    }
    @PostMapping("/calculateHistogram")
    public Result<List<CalculateHistogramVO>> calculateHistogram(
            @RequestBody Request4DTO data) {
        List<CalculateHistogramVO> result = histogramCalculatorService.calculateHistograms(data.getColumns(), data.getNumBins());

        return new Result<>(200, "计算直方图数据OK！", result);
    }
    @PostMapping("/determineCorrelationMethod")
    public Result<CorrelationMethodSelectorService.CorrelationMethod> determineCorrelationMethod(
            @RequestBody Request5DTO data) {

        CorrelationMethodSelectorService.CorrelationMethod result =
                correlationMethodSelectorService.determineCorrelation(data.getColumns());
        return new Result<>(200, "相关性分析算法推荐OK！", result);
    }

    @PostMapping("/correlation/pearson")
    public Result<List<CorrelationPairResultVO>> pearson(@RequestBody Request6DTO data) {

        List<CorrelationPairResultVO> result =
                correlationService.analyzeCorrelation(data.getColumns(), CorrelationType.PEARSON);

        return new Result<>(200, "Pearson相关性分析OK！", result);
    }

    @PostMapping("/correlation/spearman")
    public Result<List<CorrelationPairResultVO>> spearman(@RequestBody Request6DTO data) {

        List<CorrelationPairResultVO> result =
                correlationService.analyzeCorrelation(data.getColumns(), CorrelationType.SPEARMAN);

        return new Result<>(200, "Spearman相关性分析OK！", result);
    }

}
