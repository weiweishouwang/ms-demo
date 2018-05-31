package com.zhw.ms.commons.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/12/29 0029.
 */
@Component
@ConfigurationProperties(prefix = "datasource")
public class DataSourceProperties {
    private static final String JNDI_PREFIX = "java:comp/env/";

    private String masterJndiName;
    private String slaverJndiName;

    private String masterDriverClassName;
    private String masterUrl;
    private String masterUsername;
    private String masterPassword;
    private int masterMaxActive;
    private int masterMaxIdle;
    private int masterMinIdle;
    private int masterInitialSize;

    private String slaverDriverClassName;
    private String slaverUrl;
    private String slaverUsername;
    private String slaverPassword;
    private int slaverMaxActive;
    private int slaverMaxIdle;
    private int slaverMinIdle;
    private int slaverInitialSize;

    public String getMasterJndiName() {
        return JNDI_PREFIX + masterJndiName;
    }

    public void setMasterJndiName(String masterJndiName) {
        this.masterJndiName = masterJndiName;
    }

    public String getSlaverJndiName() {
        return JNDI_PREFIX + slaverJndiName;
    }

    public void setSlaverJndiName(String slaverJndiName) {
        this.slaverJndiName = slaverJndiName;
    }

    public String getMasterDriverClassName() {
        return masterDriverClassName;
    }

    public void setMasterDriverClassName(String masterDriverClassName) {
        this.masterDriverClassName = masterDriverClassName;
    }

    public String getMasterUrl() {
        return masterUrl;
    }

    public void setMasterUrl(String masterUrl) {
        this.masterUrl = masterUrl;
    }

    public String getMasterUsername() {
        return masterUsername;
    }

    public void setMasterUsername(String masterUsername) {
        this.masterUsername = masterUsername;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public String getSlaverDriverClassName() {
        return slaverDriverClassName;
    }

    public void setSlaverDriverClassName(String slaverDriverClassName) {
        this.slaverDriverClassName = slaverDriverClassName;
    }

    public String getSlaverUrl() {
        return slaverUrl;
    }

    public void setSlaverUrl(String slaverUrl) {
        this.slaverUrl = slaverUrl;
    }

    public String getSlaverUsername() {
        return slaverUsername;
    }

    public void setSlaverUsername(String slaverUsername) {
        this.slaverUsername = slaverUsername;
    }

    public String getSlaverPassword() {
        return slaverPassword;
    }

    public void setSlaverPassword(String slaverPassword) {
        this.slaverPassword = slaverPassword;
    }

    public int getSlaverInitialSize() {
        return slaverInitialSize;
    }

    public void setSlaverInitialSize(int slaverInitialSize) {
        this.slaverInitialSize = slaverInitialSize;
    }

    public int getMasterMaxActive() {
        return masterMaxActive;
    }

    public void setMasterMaxActive(int masterMaxActive) {
        this.masterMaxActive = masterMaxActive;
    }

    public int getMasterMaxIdle() {
        return masterMaxIdle;
    }

    public void setMasterMaxIdle(int masterMaxIdle) {
        this.masterMaxIdle = masterMaxIdle;
    }

    public int getMasterMinIdle() {
        return masterMinIdle;
    }

    public void setMasterMinIdle(int masterMinIdle) {
        this.masterMinIdle = masterMinIdle;
    }

    public int getMasterInitialSize() {
        return masterInitialSize;
    }

    public void setMasterInitialSize(int masterInitialSize) {
        this.masterInitialSize = masterInitialSize;
    }

    public int getSlaverMaxActive() {
        return slaverMaxActive;
    }

    public void setSlaverMaxActive(int slaverMaxActive) {
        this.slaverMaxActive = slaverMaxActive;
    }

    public int getSlaverMaxIdle() {
        return slaverMaxIdle;
    }

    public void setSlaverMaxIdle(int slaverMaxIdle) {
        this.slaverMaxIdle = slaverMaxIdle;
    }

    public int getSlaverMinIdle() {
        return slaverMinIdle;
    }

    public void setSlaverMinIdle(int slaverMinIdle) {
        this.slaverMinIdle = slaverMinIdle;
    }
}
