package com.zhw.ms.commons.filter;

import com.zhw.ms.commons.analytics.Analytics;
import com.zhw.ms.commons.analytics.RequestLog;
import com.zhw.ms.commons.web.BufferedRequestWrapper;
import com.zhw.ms.commons.web.BufferedResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DependsOn("analytics")
@Order(1)
@WebFilter(filterName = "accessLogFilter", urlPatterns = "/*")
public class AccessLogFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AccessLogFilter.class);

    private ApplicationContext appCtx;
    @Autowired
    Analytics analytics;

    @Value("${spring.application.name}")
    String applicationName = "Unknown";
    @Value("${spring.profiles:dev}")
    String profile;

    public List<String> skipRestUris = new ArrayList<>();

    @Override
    public void init(FilterConfig fc) throws ServletException {
        appCtx = WebApplicationContextUtils.getWebApplicationContext(fc.getServletContext());
        skipRestUris.add("/configprops");
        skipRestUris.add("/info");
        skipRestUris.add("/health");
        skipRestUris.add("/metrics");
        skipRestUris.add("/git-build-revision");
        skipRestUris.add("/favicon.ico");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper((HttpServletRequest) request);
        BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper((HttpServletResponse) response);
        long startTime = System.currentTimeMillis();
        try {
            chain.doFilter(bufferedRequest, bufferedResponse);
        } finally {
            if (!HttpMethod.OPTIONS.toString().equals(bufferedRequest.getMethod()) && !skipRestUris.contains(bufferedRequest.getRequestURI()) && !"integration".equals(bufferedRequest.getHeader("req_src")) && shouldLogProfile()) {
                analytics.logData(getAnalyticRequestLog(startTime, bufferedRequest, bufferedResponse));
            }
        }
    }

    private boolean shouldLogProfile() {
        return "aliyun".equals(profile) || "staging".equals(profile) || "dev".equals(profile);
    }

    protected String getRequestUserId(BufferedRequestWrapper bufferedRequestWrapper) {
        String userId = "";
        return userId;
    }

    private RequestLog getAnalyticRequestLog(long startTime, BufferedRequestWrapper bufferedRequest, BufferedResponseWrapper bufferedResponse) {
        long logTime = System.currentTimeMillis();
        RequestLog analyticRequestLog = new RequestLog();
        try {
            analyticRequestLog.startTime = startTime;
            analyticRequestLog.duration = logTime - startTime;
            analyticRequestLog.userId = getRequestUserId(bufferedRequest);
            analyticRequestLog.applicationName = applicationName;
            analyticRequestLog.restUri = bufferedRequest.getRequestURI();
            analyticRequestLog.restUrl = String.valueOf(bufferedRequest.getRequestURL());
            if (analyticRequestLog.restUri != null && !analyticRequestLog.restUri.contains("login") && !analyticRequestLog.restUri.contains("register"))
                analyticRequestLog.requestBody = bufferedRequest.getRequestBody() != null ? bufferedRequest.getRequestBody() : bufferedRequest.getParameter("json");

            analyticRequestLog.responseBody = bufferedResponse.getContent();
            analyticRequestLog.logTime = logTime;
            String clientIp = bufferedRequest.getHeader("X-Forwarded-For");
            analyticRequestLog.remoteAddr = clientIp == null ? bufferedRequest.getRemoteAddr() : clientIp;
            analyticRequestLog.remoteHost = bufferedRequest.getRemoteHost();
            analyticRequestLog.remotePort = bufferedRequest.getRemotePort();
            analyticRequestLog.protocol = bufferedRequest.getProtocol();
            analyticRequestLog.portNumber = bufferedRequest.getLocalPort();
            analyticRequestLog.status = bufferedResponse.getStatus();
            analyticRequestLog.method = bufferedRequest.getMethod();
            analyticRequestLog.userAgent = bufferedRequest.getHeader("user-agent");
            analyticRequestLog.reqId = bufferedRequest.getHeader("X-B3-TraceId");
            analyticRequestLog.ver = bufferedRequest.getHeader("ver");
            analyticRequestLog.queryString = bufferedRequest.getQueryString();
            analyticRequestLog.contentType = bufferedRequest.getContentType();
        } catch (Exception e) {
            logger.error("Exception:" + e.getMessage());
            e.printStackTrace();
        }
        return analyticRequestLog;
    }

    @Override
    public void destroy() {
    }
}
