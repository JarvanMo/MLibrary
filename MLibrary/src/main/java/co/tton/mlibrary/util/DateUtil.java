package co.tton.mlibrary.util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2015/8/29.
 * get Beijing current time
 */
public class DateUtil {



    public static void  setTimeZone(String timeZone){
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
//        "GMT+8"
    }


    public static String getTimeZoneID(){

        return  TimeZone.getDefault().getID();
    }
    public static String getMonth() {

        Calendar cal = Calendar.getInstance();
        //       cal.set(Calendar.DAY_OF_MONTH,1);

        return (cal.get(Calendar.MONTH) + 1) + "";
    }


    public static String getDay() {

        Calendar cal = Calendar.getInstance();

        return cal.get(Calendar.DATE) + "";
    }

    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.DAY_OF_MONTH,1);
        String result;

        switch (cal.get(Calendar.DAY_OF_WEEK)) {

            case Calendar.MONDAY:
                result = "星期一";
                break;
            case Calendar.TUESDAY:
                result = "星期二";
                break;
            case Calendar.WEDNESDAY:
                result = "星期三";
                break;
            case Calendar.THURSDAY:
                result = "星期四";
                break;
            case Calendar.FRIDAY:
                result = "星期五";
                break;
            case Calendar.SATURDAY:
                result = "星期六";
                break;
            case Calendar.SUNDAY:
                result = "星期日";
                break;
            default:
                result = "未知错误";
        }

        return result;
    }

    /**
     * @return 返回一个不带年份的日期的字符串
     */
    public static String getDateWithoutYear() {
        return getMonth() + "月" + getDay() + "日";
    }
}
