package com.zhw.ms.base.client.proxy;

import com.zhw.ms.common.contract.bean.Result;
import com.zhw.ms.common.contract.bean.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zouhongwei on 16/9/16.
 */
public class BaseClientProxy {
    private static final Logger logger = LoggerFactory
            .getLogger(BaseClientProxy.class);

    /*protected <T> Result<T> backFallback(Throwable t) {
        logger.error(t.getMessage(), t);
        if (t instanceof HystrixTimeoutException) {
            return new Result<>(ResultEnum.CALL_TIMEOUT);
        }

        return new Result<>(ResultEnum.CALL_FAIL);
    }*/

}