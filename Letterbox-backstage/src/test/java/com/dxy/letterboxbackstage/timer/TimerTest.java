package com.dxy.letterboxbackstage.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: JasonD
 * @date: 2023/6/24 20:47
 * @Description:
 */
public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer();
        //schedule 第一个参数为定时任务，第二个参数表示第一次调用的时间，第三个参数每隔多久执行一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("now: " + sdf.format(calendar.getTime()));
            }
        }, 2000, 2000);
    }
}
