package com.zscp.master.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by wujin on 2016/12/21.
 */
public class DateUtils {

    /**
     * 定义常量
     **/
    public static final String DATE_JFP_STR = "yyyyMM";
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";

    public final static int YEAR = Calendar.YEAR; // 年
    public final static int MONTH = Calendar.MONTH;// 月
    public final static int DATE = Calendar.DATE;// 日
    public final static int HOUR = Calendar.HOUR_OF_DAY;// 小时
    public final static int MINUTE = Calendar.MINUTE;// 分
    public final static int SECOND = Calendar.SECOND;// 秒
    public final static int MILLISECOND = Calendar.MILLISECOND; // 毫秒

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return 格式化后的日期
     */
    public static Date parse(String strDate) {
        return parse(strDate, DATE_FULL_STR);
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return 格式化后的日期
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 两个时间比较
     *
     * @param date 传入的日期
     * @return 结果 1表示传入时间大于当前时间 -1表示传入时间小于当前时间 0表示相等
     */
    public static int compareDateWithNow(Date date) {
        Date date2 = new Date();
        int rnum = date.compareTo(date2);
        return rnum;
    }

    /**
     * 两个时间比较(时间戳比较)
     *
     * @param date 传入的日期
     * @return 结果 1表示传入时间大于当前时间 -1表示传入时间小于当前时间 0表示相等
     */
    public static int compareDateWithNow(long date) {
        long date2 = dateToUnixTimestamp();
        if (date > date2) {
            return 1;
        } else if (date < date2) {
            return -1;
        } else {
            return 0;
        }
    }


    /**
     * 获取系统当前时间 yyyy-MM-dd HH:mm:ss 形式
     *
     * @return 当前时间
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }

    /**
     * 获取系统当前时间
     *
     * @param type format类型
     * @return 当前时间
     */
    public static String getNowTime(String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        return df.format(new Date());
    }

    /**
     * 将指定的日期转换成Unix时间戳
     *
     * @param date 需要转换的日期 yyyy-MM-dd HH:mm:ss
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 将指定的日期转换成Unix时间戳
     *
     * @param date       需要转换的日期 yyyy-MM-dd
     * @param dateFormat 转换的格式
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp(String date, String dateFormat) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 将当前日期转换成Unix时间戳
     *
     * @return long 时间戳
     */
    public static long dateToUnixTimestamp() {
        long timestamp = new Date().getTime();
        return timestamp;
    }


    /**
     * 将Unix时间戳转换成日期
     *
     * @param timestamp 时间戳
     * @param format    转换的格式
     * @return String 日期字符串
     */
    public static String unixTimestampToDate(long timestamp, String format) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }


    /**
     * 按照 yyyy-MM-dd 格式化日期（java.util.Date to 字符串）
     *
     * @param date 日期
     * @return 格式化后的日期
     */
    public static String formatDate(Date date) {
        if (date == null)
            date = now();
        return (formatDate(date, "yyyy-MM-dd"));
    }

    /**
     * 按照 yyyy-MM-dd HH:mm:ss 格式化日期（java.util.Date to 字符串）
     *
     * @param date 日期
     * @return 格式化后的日期
     */
    public static String formatDateTime(Date date) {
        if (date == null)
            date = now();
        return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 按照 HH:mm:ss 格式化日期（java.util.Date to 字符串）
     *
     * @param date 日期
     * @return 格式化后的日期
     */
    public static String formatTime(Date date) {
        if (date == null)
            date = now();
        return (formatDate(date, "HH:mm:ss"));
    }

    /**
     * Date类型转时间核心方法，默认转换格式为今天的日期格式（java.util.Date to 字符串）
     *
     * @param date    日期，若null默认今天
     * @param pattern 格式
     * @return 时间格式字符串
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null)
            date = now();
        if (pattern == null)
            pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return (sdf.format(date));
    }

    // --------------------- 2. --------------------------------

    /**
     * 按照 yyyy-MM-dd 格式化日期（字符串 to java.util.Date）
     *
     * @param date yyyy-MM-dd 格式的时间字符串
     * @return 格式化后的日期
     */
    public static Date parseDate(String date) {
        if (date == null)
            return now();
        return parseDate(date, "yyyy-MM-dd");
    }

    /**
     * 按照 yyyy-MM-dd HH:mm:ss 格式化日期（字符串 to java.util.Date）
     *
     * @param datetime yyyy-MM-dd HH:mm:ss格式的时间字符串
     * @return 格式化后的日期
     */
    public static Date parseDateTime(String datetime) {
        if (datetime == null)
            return now();
        return parseDate(datetime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 截断给定日期，只留下 yyyy-MM-dd （去除时间部分）
     *
     * @param datetime 给定日期
     * @return 截断后的时间
     */
    public static Date parseDate(Date datetime) {
        if (datetime == null)
            return now();
        return parseDate(datetime, "yyyy-MM-dd");
    }

    /**
     * 截断给定日期，只留下 yyyy-MM-dd HH:mm:ss （去除毫秒部分）
     *
     * @param datetime 给定日期
     * @return 截断后的时间
     */
    public static Date parseDateTime(Date datetime) {
        if (datetime == null)
            return now();
        return parseDate(datetime, "yyyy-MM-dd  HH:mm:ss");
    }

    /**
     * 按照给定格式修正日期中的部分
     *
     * @param datetime 要格式的日期
     * @param pattern  格式
     * @return 转换后的日期
     */
    public static Date parseDate(Date datetime, String pattern) {
        if (datetime == null)
            return now();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(formatter.format(datetime));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Date类型转时间核心方法，默认转换格式为今天的日期格式
     *
     * @param date    要格式的日期
     * @param pattern 格式
     * @return 转换后的日期
     */
    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        if ((date == null) || (date.equals(""))) {
            return now();
        } else {
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    /**
     * 按照 yyyy-MM-dd 格式化<b> 当前 </b>日期
     *
     * @return 当前日期字符串
     */
    public static String formatDate() {
        return (formatDate(now(), "yyyy-MM-dd"));
    }

    /**
     * 按照 yyyy-MM-dd HH:mm:ss 格式化<b> 当前 </b>日期（字符串 to java.util.Date）
     *
     * @return 当前日期字符串
     */
    public static String formatDateTime() {
        return (formatDate(now(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 按照 HH:mm:ss 格式化<b> 当前 </b>日期（字符串 to java.util.Date）
     *
     * @return 字符串
     */
    public static String formatTime() {
        return (formatDate(now(), "HH:mm:ss"));
    }

    /**
     * 按照指定 <i>patten</i> 格式化<b> 当前 </b>日期（字符串 to java.util.Date）
     *
     * @param patten 格式
     * @return 字符串
     */
    public static String formatDate(String patten) {
        return (formatDate(now(), patten));
    }

    /**
     * 获取当前 java.util.Date <i>除去时间部分</i> 日期
     *
     * @return java.util.Date
     */
    public static Date nowDate() {
        return parseDate(formatDate());
    }

    /**
     * 获取当前 java.util.Date <i>除去毫秒部分</i> 日期时间
     *
     * @return java.util.Date
     */
    public static Date nowDateTime() {
        return parseDateTime(formatDateTime());
    }


    /**
     * 计算单个日期的加法运算
     *
     * @param field  如
     *               <ul>
     *               <li>DateUtil.SECOND</li>
     *               <li>DateUtil.MINUTE</li>
     *               <li>DateUtil.HOUR</li>
     *               <li>DateUtil.DATE</li>
     *               <li>...</li>
     *               </ul>
     * @param date   指定日期 默认今日
     * @param amount 移动差值
     * @return 计算后的日期
     */
    public static Date add(Date date, int field, int amount) {
        if (date == null)
            date = now();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);

        return cal.getTime();
    }

    /**
     * 计算单个日期的加法运算，如果要减法的话 amount为负数，如-2即为日期减二
     *
     * @param date   日期
     * @param amount 天数 正数为加，负数为减
     * @return 预算后的日期
     */
    public static Date addDay(Date date, int amount) {
        return add(date, DateUtils.DATE, amount);
    }

    /**
     * 计算单个日期的加法运算，如果要减法的话 amount为负数，如-2即为日期减二
     *
     * @param date   日期
     * @param amount 月数数 正数为加，负数为减
     * @return 预算后的日期
     */
    public static Date addMonth(Date date, int amount) {
        return add(date, DateUtils.MONTH, amount);
    }

    /**
     * 计算单个日期的加法运算，如果要减法的话 amount为负数，如-2即为日期减二
     *
     * @param date   日期
     * @param amount 小时数 正数为加，负数为减
     * @return 预算后的日期
     */
    public static Date addHour(Date date, int amount) {
        return add(date, DateUtils.HOUR, amount);
    }

    /**
     * 日期的前一个月
     *
     * @param date 要计算的日期
     * @return 一个月前的日期
     */
    public static Date preMonth(Date date) {
        return add(date, DateUtils.MONTH, -1);
    }

    /**
     * 日期的后一个月
     *
     * @param date 要计算的日期
     * @return 一个月后的日期
     */
    public static Date nextMonth(Date date) {
        return add(date, DateUtils.MONTH, 1);
    }

    /**
     * 日期的前一天
     *
     * @param date 要计算的日期
     * @return 一天前的日期
     */
    public static Date preDay(Date date) {
        return add(date, DateUtils.DATE, -1);
    }

    /**
     * 日期的后一天
     *
     * @param date 要计算的日期
     * @return 一天后的日期
     */
    public static Date nextDay(Date date) {
        return add(date, DateUtils.DATE, 1);
    }

    /**
     * 计算两个日期之间的差值，第二参数减第一参数(d-k)
     *
     * @param i 固定下面几项
     *          <ul>
     *          <li>DateUtil.SECOND</li>
     *          <li>DateUtil.MINUTE</li>
     *          <li>DateUtil.HOUR</li>
     *          <li>DateUtil.DATE</li>
     *          </ul>
     * @param k 日期1
     * @param d 日期2
     * @return 差
     */
    public static int diffDate(int i, Date k, Date d) {
        int diffnum = 0;
        int needdiff = 0;
        switch (i) {
            case DateUtils.SECOND: {
                needdiff = 1000;
                break;
            }
            case DateUtils.MINUTE: {
                needdiff = 60 * 1000;
                break;
            }
            case DateUtils.HOUR: {
                needdiff = 60 * 60 * 1000;
                break;
            }
            case DateUtils.DATE: {
                needdiff = 24 * 60 * 60 * 1000;
                break;
            }
        }
        if (needdiff != 0) {
            diffnum = (int) (d.getTime() / needdiff)
                    - (int) (k.getTime() / needdiff);
            ;
        }
        return diffnum;
    }


    /**
     * 获取当前 java.util.Date 格式完整日期
     *
     * @return java.util.Date
     */
    public static Date now() {
        return (new Date());
    }
}