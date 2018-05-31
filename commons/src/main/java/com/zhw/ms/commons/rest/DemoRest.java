package com.zhw.ms.commons.rest;

import com.zhw.ms.commons.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DemoRest {
    static final Logger logger = LoggerFactory
            .getLogger(DemoRest.class);


    @RequestMapping(value = "/getAdmin", method = RequestMethod.GET)
    public Result<Object> getAdmin(@RequestParam("id") Long id) {
        return new Result<>();
    }
}
