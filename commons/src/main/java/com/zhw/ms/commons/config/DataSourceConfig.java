package com.zhw.ms.commons.config;

import com.zhw.ms.commons.db.DataSourceHandler;
import com.zhw.ms.commons.db.DynamicDataSource;
import com.zhw.ms.commons.properties.DataSourceProperties;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Configuration
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory
            .getLogger(DataSourceConfig.class);

    @Autowired
    Environment env;

    @Autowired
    private DataSourceProperties properties;

    private DataSource createJndiDataSource(String jndiName) {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName(jndiName);
        try {
            bean.afterPropertiesSet();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return (DataSource) bean.getObject();
    }

    private DataSource createMasterDataSource() {
        /*DataSourceBuilder factory = DataSourceBuilder
                .create(getClass().getClassLoader())
                .driverClassName(properties.getMasterDriverClassName())
                .url(properties.getMasterUrl())
                .username(properties.getMasterUsername())
                .password(properties.getMasterPassword());

          return factory.build();*/

        PoolProperties poolProps = new PoolProperties();
        poolProps.setDriverClassName(properties.getMasterDriverClassName());
        poolProps.setUrl(properties.getMasterUrl());
        poolProps.setUsername(properties.getMasterUsername());
        poolProps.setPassword(properties.getMasterPassword());
        poolProps.setTestWhileIdle(true);
        poolProps.setTestOnBorrow(true);
        poolProps.setValidationQuery("select 1");
        poolProps.setValidationInterval(30000);
        poolProps.setTimeBetweenEvictionRunsMillis(30000);
        poolProps.setLogAbandoned(true);
        poolProps.setRemoveAbandoned(true);
        poolProps.setRemoveAbandonedTimeout(60);
        poolProps.setMaxWait(10000);
        poolProps.setNumTestsPerEvictionRun(50);
        poolProps.setMinEvictableIdleTimeMillis(30000);
        poolProps.setMaxActive(properties.getMasterMaxActive());
        poolProps.setMaxIdle(properties.getMasterMaxIdle());
        poolProps.setMinIdle(properties.getMasterMinIdle());
        poolProps.setInitialSize(properties.getMasterInitialSize());

        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setPoolProperties(poolProps);

        return dataSource;
    }

    private DataSource createSlaverDataSource() {
        /*DataSourceBuilder factory = DataSourceBuilder
                .create(getClass().getClassLoader())
                .driverClassName(properties.getSlaverDriverClassName())
                .url(properties.getSlaverUrl())
                .username(properties.getSlaverUsername())
                .password(properties.getSlaverPassword());
        return factory.build();*/

        PoolProperties poolProps = new PoolProperties();
        poolProps.setDriverClassName(properties.getSlaverDriverClassName());
        poolProps.setUrl(properties.getSlaverUrl());
        poolProps.setUsername(properties.getSlaverUsername());
        poolProps.setPassword(properties.getSlaverPassword());
        poolProps.setTestWhileIdle(true);
        poolProps.setTestOnBorrow(true);
        poolProps.setValidationQuery("select 1");
        poolProps.setValidationInterval(30000);
        poolProps.setTimeBetweenEvictionRunsMillis(30000);
        poolProps.setLogAbandoned(true);
        poolProps.setRemoveAbandoned(true);
        poolProps.setRemoveAbandonedTimeout(120);
        poolProps.setMaxWait(10000);
        poolProps.setMaxActive(properties.getSlaverMaxActive());
        poolProps.setMaxIdle(properties.getSlaverMaxIdle());
        poolProps.setMinIdle(properties.getSlaverMinIdle());
        poolProps.setInitialSize(properties.getSlaverInitialSize());

        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setPoolProperties(poolProps);

        return dataSource;
    }

    @Bean
    public DynamicDataSource dataSource() {
        DynamicDataSource bean = new DynamicDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        /*if (env.acceptsProfiles(ProfileConst.PROFILES)) {
            targetDataSources.put(DataSourceHandler.MASTER_DS, createJndiDataSource(properties.getMasterJndiName()));
            targetDataSources.put(DataSourceHandler.SLAVE_DS, createJndiDataSource(properties.getSlaverJndiName()));
        } else {
            targetDataSources.put(DataSourceHandler.MASTER_DS, createMasterDataSource());
            targetDataSources.put(DataSourceHandler.SLAVE_DS, createSlaverDataSource());
        }*/

        targetDataSources.put(DataSourceHandler.MASTER_DS, createMasterDataSource());
        targetDataSources.put(DataSourceHandler.SLAVE_DS, createSlaverDataSource());
        bean.setTargetDataSources(targetDataSources);
        bean.setDefaultTargetDataSource(targetDataSources.get(DataSourceHandler.MASTER_DS));
        return bean;
    }
}
