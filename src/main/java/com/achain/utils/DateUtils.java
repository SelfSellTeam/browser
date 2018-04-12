package com.achain.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by qiangkz on 2017/8/9.
 */
public class DateUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTimeoneEight(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.HOUR_OF_DAY, 8);//因为是数据是0区
//        return simpleDateFormat.format(cal.getTime());
        return simpleDateFormat.format(date);
    }

    /**
     * 求给定时间和当前时间的经过时间
     *
     * @param givenDatetime 给定时间,0区时间
     * @return 返回经过时间的描述，如“1 second ago”,"2 minutes ago"
     */
    public static String calcElapsedTime(Date givenDatetime) {
        long from = givenDatetime.getTime();
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.HOUR_OF_DAY, -8);
        long to = cal.getTimeInMillis();
        int hours = (int) ((to - from) / (1000 * 60 * 60));
        if (hours > 0 && hours <= 24) {
            if (hours == 1) {
                return hours + " hour ago";
            }
            return hours + " hours ago";
        }
        int minutes = (int) ((to - from) / (1000 * 60));
        if (minutes > 0 && minutes < 60) {
            if (minutes == 1) {
                return minutes + " minute ago";
            }
            return minutes + " minutes ago";
        }
        int seconds = (int) ((to - from) / (1000));
        if (seconds <= 0) {
            seconds = 1;
        }
        if (seconds > 0 && seconds < 60) {
            if (seconds == 1) {
                return seconds + " second ago";
            }
            return seconds + " seconds ago";
        }
        return simpleDateFormat.format(givenDatetime);
    }
}
