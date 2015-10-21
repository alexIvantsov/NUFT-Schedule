package com.example.alex.nuryschedulev2.HelpClasses;

import android.app.Activity;

import com.example.alex.nuryschedulev2.HelpClasses.MyPreferences;
import com.example.alex.nuryschedulev2.R;

/**
 * Created by Oleksandr on 13.10.2015.
 */
public class StyleFactory {

    public static String color;
    private static String color_tag = "color";

    public static void init(Activity activity){
       if(color == null || color.equals("")) {
           color = MyPreferences.load(color_tag, activity);
       }
       if(color == null || color.equals("")) {
           color = "red";
       }
       save(activity);
    }
    public static  void save(Activity activity){
        MyPreferences.save(color_tag, color, activity);
    }

    public static int getNavDrawerImageId(){
        switch (color){
            case "red":
                return R.drawable.menu_image_red;
            case "blue":
                return R.drawable.menu_image_blue;
            case "green":
                return R.drawable.menu_image_green;
            case "black":
                return R.drawable.menu_image_black;
            default:
                return 0;
        }
    }

    public static int getToolbarColorId(){
        switch (color){
            case "red":
                return R.color.toolBar_red;
            case "blue":
                return R.color.toolBar_blue;
            case "green":
                return R.color.toolBar_green;
            case "black":
                return R.color.toolBar_black;
            default:
                return 0;
        }
    }

    public static int getSelectedItemColor(){
        switch (color){
            case "red":
                return R.color.selected_item_red;
            case "blue":
                return R.color.selected_item_blue;
            case "green":
                return R.color.selected_item_green;
            case "black":
                return R.color.selected_item_black;
            default:
                return 0;
        }
    }

    public static int getBlockHeaderColor(){
        switch (color){
            case "red":
                return R.color.header_red;
            case "blue":
                return R.color.header_blue;
            case "green":
                return R.color.header_green;
            case "black":
                return R.color.header_black;
            default:
                return 0;
        }
    }
}
