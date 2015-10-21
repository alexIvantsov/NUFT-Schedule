package com.example.alex.nuryschedulev2.Schedule;

import android.content.Context;

public class ScheduleFactory {

    private static Schedule schedule;

    private ScheduleFactory(){}

    public synchronized static Schedule getSchedule(Context context){
        if(schedule == null){
            schedule = new ScheduleFromCloud(context);
        }
        return schedule;
    }
}
