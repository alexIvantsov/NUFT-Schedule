package com.example.alex.nuryschedulev2.Settings;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.nuryschedulev2.R;
import com.example.alex.nuryschedulev2.HelpClasses.StyleFactory;


public class SettingsFragment extends ListFragment {

    String[] colors;
    static int currentPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getResources().getStringArray(R.array.screen_array)[3]);
        colors = getResources().getStringArray(R.array.enable_colors);
        for (int i = 0; i < colors.length; i++) {
            if(colors[i].equals(StyleFactory.color)){
                currentPosition = i;
                break;
            }
        }
        //set as parameter list of items for listview
        GroupListAdapter adapter =
                new GroupListAdapter(new String[1]);
        setListAdapter(adapter);
    }

    private class GroupListAdapter extends ArrayAdapter<String> {

        private String[] content;

        public GroupListAdapter(String[] content) {
            super(getActivity(), 0, content);
            this.content = content;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.settings_list_item, null);
//            String[] contentItem = content[position].split("\\.");
//            TextView tv = (TextView)convertView.findViewById(R.id.item_name);
//            tv.setText(contentItem[0]);
//            tv = (TextView)convertView.findViewById(R.id.item_description);
//            tv.setText(contentItem[1]);
            return convertView;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(++currentPosition == colors.length) {
            currentPosition = 0;
        }
        StyleFactory.color = colors[currentPosition];
        getActivity().recreate();
    }
}
