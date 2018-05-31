package com.zhw.ms.commons.db;

import com.zhw.ms.commons.bean.ResultEnum;
import com.zhw.ms.commons.exception.JccException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 数据源切换AOP
 * Created by ZHW on 2015/5/11.
 */
public abstract class DataSourceHandler {
    /**
     * 主库
     */
    public static final String MASTER_DS = "masterDS";
    /**
     * 从库
     */
    public static final String SLAVE_DS = "slaveDS";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    private static final ThreadLocal<Boolean> isBegin = new ThreadLocal<Boolean>();

    public static String getDbType() {
        return contextHolder.get();
    }

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static void clearDbType() {
        contextHolder.remove();
    }

    /**
     * @param joinPoint 切点
     */
    public void changeDataSource(JoinPoint joinPoint) {
        Boolean begin = isBegin.get();
        if (begin == null || begin.equals(Boolean.FALSE)) {
            isBegin.set(Boolean.TRUE);
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            String modify = Modifier.toString(method.getModifiers());

            if ("public".equals(modify)) {
                if (method.isAnnotationPresent(MasterDataSource.class)) {
                    DataSourceHandler.setDbType(DataSourceHandlerImpl.MASTER_DS);
                } else if (method.isAnnotationPresent(SlaverDataSource.class)) {
                    DataSourceHandler.setDbType(DataSourceHandlerImpl.SLAVE_DS);
                } else {
                    throw new JccException(ResultEnum.DATA_SOURCE_ERROR);
                }
            } else {
                DataSourceHandler.setDbType(DataSourceHandlerImpl.SLAVE_DS);
            }
        }
    }

    public void resetDataSource(JoinPoint joinPoint) {
        isBegin.set(Boolean.FALSE);
    }

}
