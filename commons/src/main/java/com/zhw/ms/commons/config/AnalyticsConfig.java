package com.zhw.ms.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConditionalOnProperty(name = "analytics.db.url", matchIfMissing = false)
public class AnalyticsConfig {

    @Value("${analytics.db.userName}")
    private String dbUserName;
    @Value("${analytics.db.password}")
    private String dbPassword;
    @Value("${analytics.db.url}")
    private String dbUrl;
    @Value("${analytics.db.dbClassName}")
    private String dbClassName;

    @Bean(name = "analyticsJdbcTemplate")
    public JdbcTemplate analyticsJdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);
        return new JdbcTemplate(dataSource);
    }

    /*@Bean
    @DependsOn("analyticsJdbcTemplate")
    public AnalyticsDao analyticsDao() {
        return new AnalyticsDao();
    }

    @Bean
    @DependsOn("analyticsDao")
    public Analytics analytics() {
        return new MysqlAnalytics();
    }

    @Bean
    @DependsOn("analytics")
    public AccessLogFilter kaishiReportingFilter() {
        AccessLogFilter kaishiAnalyticsFilter = new AccessLogFilter();
        kaishiAnalyticsFilter.skipRestUris.add("/configprops");
        kaishiAnalyticsFilter.skipRestUris.add("/info");
        kaishiAnalyticsFilter.skipRestUris.add("/health");
        kaishiAnalyticsFilter.skipRestUris.add("/metrics");
        kaishiAnalyticsFilter.skipRestUris.add("/git-build-revision");
        return kaishiAnalyticsFilter;
    }*/
}
