package com.example.alex.nuryschedulev2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alex on 16.06.15.
 */
public class WeekContainer extends Fragment {

    private int numberWeek;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Day> days_list;
        try {
            days_list = Schedule.schedule.getWeek(numberWeek);
        }
        catch (NullPointerException e){
            return;
        }
        for(Day day: days_list) {
            try {
                LinearLayout weekContainer = (LinearLayout) getView().findViewById(R.id.week_container);
                View myView = getActivity().getLayoutInflater().inflate(R.layout.list_lessons, null);
                ListView listLessons = (ListView) myView.findViewById(R.id.list_lessons);

                ArrayList<Lesson> lessons_list = day.getLessonList();;

                View view1 = getActivity().getLayoutInflater().inflate(R.layout.schedule_list_header_item, null);
                TextView dayName = (TextView)view1.findViewById(R.id.day_name);
                dayName.setText(getResources().getStringArray(R.array.day_name)[day.getDayId()]);
                listLessons.addHeaderView(view1);

                listLessons.setAdapter(new LessonListAdapter(lessons_list));
                Helper.getListViewSize(listLessons);
                weekContainer.addView(myView);
                listLessons.setOnItemClickListener(new MyOnItemClickListener(getActivity().getApplicationContext()));
            } catch (NullPointerException e) {

            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.week_container, container, false);
        return rootView;
    }

    public static WeekContainer newInstance(int numberWeek){
        WeekContainer weekContainer = new WeekContainer();
        weekContainer.setNumberWeek(numberWeek);
        return  weekContainer;
    }

    public void setNumberWeek(int numberWeek) {
        this.numberWeek = numberWeek;
    }

    private class LessonListAdapter extends ArrayAdapter<Lesson> {

        private ArrayList<Lesson> lessons;

        public LessonListAdapter(ArrayList<Lesson> schedule) {
            super(getActivity(), 0, schedule);
            this.lessons = schedule;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

           // if (convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.schedule_list_item, null);

            Lesson lesson = lessons.get(position);

            TextView tv = (TextView) convertView.findViewById(R.id.name);
            tv.setText(lesson.getNameLesson());

            tv = (TextView) convertView.findViewById(R.id.number);
            tv.setText(Integer.toString(lesson.getNumberLesson()));

            tv = (TextView) convertView.findViewById(R.id.type_place);
            tv.setText(lesson.getTypeLesson() + " " + lesson.getPlaceLesson());

            tv = (TextView) convertView.findViewById(R.id.time);
            tv.setText(lesson.getTimeLesson());

            return convertView;
        }
    }
}
