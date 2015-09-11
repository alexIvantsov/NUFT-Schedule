package com.example.alex.nuryschedulev2;

import com.example.alex.nuryschedulev2.Model.Day;
import com.example.alex.nuryschedulev2.Model.Group;
import com.example.alex.nuryschedulev2.Model.Lesson;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alex on 12.06.15.
 */
public class Schedule {

    private String groupName;
    private Day[] firstWeek;
    private Day[] secondWeek;
    public static Schedule schedule;

    public Schedule(String groupName){
        this.groupName = groupName;
        this.firstWeek = new Day[6];
        this.secondWeek = new Day[6];

    }

    public void getSchedule(String groupName){
        splitSchedule();
    }

    public ArrayList<Day> getWeek(int numberWeek){
        if(numberWeek == 0){
            return new ArrayList<Day>(Arrays.asList(firstWeek));
        }else{
            return new ArrayList<Day>(Arrays.asList(secondWeek));
        }
    }

    public String getGroupName() {
        return groupName;
    }

    private void splitSchedule(){
        JSONArray jsonArray = downloadJSONSchedule(groupName);
        ArrayList<JSONObject> daySchedule = new ArrayList<JSONObject>();
        ArrayList<JSONObject> dayFirstWeek = new ArrayList<JSONObject>();
        ArrayList<JSONObject> daySecondWeek = new ArrayList<JSONObject>();
        try {
            for (int i = 0; i < 6; i++) {
                daySchedule = new ArrayList<JSONObject>();
                for (int j = 0; j < jsonArray.length(); j++) {
                    if (new Timestamp(jsonArray.getJSONObject(j).getLong("start")).getDay() == i) {
                        daySchedule.add(jsonArray.getJSONObject(j));
                    }
                }
                if (daySchedule.size() != 0) {
                    dayFirstWeek = new ArrayList<JSONObject>();
                    daySecondWeek = new ArrayList<JSONObject>();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd");
                    Date date = new Date(daySchedule.get(0).getLong("start"));
                    String time = sdf.format(date);
                    for (int j = 0; j < daySchedule.size(); j++) {
                        if (time.equals(sdf.format(new Date(daySchedule.get(j).getLong("start"))))) {
                            dayFirstWeek.add(daySchedule.get(j));
                        } else {
                            daySecondWeek.add(daySchedule.get(j));
                        }
                    }
                    // Перевірка чи співпадають пари з тижнем, якщо ні - мінять місцями
                    if (daySecondWeek.size() != 0) {
                        int j = 0;
                        while (daySecondWeek.get(0) == null) j++;
                        if (Integer.parseInt(time) > Integer.parseInt(sdf.format(new Date(daySecondWeek.get(j).getLong("start"))))) {
                            ArrayList<JSONObject> buffer = dayFirstWeek;
                            dayFirstWeek = daySecondWeek;
                            daySecondWeek = buffer;
                        }
                    }
                    Day day = new Day(i);
                    for (int j = 0; j < dayFirstWeek.size(); j++) {
                        if (dayFirstWeek.get(j) != null) {
                            String[] parametr = dayFirstWeek.get(j).getString("title").split("-");
                            int numberLesson = Integer.parseInt(parametr[0]);
                            String nameLesson = parametr[1];
                            String typeLesson = dayFirstWeek.get(j).getString("fieldsdata").split(",")[0];
                            String timeLesson = Lesson.timeLessonList[numberLesson - 1];
                            String placeLesson = parametr[2] + "-" + parametr[3].split("\\(")[0];
                            Lesson lesson = new Lesson(numberLesson, nameLesson, typeLesson, placeLesson, timeLesson);
                            day.getLessonList().add(lesson);
                        }
                    }
                    day.sortLessons();
                    this.firstWeek[i] = day;
                    day = new Day(i);
                    for (int j = 0; j < daySecondWeek.size(); j++) {
                        if (daySecondWeek.get(j) != null) {
                            String[] parametr = daySecondWeek.get(j).getString("title").split("-");
                            int numberLesson = Integer.parseInt(parametr[0]);
                            String nameLesson = parametr[1];
                            String typeLesson = daySecondWeek.get(j).getString("fieldsdata").split(",")[0];
                            String timeLesson = Lesson.timeLessonList[numberLesson - 1];
                            String placeLesson = parametr[2] + "-" + parametr[3].split("\\(")[0];
                            Lesson lesson = new Lesson(numberLesson, nameLesson, typeLesson, placeLesson, timeLesson);
                            day.getLessonList().add(lesson);
                        }
                    }
                    day.sortLessons();
                    this.secondWeek[i] = day;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray downloadJSONSchedule(String groupName){
        JSONArray jsonArray = null;
        DefaultHttpClient hc = new DefaultHttpClient();
        ResponseHandler<String> res = new BasicResponseHandler();
        HttpGet http = new HttpGet(Group.getAddress(groupName));
        String response = null;
        try {
            response = hc.execute(http, res);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            jsonArray = new JSONObject(response).getJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
