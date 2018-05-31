package com.zhw.ms.commons.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@DependsOn("analyticsDao")
public class MysqlAnalytics implements Analytics {
    private static final Logger logger = LoggerFactory.getLogger(MysqlAnalytics.class);

    @Autowired
    AnalyticsDao analyticsDao;

    ExecutorService analyticsExecutorService = Executors.newFixedThreadPool(3);

    @Override
    public void logData(final RequestLog requestLog) {
        analyticsExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    requestLog.id = UUID.randomUUID().toString();
                    if (requestLog.requestBody != null && requestLog.requestBody.length() > MAX_LENGTH)
                        requestLog.requestBody = requestLog.requestBody.substring(0, MAX_LENGTH);
                    if (requestLog.responseBody != null && requestLog.responseBody.length() > MAX_LENGTH)
                        requestLog.responseBody = requestLog.responseBody.substring(0, MAX_LENGTH);
                    analyticsDao.insert(requestLog);
                } catch (Exception e) {
                    logger.error("Exception: " + e.getMessage());
                }
            }
        });
    }

    public static final int MAX_LENGTH = 2047;
}
