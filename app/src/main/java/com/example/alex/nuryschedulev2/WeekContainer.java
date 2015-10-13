package com.example.alex.nuryschedulev2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.alex.nuryschedulev2.Model.Day;
import com.example.alex.nuryschedulev2.Model.Lesson;
import com.example.alex.nuryschedulev2.Schedule.Schedule;
import com.example.alex.nuryschedulev2.Schedule.ScheduleFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by alex on 16.06.15.
 */
public class WeekContainer extends Fragment {

    private int numberWeek;
    private int viewHeight;

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Day> days_list;
        ScrollView scrollView = (ScrollView)getView().findViewById(R.id.scroll_view);
        try {
            days_list = ScheduleFactory.getSchedule().getWeek(numberWeek);
        }
        catch (NullPointerException e){
            return;
        }
        boolean flag = false;
        viewHeight = 0;
        for(Day day: days_list) {
            try {
                LinearLayout weekContainer = (LinearLayout) getView().findViewById(R.id.week_container);
                final View myView = getActivity().getLayoutInflater().inflate(R.layout.list_lessons, null);
                ListView listLessons = (ListView) myView.findViewById(R.id.list_lessons);

                ArrayList<Lesson> lessons_list = day.getLessonList();;

                View view1 = getActivity().getLayoutInflater().inflate(R.layout.schedule_list_header_item, null);
                TextView dayName = (TextView)view1.findViewById(R.id.day_name);
                dayName.setText(getResources().getStringArray(R.array.day_name)[day.getDayId()]);
                listLessons.addHeaderView(view1);

                listLessons.setAdapter(new LessonListAdapter(lessons_list));
                getListViewSize(listLessons);
                weekContainer.addView(myView);
                String dayLongName = Calendar.getInstance().getDisplayName(
                        Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                String currentday = getResources().getStringArray(R.array.day_name)[day.getDayId()];

                if(dayLongName.equals(currentday)) flag = true;

                if(!flag) {
                    ViewTreeObserver viewTreeObserver = myView.getViewTreeObserver();
                    if (viewTreeObserver.isAlive()) {
                        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                myView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                viewHeight += myView.getHeight();
                            }
                        });
                    }
                }
                listLessons.setOnItemClickListener(new MyOnItemClickListener(getActivity().getApplicationContext()));
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

    private void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }
}
