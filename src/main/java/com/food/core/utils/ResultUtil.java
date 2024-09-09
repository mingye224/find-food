package com.food.core.utils;

import com.food.core.enums.ResponseCodeMessage;
import org.springframework.stereotype.Component;

@Component
public class ResultUtil {
    /**
     * 最终返回 指定是否加密
     *
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(ResponseCodeMessage.SUCCESS.getCode(), msg, data);
    }

    /**
     *
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseCodeMessage.SUCCESS.getCode(), ResponseCodeMessage.SUCCESS.getMessage(), data);
    }

    /**
     *
     *
     * @return
     */
    public static <T> Result<T> success() {
        return new Result<>(ResponseCodeMessage.SUCCESS.getCode(), ResponseCodeMessage.SUCCESS.getMessage());
    }
    /**
     * 返回错误状态，自定义message
     *
     * @param codeMess
     * @param msg
     * @return
     */
    public static <T> Result<T> error(ResponseCodeMessage codeMess, String msg) {
        return new Result<>(codeMess.getCode(), msg);
    }

    /**
     * 返回错误状态
     *
     * @param codeMess
     * @return
     */
    public static <T> Result<T> error(ResponseCodeMessage codeMess) {
        return new Result<>(codeMess.getCode(), codeMess.getMessage());
    }
}
