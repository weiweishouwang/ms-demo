package com.zhw.ms.commons.utils;

import com.zhw.ms.commons.consts.JccConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用共通
 * Created by ZHW on 2015/5/11.
 */
public class JccUtil {

    private static Logger logger = LoggerFactory.getLogger(JccUtil.class);

    /**
     * 转换为小写
     *
     * @param obj the obj
     * @return string string
     */
    public static String toLower(String obj) {
        if (obj == null) {
            return JccConst.EMPTY;
        } else {
            return obj.toLowerCase().trim();
        }
    }

    /**
     * 转换为大写
     *
     * @param obj the obj
     * @return string string
     */
    public static String toUpper(String obj) {
        if (obj == null) {
            return JccConst.EMPTY;
        } else {
            return obj.toUpperCase().trim();
        }
    }

    /**
     * 转换为String
     *
     * @param obj the obj
     * @return string string
     */
    public static String toStr(Object obj) {
        if (obj == null) {
            return StringUtils.EMPTY;
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * 转换为Long
     *
     * @param obj the obj
     * @return long long
     */
    public static Long toLong(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).longValue();
        } else {
            try {
                BigDecimal value = new BigDecimal(obj.toString().trim());
                return value.longValue();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    /**
     * 转换为Int
     *
     * @param obj the obj
     * @return integer integer
     */
    public static Integer toInt(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).intValue();
        } else {
            try {
                BigDecimal value = new BigDecimal(obj.toString().trim());
                return value.intValue();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    /**
     * 转换为Double
     *
     * @param obj the obj
     * @return double double
     */
    public static Double toDouble(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).doubleValue();
        } else {
            try {
                BigDecimal value = new BigDecimal(obj.toString().trim());
                return value.doubleValue();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    /**
     * 转换为BigDecimal
     *
     * @param obj the obj
     * @return big decimal
     */
    public static BigDecimal toBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        } else {
            try {
                return new BigDecimal(obj.toString().trim());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    /**
     * 转换为Date
     *
     * @param obj the obj
     * @return date date
     */
    public static Date toDate(Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return ((Date) obj);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * 转换为UTF8
     *
     * @param str the str
     * @return string string
     */
    public static String toUTF8(String str) {

        if (StringUtils.length(str) < 1) {
            return str;
        }

        try {
            String utf8Str = new String(str.getBytes(), JccConst.CHARSET_UTF8);
            return utf8Str;
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * 判断是否相等
     *
     * @param a the a
     * @param b the b
     * @return boolean boolean
     */
    public static boolean equals(Object a, Object b) {

        if (a == b) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else if (a.equals(b)) {
                return true;
            } else {
                return a.toString().equals(b.toString());
            }
        }
    }

    /**
     * 获取地域信息
     *
     * @return locale locale
     */
    public static String getLocale() {
        return Locale.getDefault().toString();
    }

    /**
     * 判断是否空集合
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return boolean boolean
     */
    public static <T> boolean isEmptyList(List<T> list) {
        if (list == null) {
            return true;
        } else if (list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否空Map
     *
     * @param <T> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return boolean boolean
     */
    public static <T, V> boolean isEmptyMap(Map<T, V> map) {
        if (map == null) {
            return true;
        } else if (map.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否空数组
     *
     * @param <T>   the type parameter
     * @param array the array
     * @return boolean boolean
     */
    public static <T> boolean isEmptyArray(T[] array) {
        if (array == null) {
            return true;
        } else if (array.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 转换为Byte
     *
     * @param obj the obj
     * @return byte byte
     */
    public static Byte toByte(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).byteValue();
        } else {
            DecimalFormat format = new DecimalFormat("0000000000000000000");
            try {
                return format.parse(obj.toString().trim()).byteValue();
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    /**
     * 获取第几页
     *
     * @param length the length
     * @param unit   the unit
     * @return unit count
     */
    public static int getUnitCount(int length, int unit) {
        if (length % unit == 0) {
            return length / unit;
        } else {
            return length / unit + 1;
        }
    }

    /**
     * 获取Like查询字符串
     *
     * @param str the str
     * @return like str
     */
    public static String getLikeStr(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(JccUtil.toStr(str));
        sb.append("%");

        return sb.toString();
    }

    /**
     * 格式化金额
     *
     * @param amount the amount
     * @return amount amount
     */
    public static String getAmount(Object amount) {
        NumberFormat formatter = new DecimalFormat("###,###,###,##0.00");
        try {
            return formatter.format(amount);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "0.00";
        }
    }

    /**
     * 获取字符串的Byte数组
     *
     * @param obj     the obj
     * @param charset the charset
     * @return byte [ ]
     */
    public static byte[] toByteArray(Object obj, String charset) {
        if (obj == null) {
            return null;
        } else {
            try {
                return obj.toString().trim().getBytes(charset);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    /**
     * 暂停
     *
     * @param millis the millis
     */
    public static void sleep(long millis) {
        if (millis < 1) {
            millis = 1;
        } else {
            try {
                Thread.sleep(millis);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 将字符串分解为数组
     *
     * @param str  the str
     * @param sign the sign
     * @return string [ ]
     */
    public static String[] toArray(String str, String sign) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            String[] strArray = str.trim().split(sign);
            List<String> result = new ArrayList<String>();

            for (int i = 0; i < strArray.length; ++i) {
                if (!StringUtils.isBlank(strArray[i])) {
                    result.add(strArray[i]);
                }
            }

            return result.toArray(new String[result.size()]);
        }
    }

    /**
     * 复制对象到Map
     *
     * @param src    the src
     * @param target the target
     */
    public static void copyPropertys(Object src, Map<String, Object> target){
        if(null != src){
            if (target == null) {
                target = new HashMap<String, Object>();
            }

            BeanWrapper beanWrapper = new BeanWrapperImpl(src);
            PropertyDescriptor[] descriptor = beanWrapper.getPropertyDescriptors();
            for (int i = 0; i < descriptor.length; i++) {
                String key = descriptor[i].getName();
                if(!key.equals("class")){
                    target.put(key, beanWrapper.getPropertyValue(key));
                }
            }
        }
    }

    /**
     * Mask bank card number.
     *
     * @param s the bank card number
     * @return the bank card number after masking
     */
    public static String maskBankCardNumber(String s) {
        int l = s.length();
        int i = l - 4;//截取最后四个字符；
        return s.substring(i, l);
    }

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取随机数
     *
     * @param max the max
     * @return next next
     */
    public static int getNext(int max) {
        return new Random().nextInt(max);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        /*System.out.println(getNext(10));
        System.out.println(getNext(10));
        System.out.println(getNext(10));
        System.out.println(getNext(10));

        //System.out.println(getAmount(8));
        int[] results = new int[10];

        for (int i = 0; i < 10000; ++i) {
            ++results[getNext(10)];
        }

        for (int i = 0; i < results.length; ++i) {
            System.out.println(results[i]);
        }*/

        //System.out.println(toInt("2.00"));

        System.out.println(underlineToCamel("bank_card_phone_number"));
    }

    /**
     * 根据类获取bean对应的名称
     *
     * @param clazz 指定类别
     * @return bean对应的名称，类名第一个字母小写
     */
    public static String getBeanName(Class clazz) {
        String className = clazz.getSimpleName();
        return className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
    }

    public static int random(int bound) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(bound);
    }

    public static String underlineToCamel(String param) {
        if (StringUtils.isBlank(param)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }
}
