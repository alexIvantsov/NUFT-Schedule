package com.example.alex.nuryschedulev2;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.alex.nuryschedulev2.HelpClasses.StyleFactory;
import com.example.alex.nuryschedulev2.Model.Day;
import com.example.alex.nuryschedulev2.Model.Lesson;
import com.example.alex.nuryschedulev2.Schedule.ScheduleFactory;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekContainer extends Fragment {

    private int numberWeek;
    private int viewHeight;

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Day> days_list;
        ScrollView scrollView = (ScrollView) getView().findViewById(R.id.scroll_view);
        try {
            days_list = ScheduleFactory.getSchedule(getActivity()).getWeek(numberWeek);
        } catch (NullPointerException e) {
            return;
        }
        boolean flag = false;
        viewHeight = 0;
        for (Day day : days_list) {
            try {
                LinearLayout weekContainer = (LinearLayout) getView().findViewById(R.id.week_container);
                final View myView = getActivity().getLayoutInflater().inflate(R.layout.list_lessons, null);
                LinearLayout listLessons = (LinearLayout) myView.findViewById(R.id.list_lessons);

                ArrayList<Lesson> lessons_list = day.getLessonList();

                View view1 = getActivity().getLayoutInflater().inflate(R.layout.schedule_list_header_item, null);
                view1.setBackgroundColor(getResources().getColor(StyleFactory.getBlockHeaderColor()));
                TextView dayName = (TextView) view1.findViewById(R.id.day_name);
                dayName.setText(day.getDayName());
                listLessons.addView(view1);

                for (Lesson lesson : lessons_list) {
                    View lessonView = getActivity().getLayoutInflater().inflate(R.layout.schedule_list_item, null);

                    TextView tv = (TextView) lessonView.findViewById(R.id.name);
                    tv.setText(lesson.getNameLesson());

                    tv = (TextView) lessonView.findViewById(R.id.number);
                    tv.setText(Integer.toString(lesson.getNumberLesson()));

                    tv = (TextView) lessonView.findViewById(R.id.type_place);
                    tv.setText(lesson.getTypeLesson() + " " + lesson.getPlaceLesson());

                    tv = (TextView) lessonView.findViewById(R.id.time);
                    tv.setText(lesson.getTimeLesson());

                    tv = (TextView) lessonView.findViewById(R.id.teacher);
                    tv.setText(lesson.getTeacher());

                    listLessons.addView(lessonView);
                }

                weekContainer.addView(myView);
                Calendar calendar = Calendar.getInstance();
                int dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayNumber == (day.getDayNumber() + 2)) flag = true;

                if (!flag) {
                    ViewTreeObserver viewTreeObserver = myView.getViewTreeObserver();
                    if (viewTreeObserver.isAlive()) {
                        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onGlobalLayout() {
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                    myView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                } else {
                                    myView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                }
                                viewHeight += myView.getHeight();
                            }
                        });
                    }
                }
            } catch (NullPointerException e) {

            }
        }
        final ScrollView mainScroll = (ScrollView) getView().findViewById(R.id.scroll_view);

        ViewTreeObserver vto = scrollView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                mainScroll.smoothScrollTo(0, viewHeight);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.week_container, container, false);
        return rootView;
    }

    public static WeekContainer newInstance(int numberWeek) {
        WeekContainer weekContainer = new WeekContainer();
        weekContainer.setNumberWeek(numberWeek);
        return weekContainer;
    }

    public void setNumberWeek(int numberWeek) {
        this.numberWeek = numberWeek;
    }
}