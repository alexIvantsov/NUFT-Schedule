package com.example.alex.nuryschedulev2.MapUniversity;

import android.support.v4.app.Fragment;
import com.example.alex.nuryschedulev2.SecondaryActivity;

/**
 * Created by Oleksandr on 20.10.2015.
 */
public class MapActivity extends SecondaryActivity {
    @Override
    public Fragment getFragment() {
        return new MapFragment();
    }
}
