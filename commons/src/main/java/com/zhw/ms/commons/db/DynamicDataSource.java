package com.zhw.ms.commons.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * Created by ZHW on 2015/4/23.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory
            .getLogger(DynamicDataSource.class);

    @Override
    public Object determineCurrentLookupKey() {
        String type = DataSourceHandler.getDbType();
        logger.debug(type);
        return type;
    }

}
