package com.example.alex.nuryschedulev2.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Day implements Serializable {

    private ArrayList<Lesson> lessonList;
    private String name;
    private int dayNumber;

    public Day(String name, int dayNumber){
        lessonList = new ArrayList<>();
        this.name = name;
        this.dayNumber = dayNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public ArrayList<Lesson> getLessonList() {
        return lessonList;
    }

    public void sortLessons(){
        Collections.sort(lessonList);
    }

    public String getDayName() {
        return name;
    }
}
