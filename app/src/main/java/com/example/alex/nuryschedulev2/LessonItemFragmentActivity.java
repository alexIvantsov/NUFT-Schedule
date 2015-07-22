package com.example.alex.nuryschedulev2;

import android.support.v4.app.Fragment;

/**
 * Created by alex on 22.06.15.
 */
public class LessonItemFragmentActivity extends MainActivity {

    @Override
    protected Fragment getCurrentFragment() {
        return new LessonItemFragment();
    }
}
