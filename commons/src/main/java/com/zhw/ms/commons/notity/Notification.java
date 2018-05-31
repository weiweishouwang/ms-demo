package com.zhw.ms.commons.notity;

import org.springframework.boot.logging.LogLevel;

public interface Notification {
    void notify(LogLevel level, String msg);
}
