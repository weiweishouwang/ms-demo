package com.zhw.ms.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件共通
 * Created by ZHW on 2015/5/11.
 */
public class PropUtil {

    private final static String FILE_MESSAGES = "whitelist.properties";
    //private final static String FILE_MAIL = "mail.properties";

    private static Logger logger = LoggerFactory.getLogger(PropUtil.class);

    private static Map<String, Properties> propMap = new HashMap<String, Properties>();

    static {
        try {
            Resource resource = new ClassPathResource(FILE_MESSAGES);
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            propMap.put(FILE_MESSAGES, props);

            /*resource = new ClassPathResource(FILE_MAIL);
            props = PropertiesLoaderUtils.loadProperties(resource);
            propMap.put(FILE_MAIL, props);*/
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 根据Key获取值
     *
     * @param key the key
     * @return property
     */
    public static String getProperty(String key) {

        for (Map.Entry<String, Properties> entry : propMap.entrySet()) {
            Properties props = entry.getValue();
            if (props.containsKey(key)) {
                return props.getProperty(key);
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * 根据Key获取值
     *
     * @param fileName the file name
     * @param key      the key
     * @return property
     */
    public static String getProperty(String fileName, String key) {
        try {
            Resource resource = new ClassPathResource(fileName);
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            if (props.containsKey(key)) {
                return props.getProperty(key);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return StringUtils.EMPTY;
    }

    /**
     * 填充Properties
     *
     * @param props    the props
     * @param fileName the file name
     */
    public static void fillProperties(Properties props, String fileName) {
        try {
            Resource resource = new ClassPathResource(fileName);
            PropertiesLoaderUtils.fillProperties(props, resource);
            propMap.put(fileName, props);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
