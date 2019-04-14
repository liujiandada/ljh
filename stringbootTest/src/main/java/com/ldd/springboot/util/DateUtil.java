package com.ldd.springboot.util;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    private static SimpleDateFormat Hm = new SimpleDateFormat(
            "HH:mm");
    private static SimpleDateFormat Hms = new SimpleDateFormat(
            "HH:mm:ss");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat(
            "yyyy年MM月dd日");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf_hour = new SimpleDateFormat(
            "HH:mm");
    private static SimpleDateFormat sdf_mouth = new SimpleDateFormat(
            "MM月dd日 HH:mm");


    private static final Long THREE_MINUTE_TIME = 1000 * 3 * 60L;
    private static final Long ONE_HOUR_TIME = 1000 * 3600L;
    private static final Long ONE_DAY_TIME = 1000 * 3600 * 24L;
    private static final Long TWO_DAY_TIME = 1000 * 3600 * 24 * 2L;
    //判断节假日的api接口
    static String HTTP_HOLIDAY_URL = "http://timor.tech/api/holiday/info/";


    /**
     * 获取这个月的天数
     * @param date
     * @return
     */
    public static Integer getMaxDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     *  字符串转换为时间戳 构造参数: @param time
     * 需转换时间
     *
     * @return: Integer 时间戳
     *
     */
    public static Integer getTime(String time) {
        String re_time = null;
        Date d;
        try {
            d = sdf1.parse(time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(re_time);
    }

    /**
     *将时间戳转为字符串 构造参数: @param time
     * 需转换时间
     *
     * @return: String 时间戳
     *
     */
    public static String getStrTime(Integer time) {
        String re_StrTime = null;
        long lcc_time = Long.valueOf(time);
        re_StrTime = sdf1.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 计算两个日期差几天
     * @param startTime
     * @param endTime
     * @return
     */
    public static Integer getDifference (Date startTime ,Date  endTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        Integer start = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(endTime);
        Integer end = calendar.get(Calendar.DAY_OF_MONTH);
        return end - start >= 0 ? end -start + 1 : 0;
    }

    /**
     * 字符串转Date
     * @param time
     * @return
     */
    public static Date getDateStr(String time){
        Date date = null;
        try {
            date = sdf1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 比较时间大小
     * @param one
     * @param two
     * @return
     */
    public static boolean EqTime(String one,String two) {
        try {
            Date dt1 = Hm.parse(one);
            Date dt2 = Hm.parse(two);
            return dt1.getTime()>=dt2.getTime() ? true : false;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     *判断是否 节假日
     */
    public static boolean isHoliday (Date date){
        String str  = sdf1.format(date);
        String jsonResult = request(HTTP_HOLIDAY_URL, str);
        JSONObject json = JSONObject.parseObject(jsonResult);
        if("0".equals(json.getString("code"))){
            if(null != json.getString("holiday") && !"".equals(json.getString("holiday") )){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param httpUrl
     * @param httpArg
     * @return
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl +  httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得两个日期区间的集合
     * @param start
     * @param end
     * @return
     */
    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 判断日期是否是周末
     * @param bDate
     * @return
     * @throws ParseException
     */
    public static boolean isWeekend(Date bDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(bDate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }

    }
}
