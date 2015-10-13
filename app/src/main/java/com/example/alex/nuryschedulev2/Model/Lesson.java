package com.example.alex.nuryschedulev2.Model;

import java.io.Serializable;

/**
 * Created by alex on 12.06.15.
 */
public class Lesson implements Comparable<Lesson>, Serializable {

    private int numberLesson;
    private String nameLesson;
    private String typeLesson;
    private String placeLesson;
    private String timeLesson;

    public static String [] timeLessonList;

    public Lesson(int numberLesson, String nameLesson, String typeLesson, String placeLesson, String timeLessn){
        this.nameLesson = nameLesson;
        this.numberLesson = numberLesson;
        this.placeLesson = placeLesson;
        this.timeLesson = timeLessn;
        this.typeLesson = typeLesson;
    }

    public int getNumberLesson() {
        return numberLesson;
    }

    public String getNameLesson() {
        return nameLesson;
    }

    public String getTypeLesson() {
        return typeLesson;
    }

    public String getPlaceLesson() {
        return placeLesson;
    }

    public String getTimeLesson() {
        return timeLesson;
    }

    @Override
    public int compareTo(Lesson another) {
        int res = Integer.compare(this.numberLesson, another.getNumberLesson());
        return res;
    }
}

