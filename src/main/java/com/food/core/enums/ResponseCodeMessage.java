package com.food.core.enums;

import org.springframework.lang.Nullable;

public enum ResponseCodeMessage {
    //code定义为4位数，前两位定义类型，后两位为子类型
    SUCCESS(0, "请求成功"),
    //参数类的错误以10开头
    PARAMETER_ERROR(1001,"参数错误"),
    PARAMETER_ID_NOT_EXIST(1002,"id值不存在"),
    //DB数据类的错误, 11开头
    DATA_NOT_EXIST(1101, "数据不存在"),
    DATA_STATUS_ERROR(1102, "数据状态错误"),
    DATA_SAVE_ERROR(1103, "数据保存错误"),
    DATA_UP_ERROR(1104, "数据更新错误"),
    //TOKEN等用户登录态相关的20开头
    /**
     * 没有登录或者登录已经过期
     */
    TOKEN_INVALID(2001,"Token失效"),
    TOKEN_NOT_EXIST(2002, "Token不存在"),
    //验证码相关
    VERIFY_CODE_FREQUENTLY(2010,"获取验证码过于频繁"),
    VERIFY_CODE_FAILED(2011,"验证码校验失败"),
    VERIFY_CODE_EXPIRE(2012,"验证码过期"),
    //token 校验不通过
    TOKEN_NOT_PASS(2031,"token校验失败"),
    NOT_LOGIN(2050, "没有登录或者登录已经过期"),
    //权限相关的错误, 21开头
    NO_PERMISSION(2101,"无权限执行此操作"),
    //文件操作类的错误，22开头
    FILE_SAVE_ERROR(2201,"保存文件出错"),
    FILE_READ_ERROR(2202,"读取文件出错"),
    FILE_DELETE_ERROR(2203,"删除文件出错"),
    //http请求其他服务时产生的错误，80开头
    HTTP_CLIENT_ERROR(8001,"http请求错误"),
    HTTP_CLIENT_RETURN_ERROR(8002,"http请求返回的内容错误"),
    //获取http数据错误
    HTTP_ATTRIBUTES_ERROR(8003, "获取http属性错误"),
    //资源释放类的错误，81开头
    CLOSE_ERROR(8101,"资源释放错误"),
    //oss操作类的错误，82开头
    OSS_UP_ERROR(8201,"上传oss错误"),
    OSS_DOWN_ERROR(8202,"oss下载错误"),
    OSS_DEL_ERROR(8203,"oss文件删除错误"),

    //90开头一般是不能预知的错误，经有AOP捕获后会发出通知
    SYSTEM_UNKNOWN_ERROR(9001, "系统内部未知错误"),
    //远程调用相关50开头
    SERVICE_ERROR(5000,"服务异常,暂时熔断！");
    private final int code;
    private final String message;

    private ResponseCodeMessage(int value, String message) {
        this.code = value;
        this.message = message;
    }

    public static ResponseCodeMessage valueOf(int statusCode) {
        ResponseCodeMessage status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        } else {
            return status;
        }
    }

    @Nullable
    public static ResponseCodeMessage resolve(int statusCode) {
        ResponseCodeMessage[] vals = values();
        int len = vals.length;

        for(int i = 0; i < len; ++i) {
            ResponseCodeMessage status = vals[i];
            if (status.code == statusCode) {
                return status;
            }
        }

        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
