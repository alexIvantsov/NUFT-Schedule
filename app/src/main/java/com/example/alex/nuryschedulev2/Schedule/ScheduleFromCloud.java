package com.example.alex.nuryschedulev2.Schedule;

import android.content.Context;
import android.util.Log;

import com.example.alex.nuryschedulev2.Model.Day;
import com.example.alex.nuryschedulev2.Model.Lesson;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScheduleFromCloud implements Schedule{

    private String PARSE_APPLICATION_ID = "pFiAg2zTsvmitBqBKgKyCAciEAPtMuQL4Mi1YLk9";
    private String PARSE_CLIENT_KEY = "LecfwPa2Q29BSCEBa5QSvspWDOM8a9egknO7sh2l";
    private String TAG = "MyLog";
    private ArrayList<ParseObject> pinList;
    private ArrayList<ParseObject> bufferPinList;


    private Day [] weekOne;
    private Day [] weekTwo;
    private String name;

    public ScheduleFromCloud(Context context){
        name = "";
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
    }

    @Override
    public boolean hasSavedSchedule() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Groups");
        query.fromLocalDatastore();
        List<ParseObject> parseObjects = null;
        try {
            parseObjects = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(parseObjects != null)
            if(parseObjects.size() != 0)
                return true;
        return false;
    }

    @Override
    public boolean hasLoadedSchedule() {
        return ((weekOne != null && weekTwo != null) &&(weekOne.length != 0 || weekTwo.length != 0));
    }

    @Override
    public boolean loadShedule(String name) {
        weekOne = new Day[5];
        weekTwo = new Day[5];

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Groups");
        query.fromLocalDatastore();
        List<ParseObject> groupList = null;
        try {
            groupList = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        for(ParseObject object: groupList){
            query = ParseQuery.getQuery("Week");
            query.whereEqualTo("GroupId", object);
            query.fromLocalDatastore();
            List<ParseObject> weekList = null;
            try {
                weekList = query.find();
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
            if(weekList != null && weekList.size() != 0) {
                name = object.getString("name");
                break;
            }
        }
        return load(name, false);
    }

    @Override
    public boolean updateShedule(String name) {
        weekOne = new Day[5];
        weekTwo = new Day[5];
        bufferPinList = new ArrayList<>();
        if(load(name, true)){
            pinList = bufferPinList;
            saveSchedule();
            return true;
        }
        return false;
    }

    public boolean load(String name, boolean fromCloud) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Groups");
        if(!fromCloud) query.fromLocalDatastore();
        query.whereEqualTo("name", name);
        List<ParseObject> groupList = null;
        try {
            groupList = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if(fromCloud)bufferPinList.addAll(groupList);
        for (ParseObject object : groupList) {
            Log.d(TAG, (String) object.get("name"));
            if(!loadWeeks(object, fromCloud))
                return false;
        }
        this.name = name;
        return true;
    }

    private boolean loadWeeks(ParseObject group, boolean fromCloud){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Week");
        if(!fromCloud) query.fromLocalDatastore();
        query.whereEqualTo("GroupId", group);
        List<ParseObject> weekList = null;
        try {
            weekList = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if(fromCloud)bufferPinList.addAll(weekList);
        for (ParseObject object : weekList) {
            if(!loadDays(object, fromCloud))
                return false;
        }
        return true;
    }

    private boolean loadDays(final ParseObject week, boolean fromCloud){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Day");
        if(!fromCloud) query.fromLocalDatastore();
        query.whereEqualTo("WeekId", week);
        List<ParseObject> dayList = null;
        try {
            dayList = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if(fromCloud)bufferPinList.addAll(dayList);
        for (ParseObject object : dayList) {
            Log.d(TAG, (String) object.get("name"));
            Day day = new Day((String)object.get("name"), object.getInt("number"));
            int numberWeek = week.getInt("number");
            if(numberWeek == 1){
                weekOne[object.getInt("number")] = day;
            }else{
                weekTwo[object.getInt("number")] = day;
            }
            if(!loadLessons(object, day, fromCloud))
                return false;
        }
        return true;
    }

    private boolean loadLessons(ParseObject objectDay, final Day day, boolean fromCloud){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Lesson");
        if(!fromCloud) query.fromLocalDatastore();
        query.whereEqualTo("DayId", objectDay);
        List<ParseObject> lessonList = null;
        try {
            lessonList = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if(fromCloud)bufferPinList.addAll(lessonList);
        for (ParseObject object : lessonList) {
            String name = object.getString("name");
            String type = object.getString("type");
            int number = Integer.parseInt(object.getString("number"))+1;
            String teacher = object.getString("teacher");
            String time = object.getString("time");
            String place = object.getString("place");
            Lesson lesson = new Lesson(number, name, type, place, time, teacher);

            day.getLessonList().add(lesson);
        }
        day.sortLessons();
        return true;
    }

    @Override
    public ArrayList<Day> getWeek(int number) {
        if (number == 1) {
            return new ArrayList<>(Arrays.asList(weekOne));
        } else {
            return new ArrayList<>(Arrays.asList(weekTwo));
        }
    }

    private void saveSchedule(){
        try {
            ParseObject.unpinAll("currentGroup");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ParseObject.pinAllInBackground("currentGroup", pinList);

    }

    @Override
    public String getGroupName() {
        return this.name;
    }

    @Override
    public ArrayList<String> getGroupsNameList() {
        ArrayList<String> names = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Groups");
        List<ParseObject> groups = null;
        try {
            groups = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        for(ParseObject object: groups){
            names.add(object.getString("name"));
        }
        try {
            ParseObject.unpinAll("group_object");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ParseObject.pinAllInBackground("group_object", groups);
        return names;
    }


}
