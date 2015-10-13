package com.example.alex.nuryschedulev2.Schedule;

import android.content.Context;

/**
 * Created by Oleksandr on 04.10.2015.
 */
public class ScheduleFactory {

    private static Schedule schedule;

    private ScheduleFactory(){}

    public synchronized static Schedule getSchedule(){
        if(schedule == null){
            schedule = new MySchedule();
        }
        return schedule;
    }

    public static boolean loadSavedShedule(Context context){
        schedule = (MySchedule)Serealisation.readFromFile(context);
        return (schedule != null);
    }
}
