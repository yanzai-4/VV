package AI.Mind.General;

// ##### 简介 #####
// 计算机时间
// 返回当前计算机时间

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

import java.util.Calendar;

public class Timer {

    // ##### 变量 #####
    // 当前时间
    Calendar time;

    // ##### 功能函数 #####
    // 返回当前年份
    public int getYear() {
        time = Calendar.getInstance();
        return time.get(Calendar.YEAR);
    }

    // 返回当前月份
    public int getMonth() {
        time = Calendar.getInstance();
        return time.get(Calendar.MONTH);
    }

    // 返回当前日期
    public int getDate() {
        time = Calendar.getInstance();
        return time.get(Calendar.DATE);
    }

    // 返回当前小时
    public int getHour() {
        time = Calendar.getInstance();
        return time.get(Calendar.HOUR_OF_DAY);
    }

    // 返回当前分钟
    public int getMinute() {
        time = Calendar.getInstance();
        return time.get(Calendar.MINUTE);
    }

    // 返回当前秒
    public int getSecond() {
        time = Calendar.getInstance();
        return time.get(Calendar.SECOND);
    }

    // 返回当前时间戳
    public String getTime() {
        time = Calendar.getInstance();
        return time.get(Calendar.YEAR) + "." +
                time.get(Calendar.MONTH) + "." +
                time.get(Calendar.DATE) + "_" +
                time.get(Calendar.HOUR_OF_DAY) + "." +
                time.get(Calendar.MINUTE) + "." +
                time.get(Calendar.SECOND);
    }
}
