package com.zhw.ms.commons.filter;

import com.zhw.ms.commons.utils.JccUtil;
import com.zhw.ms.commons.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * 网关过滤器，加解密参数，签名验证
 * Created by ZHW on 2015/9/8.
 */
public class LogFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        initMDC();
        chain.doFilter(request, response);
        removeMDC();
    }

    @Override
    public void destroy() {
    }

    /**
     * 初始化log4j参数
     */
    private void initMDC() {
        MDC.put("ip", WebUtil.getIP());
        MDC.put("requestID", JccUtil.getUUID());
    }

    /**
     * 删除log4j参数
     */
    private void removeMDC() {
        MDC.remove("ip");
        MDC.remove("requestID");
    }

}
