package com.zhw.ms.commons.utils;

import com.zhw.ms.common.contract.consts.CommonConst;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 日期格式化共通
 * Created by ZHW on 2015/5/11.
 */
public class DateUtil {
    /**
     * The constant YYYYMM.
     */
    public final static String YYYYMM = "yyyyMM";
    /**
     * The constant YYYYMMDD.
     */
    public final static String YYYYMMDD = "yyyyMMdd";
    /**
     * The constant YYYYMMDD2.
     */
    public final static String YYYYMMDD2 = "yyyy/MM/dd";
    /**
     * The constant YYYYMMDD3.
     */
    public final static String YYYYMMDD3 = "yyyy-MM-dd";
    /**
     * The constant YYYYMMDD4.
     */
    public final static String YYYYMMDD4 = "yyyy年M月d日";
    /**
     * The constant MMDD.
     */
    public final static String MMDD = "MM/dd";
    /**
     * The constant HHMM.
     */
    public final static String HHMM = "HH:mm";
    /**
     * The constant HHMMSS.
     */
    public final static String HHMMSS = "HH:mm:ss";
    /**
     * The constant HHMMSSSSS.
     */
    public final static String HHMMSSSSS = "HH:mm:ss.SSS";
    /**
     * The constant HHMMSS2.
     */
    public final static String HHMMSS2 = "hh:mm:ss a";
    /**
     * The constant HHMMSSSSS2.
     */
    public final static String HHMMSSSSS2 = "hh:mm:ss.SSS a";
    /**
     * The constant YYYYMMDDHH.
     */
    public final static String YYYYMMDDHH = "yyyyMMdd HH";
    /**
     * The constant YYYYMMDDHHMM.
     */
    public final static String YYYYMMDDHHMM = "yyyyMMdd HH:mm";
    /**
     * The constant YYYYMMDDHHMM2.
     */
    public final static String YYYYMMDDHHMM2 = "yyyy/MM/dd HH:mm";
    /**
     * The constant YYYYMMDDHHMM3.
     */
    public final static String YYYYMMDDHHMM3 = "yyyyMMddHHmm";
    /**
     * The constant YYYYMMDDHHMM4.
     */
    public final static String YYYYMMDDHHMM4 = "yyyy年M月d日 HH:mm";
    /**
     * The constant YYYYMMDDHHMMSS.
     */
    public final static String YYYYMMDDHHMMSS = "yyyyMMdd HH:mm:ss";
    /**
     * The constant YYYYMMDDHHMMSS2.
     */
    public final static String YYYYMMDDHHMMSS2 = "yyyy/MM/dd HH:mm:ss";
    /**
     * The constant YYYYMMDDHHMMSS3.
     */
    public final static String YYYYMMDDHHMMSS3 = "yyyy-MM-dd HH:mm:ss";
    /**
     * The constant YYYYMMDDHHMMSS4.
     */
    public final static String YYYYMMDDHHMMSS4 = "yyyyMMddHHmmss";
    /**
     * The constant YYYYMMDDHHMMSSMS.
     */
    public final static String YYYYMMDDHHMMSSMS = "yyyyMMdd HH:mm:ss.SSS";
    /**
     * The constant YYYYMMDDHHMMSSMS2.
     */
    public final static String YYYYMMDDHHMMSSMS2 = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * The constant ONE_DATE_MI.
     */
    public static int ONE_DATE_MI = 1000 * 60 * 60 * 24;
    /**
     * The constant ONE_YEAR.
     */
    public static int ONE_YEAR = 365;

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 日期格式化
     *
     * @param date    日期
     * @param pattern 格式化类型
     * @return 格式化字符串 string
     */
    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) {
            return CommonConst.EMPTY;
        }

        SimpleDateFormat simpleDateFordmat = new SimpleDateFormat(pattern);
        return simpleDateFordmat.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date    日期
     * @param pattern 格式化类型
     * @return 格式化字符串 string
     */
    public static String format(Object date, String pattern) {
        if (date == null || pattern == null) {
            return CommonConst.EMPTY;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(CommonUtil.toDate(date));
    }

    /**
     * 字符转日期
     *
     * @param source  日期字符串
     * @param pattern 格式化类型
     * @return 日期 date
     */
    public static Date parseDate(String source, String pattern) {
        if (source == null || pattern == null) {
            return null;
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 日期转日历
     *
     * @param date 日期
     * @return 日历 calendar
     */
    public static Calendar convertToCalendar(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取年龄
     *
     * @param birthday 出生日期
     * @return 年龄 age
     */
    public static int getAge(Date birthday) {
        if (birthday == null) {
            return 0;
        }

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(birthday);

        Calendar calendar2 = Calendar.getInstance();

        int value = calendar2.compareTo(calendar1);

        if (value % ONE_DATE_MI == 0) {
            value = value / ONE_DATE_MI;
        } else {
            value = value / ONE_DATE_MI + 1;
        }

        if (value % ONE_YEAR == 0) {
            value = value / ONE_YEAR;
        } else {
            value = value / ONE_YEAR + 1;
        }

        return value;
    }

    /**
     * 获取现在的日时
     *
     * @param pattern 格式化类型
     * @return 日期 now
     */
    public static Date getNow(String pattern) {
        return parseDate(format(new Date(), pattern), pattern);
    }

    /**
     * 获取现在的日时
     *
     * @return 日期 now
     */
    public static Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 增加分钟
     *
     * @param time   日期
     * @param amount 分钟数
     * @return 新的日期 date
     */
    public static Date addMinutes(Date time, int amount) {
        return DateUtils.addMinutes(time, amount);
    }

    /**
     * 增加小时
     *
     * @param time   日期
     * @param amount 小时数
     * @return 新的日期 date
     */
    public static Date addHour(Date time, int amount) {
        return DateUtils.addHours(time, amount);
    }

    /**
     * 增加秒
     *
     * @param time   日期
     * @param amount 秒数
     * @return 新的日期 date
     */
    public static Date addSeconds(Date time, int amount) {
        return DateUtils.addSeconds(time, amount);
    }

    /**
     * 增加天
     *
     * @param time   日期
     * @param amount 天数
     * @return 新的日期 date
     */
    public static Date addDay(Date time, int amount) {
        return DateUtils.addDays(time, amount);
    }

    /**
     * 获取相差几分钟
     *
     * @param first  日期
     * @param secend 日期
     * @return 分钟数 long
     */
    public static long minusMinute(Date first, Date secend) {
        return (long) Math.ceil((first.getTime() - secend.getTime()) / (1000.0 * 60));
    }

    /**
     * 获取相差几秒
     *
     * @param first  日期
     * @param secend 日期
     * @return 秒数 int
     */
    public static int minusSecond(Date first, Date secend) {
        return (int) Math.ceil((first.getTime() - secend.getTime()) / 1000.0);
    }

    /**
     * 获取星期
     *
     * @param date 日期
     * @return 星期 week
     */
    public static int getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前第几个周
     *
     * @param date 日期
     * @return 周数 week of year
     */
    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取现在的日期字符串
     *
     * @param pattern 格式化类型
     * @return 日期字符串 now str
     */
    public static String getNowStr(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 日期相减
     *
     * @param firstDate  被减日期
     * @param secondDate 减日期
     * @param timeUnit   结果的单位
     * @return firstDate - secondDate后的结果
     */
    public static Long subDateTime(Date firstDate, Date secondDate, TimeUnit timeUnit) {
        if (firstDate == null || secondDate == null) {
            return null;
        }
        return timeUnit.convert(firstDate.getTime() - secondDate.getTime(), TimeUnit.MILLISECONDS);

    }

    /**
     * Date 转换为 LocalDate
     *
     * @param date the date
     * @return the local date
     */
    public static LocalDate dateToLocalDate(Date date) {
        LocalDateTime dateTime = dateToLocalDateTime(date);
        if (dateTime != null) {
            return dateTime.toLocalDate();
        } else {
            return null;
        }
    }

    /**
     * Date 转换为 LocalDateTime
     *
     * @param time the time
     * @return the local date time
     */
    public static LocalDateTime dateToLocalDateTime(Date time) {
        if (time != null) {
            Instant instant = time.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            return localDateTime;
        }
        return null;

    }

    /**
     * LocalDate 转换为 Date
     *
     * @param localDate the local date
     * @return the date
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDateTimeToDate(localDate.atStartOfDay());
    }

    /**
     * LocalDateTime 转换为 Date
     *
     * @param localDateTime the local date time
     * @return the date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }
}
