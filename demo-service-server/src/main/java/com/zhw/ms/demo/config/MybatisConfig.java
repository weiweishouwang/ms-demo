package com.zhw.ms.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Component
@MapperScan(basePackages = {"com.zhw.ms.demo.persistence"})
public class MybatisConfig {
}
