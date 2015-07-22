package com.example.alex.nuryschedulev2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by alex on 12.06.15.
 */
public class Day {

    private ArrayList<Lesson> lessonList;
    private int dayId;

    public Day(int dayId){
        lessonList = new ArrayList<Lesson>();
        this.dayId = dayId;
    }

    public ArrayList<Lesson> getLessonList() {
        return lessonList;
    }

    public void sortLessons(){
        Collections.sort(lessonList);
    }

    public int getDayId() {
        return dayId;
    }
}
