package com.example.alex.nuryschedulev2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.nuryschedulev2.Model.Group;

import java.util.ArrayList;

/**
 * Created by alex on 11.06.15.
 */
public class MyListGroupFragment extends ListFragment {

    private static ArrayList<Group> groups;
    private static String FRAGMENT_INSTANCE_NAME = "fragment";

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
            nameView.setText(group.getName());
            FrameLayout frameLayout = (FrameLayout)convertView.findViewById(R.id.frame);
            if(group.isSelected()){
                frameLayout.setBackgroundColor(0xC5FF1308);
                ((TextView)convertView.findViewById(R.id.groupName)).setTextColor(0xffffffff);
            }else{
                frameLayout.setBackgroundColor(0x0000);
                ((TextView)convertView.findViewById(R.id.groupName)).setTextColor(0xff000000);
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
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, FRAGMENT_INSTANCE_NAME).commit();

    }
}
