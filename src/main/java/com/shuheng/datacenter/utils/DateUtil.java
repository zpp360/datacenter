package com.shuheng.datacenter.utils;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    // 年
    public final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    // 年月日
    public final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    // 年月日
    public final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    // 年月日时分秒
    public final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static SimpleDateFormat sdfTimeNoSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public static String dataFormat(Date date, SimpleDateFormat format){
        return format.format(date);
    }

    /**
     * 获取YYYY格式
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     * @param time
     * @return
     */
    public static String getDay(Timestamp time) {
        return sdfDay.format(time);
    }

    /**
     * 获取YYYYMMDD格式
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm格式
     * @return
     */
    public static String getTimeNoSecond() {
        return sdfTimeNoSecond.format(new Date());
    }

    /**
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     * @throws @author
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验日期是否合法
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            fmt.setLenient(false);//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            try {
                fmt.setLenient(false);//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期
                fmt.parse(s);
                return true;
            } catch (Exception e1) {//yyyyMMdd
                if(s.length()!=8){//年月日中间无隔断时，长度为8位
                    return false;
                }
                s = s.substring(0,4) + "-" + s.substring(4,6) + "-" + s.substring(6,8);
                fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    fmt.setLenient(false);//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期
                    fmt.parse(s);
                    return true;
                } catch (Exception e2) {
                    return false;
                }
            }
        }
    }




    public static void main(String[] ags){

    }
}
