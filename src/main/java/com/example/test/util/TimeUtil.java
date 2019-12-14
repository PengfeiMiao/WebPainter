package com.example.test.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtil {

    public static String YEAR_PATTERN = "yyyy";

    public static String YEAR_MONTH_PATTERN = "yyyy-MM";

    public static String YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd";

    public static String YEAR_MONTH_DAY_HOUR_MIN_PATTERN = "yyyy-MM-dd HH:mm";

    public static String YEAR_MONTH_DAY_HOUR_MIN_SEC_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String YEAR_MONTH_DAY_HOUR_MIN_SEC_PATTERN2 = "yyyyMMddHHmmss";

    public static String HOUR_MIN_PATTERN = "HH:mm";

    public static long ONE_HOUR = 60 * 60 * 1000;

    /**
     * Created by luojizhou on 2017/08/21
     * 将 日期字符串 转换为 日期(Date)对象
     *
     * @param dateString
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dateString, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Created by luojizhou on 2017/08/21
     * 将  日期(Date)对象 转换为 日期字符串
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateString = null;
        try {
            dateString = sdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static boolean beforeDate(Date curDate, Date compareDate) {
        boolean before = false;

        if (curDate.getTime() <= compareDate.getTime()) {
            before = true;
        }

        return before;
    }

    /**
     * 计算日期之间的差值
     *
     * @param
     * @param
     * @return
     */
    public static int differentDaysByMillisecond(Date oldDate, Date newDate) {
        if (newDate == null || oldDate == null) {
            return -1;
        }
        int days = (int) ((newDate.getTime() - oldDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public static String getYearPattern() {
        return YEAR_PATTERN;
    }

    public static String getYearMonthPattern() {
        return YEAR_MONTH_PATTERN;
    }

    public static String getYearMonthDayPattern() {
        return YEAR_MONTH_DAY_PATTERN;
    }

    public static String getYearMonthDayHourMinPattern() {
        return YEAR_MONTH_DAY_HOUR_MIN_PATTERN;
    }

    public static String getYearMonthDayHourMinSecPattern() {
        return YEAR_MONTH_DAY_HOUR_MIN_SEC_PATTERN;
    }

    public static String getHourMinPattern() {
        return HOUR_MIN_PATTERN;
    }

    /**
     * Created by luojizhou on 2017/08/25
     * 判断当前时间是否在指定的时间范围内
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean currentDateIsBetweenPeriod(Date startDate, Date endDate) {

        Date currentDate = Calendar.getInstance().getTime();
        if (startDate.getTime() <= currentDate.getTime() && currentDate.getTime() <= endDate.getTime()) {
            return true;
        }
        return false;
    }

    public Date convertStringToDate(String dateString) throws ParseException {
        /**
         * 小写的mm表示的是分钟
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = sdf.parse(dateString);
        return date;
    }

    public Date convertStringToDate(String dateString, String formatString) throws ParseException {
        /**
         * 小写的mm表示的是分钟
         */
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        Date date = sdf.parse(dateString);
        return date;
    }

    public String convertDateToString(Date date) {
        /**
         * 小写的mm表示的是分钟
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        return dateString;
    }

    /**
     * 给指定日期加上天数
     *
     * @param date
     * @param day
     * @return
     */
    public Date addDate(Date date, Long day) {
        /**
         * 得到指定日期的毫秒数
         */
        Long time = date.getTime();
        /**
         * 要加上的天数转换成毫秒数
         */
        day = day * 24 * 60 * 60 * 1000;
        /**
         * 相加得到新的毫秒数
         */
        time += day;
        /**
         * 将毫秒数转换成日期
         */
        return new Date(time);
    }

    /**
     * 判断两个时间段是否存在相交的时间段
     *
     * @param startTime1
     * @param endTime1
     * @param startTime2
     * @param endTime2
     * @return
     * @author xiaoyin
     * @date 2019/7/31 9:23
     */
    public static boolean isContain(String startTime1, String endTime1, String startTime2, String endTime2) {
        if (StringUtil.hasOneBlank(startTime1, endTime1, startTime2, endTime2)) {
            return false;
        }
        try {
            Date startDate1 = new SimpleDateFormat("HH:mm").parse(startTime1);
            Date endDate1 = new SimpleDateFormat("HH:mm").parse(endTime1);
            Date startDate2 = new SimpleDateFormat("HH:mm").parse(startTime2);
            Date endDate2 = new SimpleDateFormat("HH:mm").parse(endTime2);
            long aStart = startDate1.getTime();
            long aEnd = endDate1.getTime();

            long bStart = startDate2.getTime();
            long bEnd = endDate2.getTime();

            // a0 包在了 b0 ~ b1 之间
            if (aStart >= bStart && aStart <= bEnd) {
                return true;
            }

            // b0 包在了 a0 ~ a1 之间
            if (aStart <= bStart && aEnd >= bStart) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 时间格式转换方法
     *
     * @param converterStr 需要转换的字符串
     * @param sourceFormat 源模式
     * @param targetFormat 目标模式
     * @return
     * @author ff
     */
    public static String timeConverter(String converterStr, String sourceFormat, String targetFormat) {
        Date date = stringToDate(converterStr, sourceFormat);

        converterStr = dateToString(date, targetFormat);

        return converterStr;
    }

    /**
     * 两个时间相减
     *
     * @param subtracted         被减数
     * @param subtractedPattern  被减数模式
     * @param subtraction        减数
     * @param subtractionPattern 减数模式
     * @return
     */
    public static long timeSubtract(String subtracted, String subtractedPattern, String subtraction, String subtractionPattern) {
        /**
         * 获取被减数时间毫秒
         */
        Date subtractedDate = stringToDate(subtracted, subtractedPattern);
        long subtractedMill = subtractedDate.getTime();
        /**
         * 获取减数时间毫秒
         */
        Date subtractionDate = stringToDate(subtraction, subtractionPattern);
        long subtractionMill = subtractionDate.getTime();

        if (subtractedMill >= subtractionMill) {
            return subtractedMill - subtractionMill;
        } else {
            return subtractionMill - subtractedMill;
        }
    }
}
