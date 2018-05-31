package com.zhw.ms.demo.db;

import com.zhw.ms.commons.db.DataSourceHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 数据源切换AOP
 * Created by ZHW on 2015/5/11.
 */
@Aspect
@Component
public class DataSourceHandlerImpl extends DataSourceHandler implements Ordered {
    /**
     * @param joinPoint
     */
    @Before("execution(* com.zhw.ms.demo.service.*Service.*(..))")
    public void changeDataSource(JoinPoint joinPoint) {
        super.changeDataSource(joinPoint);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
