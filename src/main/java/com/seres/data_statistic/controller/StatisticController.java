package com.seres.data_statistic.controller;
import com.seres.data_statistic.common.Result;
import com.seres.data_statistic.dto.Request2DTO;
import com.seres.data_statistic.dto.Request3DTO;
import com.seres.data_statistic.dto.Request4DTO;
import com.seres.data_statistic.dto.RequestDTO;
import com.seres.data_statistic.service.DataOverviewService;
import com.seres.data_statistic.service.DataStatisticService;
import com.seres.data_statistic.service.DataTypeAnalysisService;
import com.seres.data_statistic.vo.DataDescMutVO;
import com.seres.data_statistic.vo.DataOverviewVO;
import com.seres.data_statistic.vo.DataTypeVO;
import com.seres.data_statistic.vo.NormalTestVO;
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

}
