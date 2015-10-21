package com.example.alex.nuryschedulev2.Settings;

import android.content.Intent;
import android.support.v4.app.Fragment;
import com.example.alex.nuryschedulev2.BeginActivity;
import com.example.alex.nuryschedulev2.SecondaryActivity;

public class SettingsActivity extends SecondaryActivity {

    @Override
    public Fragment getFragment() {
        return new SettingsFragment();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, BeginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
