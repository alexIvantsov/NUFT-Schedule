package com.example.alex.nuryschedulev2.Model;

import android.content.Context;

import com.example.alex.nuryschedulev2.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by alex on 11.06.15.
 */
public class Group implements Serializable {

    private String name;
    private String address;
    private boolean selected;
    private static ArrayList<Group> listGroups;

    public Group(String name, String address, boolean selected){
        this.name = name;
        this.address = address;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getAddress(){
        return address;
    }

    public static String getAddress(String groupName) {
        if(listGroups == null) getGroupList(MainActivity.context);
            for(Group group: listGroups){
                if(group.getName().equals(groupName))
                    return group.getAddress();
            }
        return null;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static ArrayList<Group> getGroupList(Context context){
        if(listGroups == null) {
            listGroups = new ArrayList<Group>();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open("groups.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    Group group = new Group(line.split("  ")[0], line.split("  ")[1], false);
                    listGroups.add(group);
                }
            } catch (IOException e) {
                //Помилка
            }
        }
        return listGroups;
    }
}
