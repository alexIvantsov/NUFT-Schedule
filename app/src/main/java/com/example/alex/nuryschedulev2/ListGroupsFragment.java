package com.example.alex.nuryschedulev2;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
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

import com.example.alex.nuryschedulev2.HelpClasses.InternetAccess;
import com.example.alex.nuryschedulev2.HelpClasses.MyPreferences;
import com.example.alex.nuryschedulev2.HelpClasses.StyleFactory;
import com.example.alex.nuryschedulev2.Schedule.ScheduleFactory;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by alex on 11.06.15.
 */
public class ListGroupsFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getResources().getStringArray(R.array.screen_array)[0]);

        if(InternetAccess.isOnline(getActivity()))
            new AsTask().execute();
        else {
            showNoInternetAccessFragment();
        }
    }

    private void showNoInternetAccessFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new NoInternetAccessFragment();
        Bundle bundle = new Bundle();
        bundle.putString("class_name", this.getClass().getName());
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    private class GroupListAdapter extends ArrayAdapter<String> {

        public GroupListAdapter(ArrayList<String> groups) {
            super(getActivity(), 0, groups);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.group_list_item, null);
            }

            String groupName = getItem(position);
            TextView nameView = (TextView) convertView.findViewById(R.id.groupName);
            nameView.setText(groupName);
            FrameLayout frameLayout = (FrameLayout)convertView.findViewById(R.id.frame);

            //First letter of group name in color circle
            char [] letter = groupName.toCharArray();
            String firstLetter = String.valueOf(letter[0]);
            //because first row has "begin file" char
            if (position == 0)
                firstLetter = String.valueOf(letter[1]);

            TextView firstLetterView = (TextView)convertView.findViewById(R.id.First_Lettter_Group_Name);
            firstLetterView.setText(firstLetter);
            GradientDrawable bgShape = (GradientDrawable)firstLetterView.getBackground();
            Random random = new Random();
            bgShape.setColor(Color.argb(255, random.nextInt(200),random.nextInt(200), random.nextInt(200)));

            String savedName = ScheduleFactory.getSchedule(getActivity()).getGroupName();
            if(groupName.equals(savedName)){
                frameLayout.setBackgroundColor(getResources().getColor(StyleFactory.getSelectedItemColor()));
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
        String selectedName = (String)((TextView)v.findViewById(R.id.groupName)).getText();
        Fragment fragment = new ViewPagerFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean("load_from_cloud", true);
        fragment.setArguments(bundle);

        MyPreferences.save(MyPreferences.saved_group_name, selectedName, getActivity());
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();

    }

    private class AsTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            return ScheduleFactory.getSchedule(getActivity()).getGroupsNameList();
        }
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if(getActivity() != null) {
                if(result != null) {
                    GroupListAdapter adapter = new GroupListAdapter(result);
                    setListAdapter(adapter);
                }else{
                    showNoInternetAccessFragment();
                }
            }
        }
    }
}
