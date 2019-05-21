package com.xws.xysz.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 枚举初始化
 * JokerYG
 * Date: 2018-11-22
 * Time: 10:28
 */
@Component
public class InitTask implements CommandLineRunner {

    @Autowired
    ServletContext servletContext;

    @Override
    public void run(String... args) {
        System.out.println("--- [定时任务] 开始初始化 ---");
        timedTask();
        System.out.println("--- [定时任务] 初始化完毕 ---");
    }

    ScheduledExecutorService ses;

    /**
     * 定时任务
     */
    private void timedTask() {
        ses = Executors.newScheduledThreadPool(20);
        Date now = new Date();
        Calendar nextHour = Calendar.getInstance();

        //定时更新申请额度表 凌晨12点
        nextHour = Calendar.getInstance();
        nextHour.add(GregorianCalendar.DATE, 1);
        nextHour.set(Calendar.HOUR_OF_DAY, 0);
        nextHour.set(Calendar.MINUTE, 0);
        nextHour.set(Calendar.SECOND, 0);
        nextHour.set(Calendar.MILLISECOND, 0);
        nextHour.getTime().toString();
        //起始时间
        long space = nextHour.getTimeInMillis() - now.getTime();
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
//                applyRecordService.updateState();
            }
        }, space, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
        System.out.println("--定时更新申请额度表--");

        //定时更新还款计划状态 凌晨12点
        nextHour = Calendar.getInstance();
        nextHour.add(GregorianCalendar.DATE, 1);
        nextHour.set(Calendar.HOUR_OF_DAY, 0);
        nextHour.set(Calendar.MINUTE, 0);
        nextHour.set(Calendar.SECOND, 0);
        nextHour.set(Calendar.MILLISECOND, 0);
        //起始时间
        long time_c = nextHour.getTimeInMillis() - now.getTime();
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

//                repaymentPlanService.updateState();
            }
        }, time_c, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
        System.out.println("--定时更新还款计划状态--");
    }
}
