package com.example.alex.nuryschedulev2.Schedule;

import com.example.alex.nuryschedulev2.Model.Day;
import java.util.ArrayList;

/**
 * Created by Oleksandr on 04.10.2015.
 */
public interface Schedule {

    public boolean hasSavedSchedule();
    public boolean hasLoadedSchedule();
    public boolean loadShedule(String name);
    public boolean updateShedule(String name);
    public ArrayList<Day> getWeek(int number);
    public String getGroupName();
    public ArrayList<String> getGroupsNameList();
}
