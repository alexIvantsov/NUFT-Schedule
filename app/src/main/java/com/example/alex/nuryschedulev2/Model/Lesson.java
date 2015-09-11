package com.example.alex.nuryschedulev2.Model;

/**
 * Created by alex on 12.06.15.
 */
public class Lesson implements Comparable<Lesson> {

    private int numberLesson;
    private String nameLesson;
    private String typeLesson;
    private String placeLesson;
    private String timeLesson;

    public static String [] timeLessonList = {"8.15 - 9.35", "9.50 - 11.10", "11.20 - 12.40", "13.20 - 14.40",
            "14.50 - 16.10", "16.30- 17.50", "18.00 - 19.20", "19.30 - 20.50"};

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

