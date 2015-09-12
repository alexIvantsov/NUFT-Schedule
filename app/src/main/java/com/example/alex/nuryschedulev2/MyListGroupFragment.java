package com.example.alex.nuryschedulev2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.nuryschedulev2.Model.Group;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by alex on 11.06.15.
 */
public class MyListGroupFragment extends ListFragment {

    private static ArrayList<Group> groups;
    public static String FRAGMENT_ID = "fragment with update";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(groups==null)
            groups = Group.getGroupList(this.getActivity());

        GroupListAdapter adapter = new GroupListAdapter(groups);
        setListAdapter(adapter);
    }

    private class GroupListAdapter extends ArrayAdapter<Group> {

        public GroupListAdapter(ArrayList<Group> hamster) {

            super(getActivity(), 0, hamster);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.group_list_item, null);
            }

            Group group = getItem(position);
            TextView nameView = (TextView) convertView.findViewById(R.id.groupName);
            String groupName = group.getName();
            nameView.setText(groupName);
            FrameLayout frameLayout = (FrameLayout)convertView.findViewById(R.id.frame);

            //First letter of group name in color circle
            char [] letter = groupName.toCharArray();
            String firstLetter = String.valueOf(letter[0]);
            TextView firstLetterView = (TextView)convertView.findViewById(R.id.First_Lettter_Group_Name);
            firstLetterView.setText(firstLetter);
            FrameLayout quard = (FrameLayout)convertView.findViewById(R.id.color_letter);
            Random random = new Random();
            quard.setBackgroundColor(Color.rgb(random.nextInt(250), random.nextInt(200), random.nextInt(250)));

            ImageView image_mask = (ImageView)convertView.findViewById(R.id.imageView);
            if(group.isSelected()){
                frameLayout.setBackgroundColor(0xFFFF1308);
                ((TextView)convertView.findViewById(R.id.groupName)).setTextColor(0xffffffff);
                image_mask.setImageResource(R.drawable.red_circle);
            }else{
                frameLayout.setBackgroundColor(0x0000);
                ((TextView)convertView.findViewById(R.id.groupName)).setTextColor(0xff000000);
                image_mask.setImageResource(R.drawable.circle);
            }
            return convertView;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        for(Group group: groups){
            group.setSelected(false);
        }
        Fragment fragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Name", groups.get(position).getName());
        fragment.setArguments(bundle);
        groups.get(position).setSelected(true);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_ID).commit();

    }
}
