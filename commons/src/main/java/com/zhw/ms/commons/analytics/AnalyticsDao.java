package com.zhw.ms.commons.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
@DependsOn("analyticsJdbcTemplate")
public class AnalyticsDao {
    public static long DAY = 86400000l;
    public static int DAY_SECOND = 86400;
    public static int DAYS_30 = 2592000;
    public static int ONE_HOUR = 3600;

    @Autowired
    @Qualifier("analyticsJdbcTemplate")
    JdbcTemplate analyticsJdbcTemplate;

    private static final String SQL_INSERT_REQUEST_LOG = "insert into reporting.%s(id , application_name, content_type, duration, start_time, log_time, method, port_number, protocol, query_string, remote_addr, remote_host, remote_port, req_id, request_body, response_body, rest_uri, rest_url, status, user_agent, user_id, ver) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

    private static final String SQL_INSERT_USER_REQUEST = "insert ignore into reporting.user_request(start_time, user_id) values (?,?)";

    public void insert(RequestLog requestLog) {
        if (requestLog != null) {
            String tableName = getTableName(System.currentTimeMillis());
            insetRecord(requestLog, tableName);
        }
    }

    private void insetRecord(RequestLog requestLog, String tableName) {
        /*if (requestLog.userId != null) {
            Long normalizedTime = requestLog.startTime - requestLog.startTime % DAY;
            analyticsJdbcTemplate.update(SQL_INSERT_USER_REQUEST, new Object[]{normalizedTime, requestLog.userId});
        }*/
        analyticsJdbcTemplate.update(String.format(SQL_INSERT_REQUEST_LOG, tableName), new Object[]{requestLog.id, requestLog.applicationName, requestLog.contentType, requestLog.duration, requestLog.startTime, requestLog.logTime, requestLog.method, requestLog.portNumber, requestLog.protocol, requestLog.queryString, requestLog.remoteAddr, requestLog.remoteHost, requestLog.remotePort, requestLog.reqId, requestLog.requestBody, requestLog.responseBody, requestLog.restUri, requestLog.restUrl, requestLog.status, requestLog.userAgent, requestLog.userId, requestLog.ver});
    }

    public static String getTableName(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return "request_log_" + sdf.format(new Date(time));
    }

    public void createReqeustLogTables(long startTime, long endTime) {
        for (long i = startTime; i <= endTime; i += DAY) {
            analyticsJdbcTemplate.execute(SQL_CREATE_REQUEST_LOG(getTableName(i)));
        }
    }

    public static final String SQL_CREATE_REQUEST_LOG(String tableName) {
        return "create table if not exists reporting." + tableName + " (id varchar(255) NOT NULL,start_time bigint(20) DEFAULT NULL, log_time bigint(20) DEFAULT NULL, duration bigint(20) DEFAULT NULL, application_name varchar(255) DEFAULT NULL, rest_uri varchar(255) DEFAULT NULL, method varchar(255) DEFAULT NULL, status int(11) NOT NULL, query_string varchar(255) DEFAULT NULL, content_type varchar(255) DEFAULT NULL, request_body varchar(2048) DEFAULT NULL, response_body varchar(2048) DEFAULT NULL, req_id varchar(255) DEFAULT NULL, user_agent varchar(255) DEFAULT NULL, user_id varchar(255) DEFAULT NULL, ver varchar(255) DEFAULT NULL, protocol varchar(255) DEFAULT NULL,rest_url varchar(255) DEFAULT NULL, port_number int(11) DEFAULT NULL, remote_addr varchar(255) DEFAULT NULL, remote_host varchar(255) DEFAULT NULL, remote_port int(11) DEFAULT NULL,PRIMARY KEY (id), KEY application_name (application_name), KEY start_time (start_time), KEY log_time (log_time), KEY user_id (user_id), KEY req_id (req_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    @Scheduled(cron = "0 0 2 * * *") //Every 3rd day at 2am, in UTC after migrating to Docker
    public void createTable() throws Exception {
        createReqeustLogTables(System.currentTimeMillis(), System.currentTimeMillis() + DAY);
    }
}
