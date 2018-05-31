package com.zhw.ms.commons.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

/**
 * 数据源切换AOP
 * Created by ZHW on 2015/5/11.
 */
//@Aspect
//@Component
public class DataSourceHandlerImpl extends DataSourceHandler implements Ordered {
    @Pointcut("@annotation(com.jucaicat.project.tc.commons.db.MasterDataSource)")
    public void pointCutMaster() {
    }

    @Pointcut("@annotation(com.jucaicat.project.tc.commons.db.SlaverDataSource)")
    public void pointCutSlaver() {
    }

    /**
     * @param joinPoint 切点
     */
    //@Before("pointCutMaster() || pointCutSlaver()")
    @Before("execution(* com.jucaicat.project.springboot.web.service.*Service.*(..))")
    public void changeDataSource(JoinPoint joinPoint) {
        super.changeDataSource(joinPoint);

        /*String methord = joinPoint.getSignature().getName();

        if (methord.startsWith("save") || methord.startsWith("add")
                || methord.startsWith("create") || methord.startsWith("insert")
                || methord.startsWith("update") || methord.startsWith("edit")
                || methord.startsWith("merge") || methord.startsWith("del")
                || methord.startsWith("remove") || methord.startsWith("put")
                || methord.startsWith("use") || methord.startsWith("import")
                || methord.startsWith("execute") || methord.startsWith("send")
                || methord.startsWith("modify")|| methord.startsWith("login")
                || methord.startsWith("regist")) {
            DataSourceHandler.setDbType(DataSourceHandlerImpl.MASTER_DS);
        } else {
            DataSourceHandler.setDbType(DataSourceHandlerImpl.SLAVE_DS);
        }*/
    }

    @After("execution(* com.jucaicat.project.springboot.web.service.*Service.*(..))")
    public void resetDataSource(JoinPoint joinPoint) {
        super.resetDataSource(joinPoint);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
