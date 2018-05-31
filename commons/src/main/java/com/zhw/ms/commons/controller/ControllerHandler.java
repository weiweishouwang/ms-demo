package com.zhw.ms.commons.controller;

import com.zhw.ms.commons.bean.Result;
import com.zhw.ms.commons.bean.ResultEnum;
import com.zhw.ms.commons.exception.JccException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/5/25 0025.
 */
//@ControllerAdvice
public class ControllerHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(ControllerHandler.class);

    /**
     * 处理异常，处理500错误
     *
     * @param exception
     * @param request
     * @return
     */
    //@ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException(Throwable exception, HttpServletRequest request) {
        logger.error(exception.getMessage(), exception);
        Result<Object> err = new Result<>();
        if (exception instanceof MissingServletRequestParameterException) {
            err.setRetCode(ResultEnum.ERROR_ARGUMENT.code);
            err.setRetMsg(ResultEnum.ERROR_ARGUMENT.message);
        } else if (exception instanceof JccException) {
            JccException e = (JccException) exception;
            err.setRetCode(e.getRetCode());
            err.setRetMsg(e.getRetMsg());
        } else {
            err.setRetCode(ResultEnum.SYSTEM_ERROR.code);
            err.setRetMsg(ResultEnum.SYSTEM_ERROR.message);
        }

        return err;
    }

}
