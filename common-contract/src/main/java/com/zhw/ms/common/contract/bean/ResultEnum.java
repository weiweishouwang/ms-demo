package com.zhw.ms.common.contract.bean;

/**
 * Created by hongweizou on 16/7/29.
 */
public enum ResultEnum {

    SUCCESS(1, "执行成功"),
    CALL_FAIL(-1, "服务调用失败"),
    IP_LIMIT(-2, "该IP被限制"),
    ERROR_ARGUMENT(-3, "参数错误"),
    ACCESS_TIMEOUT(-4, "访问超时"),
    SYSTEM_ERROR(-5, "系统出错，请联系客服"),
    DATA_SOURCE_ERROR(-6, "数据源注解未设置"),
    CALL_TIMEOUT(-7, "服务调用超时");

    public int code;
    public String message;

    private ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

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
}
