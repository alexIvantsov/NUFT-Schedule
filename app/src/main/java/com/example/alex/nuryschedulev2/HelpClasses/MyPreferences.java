package com.example.alex.nuryschedulev2.HelpClasses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Oleksandr on 18.10.2015.
 */
public class MyPreferences {

    public static String saved_group_name = "selected_group";

    public static void save(String key, String value, Activity activity){
        SharedPreferences sPref = activity.getPreferences(activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(key, value);
        ed.commit();
    }

    public static String load(String key, Activity activity){
        SharedPreferences sPref = activity.getPreferences(activity.MODE_PRIVATE);
        return sPref.getString(key, "");
    }
}
