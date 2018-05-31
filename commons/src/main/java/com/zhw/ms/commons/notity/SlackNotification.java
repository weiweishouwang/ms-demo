package com.zhw.ms.commons.notity;

import com.zhw.ms.commons.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Component
public class SlackNotification implements Notification {
    private static Logger logger = LoggerFactory.getLogger(SlackNotification.class);

    @Value("${spring.profiles:dev}")
    private String profile;

    private RestTemplate restTemplate = new RestTemplate();

    // dev error channel
    public static final String DevErrorChannel = "https://hooks.slack.com/services/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";

    @Async
    @Override
    public void notify(LogLevel level, String message) {
        if ("prd".equals(profile)) {
            // TODO
        } else {
            if (LogLevel.ERROR.equals(level)) {
                notify(DevErrorChannel, message);
            }
        }
    }

    private void notify(String channelURL, String msg) {
        try {
            restTemplate.postForObject(channelURL, new HttpEntity<Map<String, Object>>(Collections.singletonMap("text", msg)), String.class);
        } catch (RestClientException e) {
            logger.error("cannot send error to Slack. " + e.getMessage());
        }
    }

    public static void error(String message) {
        Notification notification = SpringUtil.getBean("slackNotification");
        notification.notify(LogLevel.ERROR, message);
    }

}
