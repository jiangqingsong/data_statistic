package com.seres.data_statistic.constains;

public enum DataType {
    CATEGORICAL("定类", "Categorical"),
    QUANTITATIVE("定量", "Quantitative");

    private final String chineseName;
    private final String englishName;

    DataType(String chineseName, String englishName) {
        this.chineseName = chineseName;
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }
}
