package com.example.springbootmall.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(200, "success", data);
    }

    public static <T> CommonResult<T> success(T data, String msg) {
        return new CommonResult<T>(200, msg, data);
    }

    public static <T> CommonResult<T> failed(String msg) {
        return new CommonResult<T>(500, msg, null);
    }

    public static <T> CommonResult<T> failed(int code, String msg) {
        return new CommonResult<T>(code, msg, null);
    }

    public static <T> CommonResult<T> unauthorized(String msg) {
        return new CommonResult<T>(401, msg, null);
    }

    public static <T> CommonResult<T> forbidden(String msg) {
        return new CommonResult<T>(403, msg, null);
    }
}
