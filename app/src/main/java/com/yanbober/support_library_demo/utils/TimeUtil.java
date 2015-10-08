package com.yanbober.support_library_demo.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by hgx on 2015/3/30.
 */
public class TimeUtil {

    /**
     *  到分钟 一般聊天使用
     * @return 类似2012-10-31 18:11
     */
    public static String getCurrentDateMin() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
                + mins);

        return sbBuffer.toString();
    }
    /**
     *  到秒钟
     * @return 类似2012-10-31 18:11:12
     */
    public static String getCurrentDateSec() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);//Notice
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));
        String second = String.valueOf(c.get(Calendar.SECOND));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
                + mins+":"+second);

        return sbBuffer.toString();
    }


    /**
     * 时间转换 输入一个string 输出 yyyy/MM/dd HH:mm
     */
    public static String getDateFromStr(String strTime) {
        String date;
        if (strTime != null && !"".equals(strTime)) {

            long updateTime = Long.parseLong(strTime);
            SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            date = dateformat1.format(new Date(updateTime * 1000));
            return date;
        } else {
            return "";
        }
    }

    /**
     * 不乘1000
     * 时间转换 输入一个string 输出 yyyy-MM-dd
     */
    public static String getDateFromStr2(String strTime) {
        String date;
        if (strTime != null && !"".equals(strTime)) {

            long updateTime = Long.parseLong(strTime);
            SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
            date = dateformat1.format(new Date(updateTime));
            return date;
        } else {
            return "";
        }
    }


    /**
     * 将长时间格式字符串转换为字符串,默认为yyyy-MM-dd HH:mm:ss
     *
     * @param milliseconds
     *            long型时间,支持毫秒和秒
     *
     * @param dataFormat
     *            需要返回的时间格式，例如： yyyy-MM-dd， yyyy-MM-dd HH:mm:ss
     *
     * @return dataFormat格式的时间结果字符串
     */
    public static String long2Str(long milliseconds, String dataFormat) {
        long tempTimestamp = milliseconds > 9999999999L ? milliseconds : milliseconds *1000;
        if (TextUtils.isEmpty(dataFormat)) {
            dataFormat = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date(tempTimestamp * 1l);
        SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
        return formatter.format(date);
    }



    /**
     * @param week calendar.day
     * @return 星期几
     */
    public static String getFriendlyWeekStr(int week) {
        String result = "";
        switch (week) {
            case Calendar.MONDAY:
                result = "一";
                break;
            case Calendar.TUESDAY:
                result = "二";
                break;
            case Calendar.WEDNESDAY:
                result = "三";
                break;
            case Calendar.THURSDAY:
                result = "四";
                break;
            case Calendar.FRIDAY:
                result = "五";
                break;
            case Calendar.SATURDAY:
                result = "六";
                break;
            case Calendar.SUNDAY:
                result = "日";
                break;

        }

        return result;

    }



    public static String timeFormat(long date) {
        long ssaa = new Date().getTime();
        long delta = ssaa - date;
        final String ONE_SECOND_AGO = "秒前";
        final String ONE_MINUTE_AGO = "分钟前";
        final String ONE_HOUR_AGO = "小时前";
        final String ONE_DAY_AGO = "天前";
        final long ONE_MINUTE = 60000L;
        final long ONE_HOUR = 3600000L;
        final long ONE_DAY = 86400000L;
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        } else {
            String time111 = getDateStr(date);
            return time111;
        }
    }


    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static String getDateStr(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        Formatter ft = new Formatter(Locale.CHINA);
        return ft.format("%1$tY年%1$tm月%1$td日", cal).toString();
    }



    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间字符串格式化成yyyy年MM月dd日格式的字符串
     *
     * @param dataString
     * @return
     */
    public static String paserData2Data(String dataString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l = 0;
        try {
            Date d = sdf.parse(dataString);
            l = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
        long lcc_time = Long.valueOf(l + "");
        return sdf1.format(new Date(lcc_time));
    }


    /**
     * @param timeStmp to yyyy-MM-dd HH:mm
     * @return
     */
    public static String paserDataNB(String timeStmp) {
        long i = Misc.parseLong(timeStmp, 0);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc_time = Long.valueOf(i + "");
        return sdf1.format(new Date(lcc_time));
    }

    /**
     * @param timeStmp to yyyy-MM-dd
     * @return
     */
    public static String paserDataNBB(String timeStmp) {
        long i = Misc.parseLong(timeStmp, 0);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        long lcc_time = Long.valueOf(i + "");
        return sdf1.format(new Date(lcc_time));
    }


    /**
     * 根据日期字符串判断当月第几周
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static int getWeek(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //第几天，从周日开始
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day - 1;
    }


    /**
     * 得到指定月的天数
     * @param year
     * @param month
     * @return
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    public static long oneHourMillis = 60 * 60 * 1000; // 一小时的毫秒数
    public static long oneDayMillis = 24 * oneHourMillis; // 一天的毫秒数
    public static long oneYearMillis = 365 * oneDayMillis; // 一年的毫秒数


    /**
     * 把日期毫秒转化为字符串。默认格式：yyyy-MM-dd HH:mm:ss。
     *
     * @param millis 要转化的日期毫秒数。
     * @return 返回日期字符串（如："2013-02-19 11:48:31"）。
     * @author wangjie
     */
    public static String millisToStringDate(long millis) {
        return millisToStringDate(millis, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 把日期毫秒转化为字符串。
     *
     * @param millis  要转化的日期毫秒数。
     * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。
     * @return 返回日期字符串。
     * @author wangjie
     */
    public static String millisToStringDate(long millis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(millis));

    }


    /**
     * 字符串解析成毫秒数
     *
     * @param str
     * @param pattern
     * @return
     */
    public static long string2Millis(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        long millis = 0;
        try {
            millis = format.parse(str).getTime();
        } catch (ParseException e) {
        }
        return millis;
    }

    /**
     * 时间格式：
     * 1小时内用，多少分钟前；
     * 超过1小时，显示时间而无日期；
     * 如果是昨天，则显示昨天
     * 超过昨天再显示日期；
     * 超过1年再显示年。
     *
     * @param millis
     * @return
     */
    public static String millisToLifeString(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (now - millis <= oneHourMillis && now - millis > 0l) { // 一小时内
            String m = millisToStringShort(now - millis, false, false);
            return "".equals(m) ? "1分钟内" : m + "前";
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // 大于今天开始开始值，小于今天开始值加一天（即今天结束值）
            return "今天 " + millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis) { // 大于（今天开始值减一天，即昨天开始值）
            return "昨天 " + millisToStringDate(millis, "HH:mm");
        }

        long thisYearStart = string2Millis(millisToStringDate(now, "yyyy"), "yyyy");
        if (millis > thisYearStart) { // 大于今天小于今年
            return millisToStringDate(millis, "MM月dd日 HH:mm");
        }

        return millisToStringDate(millis, "yyyy年MM月dd日 HH:mm");
    }



    /**
     * 把一个毫秒数转化成时间字符串。格式为小时/分/秒/毫秒（如：24903600 --> 06小时55分钟）。
     *
     * @param millis   要转化的毫秒数。
     * @param isWhole  是否强制全部显示小时/分。
     * @param isFormat 时间数字是否要格式化，如果true：少位数前面补全；如果false：少位数前面不补全。
     * @return 返回时间字符串：小时/分/秒/毫秒的格式（如：24903600 --> 06小时55分钟）。
     */
    public static String millisToStringShort(long millis, boolean isWhole, boolean isFormat) {
        String h = "";
        String m = "";
        if (isWhole) {
            h = isFormat ? "00小时" : "0小时";
            m = isFormat ? "00分钟" : "0分钟";
        }

        long temp = millis;

        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;

        if (temp / hper > 0) {
            if (isFormat) {
                h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            } else {
                h = temp / hper + "";
            }
            h += "小时";
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            if (isFormat) {
                m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                m = temp / mper + "";
            }
            m += "分钟";
        }

        return h + m;
    }

    /**
     * 时间格式：
     * 今天，显示时间而无日期；
     * 如果是昨天，则显示昨天
     * 超过昨天再显示日期；
     * 超过1年再显示年。
     *
     * @param millis
     * @return
     */
    public static String millisToLifeString2(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (millis > todayStart + oneDayMillis && millis < todayStart + 2 * oneDayMillis) { // 明天
            return "明天" + millisToStringDate(millis, "HH:mm");
        }
        if (millis > todayStart + 2 * oneDayMillis && millis < todayStart + 3 * oneDayMillis) { // 后天
            return "后天" + millisToStringDate(millis, "HH:mm");
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // 大于今天开始开始值，小于今天开始值加一天（即今天结束值）
            return "今天 " + millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis && millis < todayStart) { // 大于（今天开始值减一天，即昨天开始值）
            return "昨天 " + millisToStringDate(millis, "HH:mm");
        }

        long thisYearStart = string2Millis(millisToStringDate(now, "yyyy"), "yyyy");
        if (millis > thisYearStart) { // 大于今天小于今年
            return millisToStringDate(millis, "MM月dd日 HH:mm");
        }

        return millisToStringDate(millis, "yyyy年MM月dd日 HH:mm");
    }

    /**
     * 时间格式：
     * 今天，显示时间而无日期；
     * 如果是昨天，则显示昨天
     * 超过昨天再显示日期；
     * 超过1年再显示年。
     *
     * @param millis
     * @return
     */
    public static String millisToLifeString3(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (millis > todayStart + oneDayMillis && millis < todayStart + 2 * oneDayMillis) { // 明天
            return "明天";
        }
        if (millis > todayStart + 2 * oneDayMillis && millis < todayStart + 3 * oneDayMillis) { // 后天
            return "后天";
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // 大于今天开始开始值，小于今天开始值加一天（即今天结束值）
            return millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis && millis < todayStart) { // 大于（今天开始值减一天，即昨天开始值）
            return "昨天 ";
        }

        return millisToStringDate(millis, "MM月dd日");
    }

}
