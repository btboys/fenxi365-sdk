package com.fenxi365.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用JSON返回格式
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 请求ID
     */
    private String reqId;

    /**
     * 响应编码
     */
    private int code = 200;

    /**
     * 响应状态
     */
    private boolean success = true;

    /**
     * 返回信息（如果是错误的时候，就是错误原因）
     */
    private String msg = "请求成功";

    /**
     * 响应数据
     */
    private final T data;

    private Result(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result<T> setReqId(String reqId) {
        this.reqId = reqId;
        return this;
    }

    public static Result<Boolean> success() {
        return new Result<>(true, 200, null, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(true, 200, null, data);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return new Result<>(true, 200, msg, data);
    }

    public static <T> Result<T> ok(String msg) {
        return new Result<>(true, 200, msg, null);
    }

    public static <T> Result<T> fail() {
        return new Result<>(false, 500, null, null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(false, 500, msg, null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(false, 500, null, data);
    }

    public static <T> Result<T> fail(T data, String msg) {
        return new Result<>(false, 500, msg, data);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(false, code, msg, null);
    }

    public static <T> Result<T> fail(int code, T data) {
        return new Result<>(false, code, null, data);
    }

    public static <T> Result<T> fail(int code, T data, String msg) {
        return new Result<>(false, code, msg, data);
    }
}
