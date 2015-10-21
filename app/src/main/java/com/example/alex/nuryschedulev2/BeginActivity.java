package com.example.alex.nuryschedulev2;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.alex.nuryschedulev2.HelpClasses.MyPreferences;
import com.example.alex.nuryschedulev2.Schedule.ScheduleFactory;

public class BeginActivity extends MainActivity {

    @Override
    protected Fragment getCurrentFragment() {
        String name = MyPreferences.load(MyPreferences.saved_group_name, this);

        Bundle bundle = new Bundle();
        bundle.putString(MyPreferences.saved_group_name, name);
        bundle.putBoolean("load_from_cloud", false);

        Fragment fragment;
        if(ScheduleFactory.getSchedule(this).hasSavedSchedule()) {
            fragment = new ViewPagerFragment();
        }else {
            fragment = new ListGroupsFragment();
        }
        fragment.setArguments(bundle);

        return fragment;
    }
}
