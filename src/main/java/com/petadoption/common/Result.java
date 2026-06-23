package com.petadoption.common;

/**
 * 统一返回结果类
 */
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public Result() {}

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功 - 无数据
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    // 成功 - 有数据
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    // 失败
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    // Getter 和 Setter
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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