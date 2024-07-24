package com.seres.data_statistic.common;

/**
 * @author jiangqs
 * @version 1.0
 * @Description: 响应结果封装工具类
 * @date 2024/7/24 14:20
 */
public class Result<T> {
    private int code; // 状态码
    private String message; // 提示信息
    private T data; // 返回的数据

    // 构造函数
    public Result() {}

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功时的响应
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "Success", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    // 失败时的响应
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
