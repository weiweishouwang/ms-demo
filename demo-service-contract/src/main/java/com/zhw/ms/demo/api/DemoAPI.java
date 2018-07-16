package com.zhw.ms.demo.api;

import com.zhw.ms.common.contract.bean.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface DemoAPI {

    @RequestMapping(value = "/getAdmin", method = RequestMethod.GET)
    public Result<Object> getAdmin(@RequestParam("id") Long id);

    @RequestMapping(value = "/throwException", method = RequestMethod.GET)
    public Result<Object> throwException() throws Exception;

}
