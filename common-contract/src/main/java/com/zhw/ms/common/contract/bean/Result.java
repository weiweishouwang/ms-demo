package com.zhw.ms.common.contract.bean;

import java.io.Serializable;

/**
 * 通用返回值
 * Created by Administrator on 2016/2/25 0025.
 */
public class Result<T> implements Serializable {
    private boolean success = true;
    /**
     * 返回码
     */
    private int retCode = ResultEnum.SUCCESS.code;
    /**
     * 返回信息
     */
    private String retMsg = ResultEnum.SUCCESS.message;
    /**
     * 业务数据
     */
    private T data;

    public Result() {
    }

    public Result(ResultEnum resultEnum) {
        setRetCode(resultEnum.getCode());
        setRetMsg(resultEnum.getMessage());
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        if (retCode < 0) {
            this.success = false;
        } else {
            this.success = true;
        }
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
