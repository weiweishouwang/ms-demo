package com.zhw.ms.demo.proxy;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.zhw.ms.base.client.proxy.BaseClientProxy;
import com.zhw.ms.common.contract.bean.Result;
import com.zhw.ms.common.contract.bean.ResultEnum;
import com.zhw.ms.demo.api.DemoAPI;
import com.zhw.ms.demo.client.DemoClient;
import com.zhw.ms.demo.entity.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class DemoClientProxy extends BaseClientProxy implements DemoAPI {
    private static final Logger logger = LoggerFactory
            .getLogger(DemoClientProxy.class);

    @Autowired
    private DemoClient demoClient;


    @Override
    @HystrixCommand(fallbackMethod = "getAdminFallback")
    public Result<Admin> getAdmin(Long id) {
        return demoClient.getAdmin(id);
    }

    @Override
    public Result<Admin> throwException() {
        return null;
    }

    @HystrixCommand(fallbackMethod = "getAdminFallback")
    public Future<Result<Admin>> getAdminAsync(Long id) {
        return new AsyncResult<Result<Admin>>() {
            @Override
            public Result<Admin> invoke() {
                return demoClient.getAdmin(id);
            }
        };
    }

    public Result<Admin> getAdminFallback(Long id, Throwable t) {
        return backFallback(t);
    }

}
