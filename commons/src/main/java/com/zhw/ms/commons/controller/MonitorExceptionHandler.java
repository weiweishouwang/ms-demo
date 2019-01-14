package com.zhw.ms.commons.controller;

import com.zhw.ms.common.contract.bean.Result;
import com.zhw.ms.common.contract.bean.ResultEnum;
import com.zhw.ms.commons.notity.WechatNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

//@ControllerAdvice
public class MonitorExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(MonitorExceptionHandler.class);
    private static String ips = "";

    static {
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ifc = interfaces.nextElement();
                if (ifc.isUp()) {
                    Enumeration<InetAddress> addresses = ifc.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        String ip = addr.getHostAddress();
                        if ("127.0.0.1".equals(ip))
                            continue;
                        sb.append(ip).append(" ");
                    }
                }
            }
            ips = sb.toString();
        } catch (SocketException e) {
            logger.error(e.getMessage());
        }
    }

    @Value("${spring.application.name}")
    public String serviceName;

    //@ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleBusinessException(Exception e, WebRequest request) {


        request.getHeader("channel-id");
        String institutionId = request.getHeader("institution-id");


        Iterator<String> names = request.getHeaderNames();
        StringBuilder sbb = new StringBuilder();
        while (names.hasNext()) {
            String name = names.next();
            String value = request.getHeader(name);
            sbb.append("header " + name + ": " + value + "\n");
        }
        String headers = sbb.toString() + "IP: " + ips;

        String queryString = toQueryString(request.getParameterMap());

        String msg = serviceName + " " + request.getDescription(true);
        System.err.println(msg);
        System.err.println("queryString: " + queryString);
        System.err.println(headers);
        logger.error(e.getMessage(), e);

        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        String[] traces = stackTrace.toString().split("[\n\r]+");
        StringBuilder sb = new StringBuilder();
        boolean found = false;

        for (int i = 0; i < traces.length; i++) {
            sb.append(traces[i]).append("\n");
            if (!found) {
                if (traces[i].indexOf("zhw") != -1) {
                    found = true;
                }
            } else {
                if (traces[i].indexOf("zhw") == -1) {
                    break;
                }
            }
        }

        msg = msg + sb.toString() + "\n" + headers;

        WechatNotification.error(msg);

        Result<Object> resp = new Result<>();
        resp.setRetCode(ResultEnum.SYSTEM_ERROR.code);
        resp.setRetMsg(ResultEnum.SYSTEM_ERROR.message);

        return handleExceptionInternal(e, resp, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private String toQueryString(Map<String, String[]> paramMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            for (String value : entry.getValue()) {
                if (sb.length() > 0) {
                    sb.append('&');
                }
                sb.append(entry.getKey()).append('=').append(value);
            }
        }
        return sb.toString();
    }
}
