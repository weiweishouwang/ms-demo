package com.zhw.ms.commons.notity;

import com.zhw.ms.commons.spring.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class WechatNotification {
    private static Logger logger = LoggerFactory.getLogger(WechatNotification.class);

    @Value("${spring.profiles:dev}")
    private String profile;

    @Autowired
    Environment env;

    @Value("${spring.application.name}")
    private String appName;

    private static final String URL_GETTOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    private static final String URL_SEND = "https://qyapi.weixin.qq.com/cgi-bin/message/send";

    private static final String CORPID = "wweb37a195e29b38d0";


    private static final String URL_GETTOKEN_CDSS_TEST = URL_GETTOKEN + "?corpid=" + CORPID
            + "&corpsecret=BU3pxGRozqXdTckke1YTIaXXVoEEIr9T2OrgB8zysks";

    private static final String URL_GETTOKEN_CDSS_PRD = URL_GETTOKEN + "?corpid=" + CORPID
            + "&corpsecret=sJE_xYejMcu3fHI3oqpp8tybXIHdf7_AJqljLyu8QxM";

    private static final String TO_TAG_CDSS = "1";

    private static final int AGENT_ID_CDSS_TEST = 1000002;

    private static final int AGENT_ID_CDSS_PRD = 1000004;


    private static final String URL_GETTOKEN_IDP_TEST = URL_GETTOKEN + "?corpid=" + CORPID
            + "&corpsecret=sYQLBjYr88uHdf8CIbugojSR6g4-HtKh9TsN6YO3VdE";

    private static final String URL_GETTOKEN_IDP_PRD = URL_GETTOKEN + "?corpid=" + CORPID
            + "&corpsecret=DQnCZx6PXXb0Gn6bxxg49SQaKIzbYZaS8IEU0oA_rbA";

    private static final String TO_TAG_IDP = "2";

    private static final int AGENT_ID_IDP_TEST = 1000003;

    private static final int AGENT_ID_IDP_PRD = 1000005;


    private static String accessToken;

    private static String urlSendTo;

    private static String toTag;

    private static int agentId;


    @Scheduled(fixedRate = 7200000)
    protected void init() {
        // cdss test
        if (appName.toUpperCase().contains("cdss-platform") && env.acceptsProfiles("stg1")) {
            accessToken = getAccessToken(URL_GETTOKEN_CDSS_TEST);
            urlSendTo = URL_SEND + "?access_token=" + accessToken;
            toTag = TO_TAG_CDSS;
            agentId = AGENT_ID_CDSS_TEST;
        } else if (appName.toUpperCase().contains("cdss-platform") && env.acceptsProfiles("pdt")) {
            accessToken = getAccessToken(URL_GETTOKEN_CDSS_PRD);
            urlSendTo = URL_SEND + "?access_token=" + accessToken;
            toTag = TO_TAG_CDSS;
            agentId = AGENT_ID_CDSS_PRD;
        } else if (appName.toUpperCase().contains("individual-disease-prediction") && env.acceptsProfiles("stg1")) {
            accessToken = getAccessToken(URL_GETTOKEN_IDP_TEST);
            urlSendTo = URL_SEND + "?access_token=" + accessToken;
            toTag = TO_TAG_IDP;
            agentId = AGENT_ID_IDP_TEST;
        } else if (appName.toUpperCase().contains("individual-disease-prediction") && env.acceptsProfiles("pdt")) {
            accessToken = getAccessToken(URL_GETTOKEN_IDP_PRD);
            urlSendTo = URL_SEND + "?access_token=" + accessToken;
            toTag = TO_TAG_IDP;
            agentId = AGENT_ID_IDP_PRD;
        }
    }

    private String getAccessToken(String url) {
        AccessToken accessToken = restTemplate.getForObject(url, AccessToken.class);

        if (accessToken != null && accessToken.getErrcode() == 0) {
            return accessToken.getAccess_token();
        }

        return null;
    }


    private RestTemplate restTemplate = new RestTemplate();

    //@Async
    public void notify(LogLevel level, String message) {
        if (LogLevel.ERROR.equals(level)) {
            WechatMsg msg = new WechatMsg();
            msg.setTotag(toTag);
            msg.setAgentid(agentId);
            msg.setText(Collections.singletonMap("content", message));
            notify(urlSendTo, msg);
        }
    }

    private void notify(String channelURL, WechatMsg msg) {
        try {
            restTemplate.postForObject(channelURL, new HttpEntity<WechatMsg>(msg), Object.class);
        } catch (RestClientException e) {
            logger.error("cannot send error to Wechat. " + e.getMessage());
        }
    }

    public static void error(String message) {
        WechatNotification notification = SpringUtil.getBean("wechatNotification");
        notification.notify(LogLevel.ERROR, message);
    }

}
