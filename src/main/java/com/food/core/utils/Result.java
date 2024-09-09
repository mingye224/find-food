package com.food.core.utils;

import lombok.Data;

/**
 * @description: 全局返回包装
 * mpq
 **/
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result() {
    }

    Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
