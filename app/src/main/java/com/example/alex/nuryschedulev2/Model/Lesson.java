package com.example.alex.nuryschedulev2.Model;

import java.io.Serializable;

public class Lesson implements Comparable<Lesson>, Serializable {

    private int numberLesson;
    private String nameLesson;
    private String typeLesson;
    private String placeLesson;
    private String timeLesson;
    private String teacher;

    public static String [] timeLessonList;

    public Lesson(int numberLesson, String nameLesson, String typeLesson, String placeLesson, String timeLessn, String teacher){
        this.nameLesson = nameLesson;
        this.numberLesson = numberLesson;
        this.placeLesson = placeLesson;
        this.timeLesson = timeLessn;
        this.typeLesson = typeLesson;
        this.teacher = teacher;
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

    public String getTeacher() {
        return teacher;
    }

    @Override
    public int compareTo(Lesson another) {
        return Integer.valueOf(this.numberLesson).compareTo(Integer.valueOf(another.numberLesson));
    }
}

