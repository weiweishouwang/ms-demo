
package com.zhw.ms.common.contract.exception;

import com.zhw.ms.common.contract.bean.ResultEnum;

/**
 * 项目共通异常
 * Created by ZHW on 2015/5/11.
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * 返回码
     */
    private int retCode = ResultEnum.SUCCESS.code;
    /**
     * 返回信息
     */
    private String retMsg = ResultEnum.SUCCESS.message;

    public BusinessException(int retCode, String retMsg) {
        super(retCode + ":" + retMsg);
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public BusinessException(int retCode, String retMsg, Throwable cause) {
        super(retCode + ":" + retMsg, cause);
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.code + ":" + resultEnum.message);
        this.retCode = resultEnum.code;
        this.retMsg = resultEnum.message;
    }

    public BusinessException(ResultEnum resultEnum, Throwable cause) {
        super(resultEnum.code + ":" + resultEnum.message, cause);
        this.retCode = resultEnum.code;
        this.retMsg = resultEnum.message;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
