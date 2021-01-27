package com.zhw.ms.demo.client;


import com.zhw.ms.demo.api.DemoAPI;

@FeignClient(name = "demo-service")
public interface DemoClient extends DemoAPI {

}

