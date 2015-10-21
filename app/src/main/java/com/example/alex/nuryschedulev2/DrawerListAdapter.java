package com.example.alex.nuryschedulev2;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.alex.nuryschedulev2.HelpClasses.TextViewPlus;

/**
 * Created by Oleksandr on 07.10.2015.
 */
public class DrawerListAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private String[] item;

    public DrawerListAdapter(String [] item, Activity activity) {
        super(activity, 0, item);
        this.activity = activity;
        this.item = item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = activity.getLayoutInflater()
                    .inflate(R.layout.drawer_list_item1, null);
        }
        ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
        icon.setImageResource(getIcon(position));
        TextViewPlus text = (TextViewPlus)convertView.findViewById(R.id.text);
        text.setText(item[position]);
        return convertView;
    }

    private int getIcon(int index){
        switch(index){
            case 0:
                return R.drawable.ic_action_action_list;
            case 1:
                return R.drawable.ic_action_action_done;
            case 2:
                return R.drawable.ic_action_maps_map;
            case 3:
                return R.drawable.ic_action_action_settings;
            case 4:
                return R.drawable.ic_action_action_info;

            default:
                return 0;
        }
    }
}