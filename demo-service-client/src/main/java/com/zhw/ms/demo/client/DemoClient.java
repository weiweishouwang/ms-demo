package com.zhw.ms.demo.client;

import com.zhw.ms.demo.api.DemoAPI;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "demo-service")
public interface DemoClient extends DemoAPI {

}

