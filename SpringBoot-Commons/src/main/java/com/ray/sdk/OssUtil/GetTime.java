package com.ray.sdk.OssUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {
    /**
     * 获取系统的当前时间
     *
     * @return String
     */
    public static String PreTime() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日hh时mm分");//设置日期格式
        String format = df.format(date);

        return format;
    }

    /**
     * 获取当天
     *
     * @return String
     */
    public static String getToday() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * 获取当月
     *
     * @return String
     */
    public static String getMonth() {
        return new SimpleDateFormat("yyyy-MM").format(new Date());
    }

    /**
     * 根据传入时间 获取当月
     *
     * @param time
     * @return
     */
    public static String getLastMonthFirstDate(Date time) {

        return new SimpleDateFormat("yyyy-MM").format(time);

    }


}
