package com.zhw.ms.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * web处理共通
 * Created by ZHW on 2015/5/11.
 */
public class WebUtil {

    private static Logger logger = LoggerFactory.getLogger(WebUtil.class);

    /**
     * 获取Request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {

        if (RequestContextHolder.getRequestAttributes() != null) {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
        }

        return null;
    }

    /**
     * 获取Response
     *
     * @return response
     */
    public static HttpServletResponse getResponse() {

        if (RequestContextHolder.getRequestAttributes() != null) {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getResponse();
        }

        return null;
    }

    /**
     * 获取Session
     *
     * @return http session
     */
    public static HttpSession getHttpSession() {

        HttpServletRequest request = getRequest();

        if (request != null) {
            return request.getSession();
        }

        return null;
    }

    /**
     * 获取ServletContext
     *
     * @return servlet context
     */
    public static ServletContext getServletContext() {

        return getHttpSession().getServletContext();
    }

    /**
     * 获取客户端IP
     *
     * @return client ip
     */
    public static String getClientIp() {
        return getRequest().getRemoteAddr();
    }

    /**
     * 获取URL
     *
     * @param url the url
     * @return url
     */
    public static String getURL(String url) {
        HttpServletRequest request = getRequest();

        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());
        sb.append(":");
        sb.append(request.getServerPort());
        sb.append(request.getContextPath());
        sb.append("/");
        sb.append(url);

        return sb.toString();
    }

    /**
     * 获得本机IP
     *
     * @return ip
     */
    public static String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(), e);
        }

        return StringUtils.EMPTY;
    }

    /**
     * url编码
     *
     * @param url the url
     * @return string
     */
    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        return StringUtils.EMPTY;
    }

    /**
     * url解码
     *
     * @param url the url
     * @return string
     */
    public static String decode(String url) {
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        return StringUtils.EMPTY;
    }

    /**
     * 防xss跨站
     */
    public static String cleanXSS(String value) {
        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

    /**
     * 获取请求参数
     *
     * @return
     */
    public static String getParameterStr(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();

        StringBuilder sb = new StringBuilder();
        sb.append("{");

        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(entry.getKey()).append("=").append("[");
            sb2.append(String.join(",", entry.getValue())).append("]");
            list.add(sb2.toString());
        }

        sb.append(String.join(",", (String[]) list.toArray(new String[list.size()])));
        sb.append("}");

        return sb.toString();
    }

    /**
     * 获取URL
     *
     * @param request
     * @return
     */
    public static String getURL(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme());
        sb.append("://");
        sb.append(request.getServerName());
        sb.append(":");
        sb.append(request.getServerPort());
        sb.append(request.getRequestURI());

        return sb.toString();
    }

    /**
     * 判断是否Ajax调用
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {

        return "XMLHttpRequest".equals(request.getHeader("x-requested-with"));
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
    }
}
