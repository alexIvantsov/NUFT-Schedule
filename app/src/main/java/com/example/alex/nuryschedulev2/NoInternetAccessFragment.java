package com.example.alex.nuryschedulev2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.alex.nuryschedulev2.Schedule.ScheduleFactory;

/**
 * Created by Oleksandr on 18.10.2015.
 */
public class NoInternetAccessFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Button button= (Button) view.findViewById(R.id.try_again_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Bundle bundle = getArguments();
                String className = bundle.getString("class_name");
                Class c = null;
                try {
                    c = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Fragment fragment = null;
                try {
                    fragment = (Fragment)(c.newInstance());
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                bundle = new Bundle();
                bundle.putBoolean("load_from_cloud", true);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment).commit();
            }
        });

        button = (Button) view.findViewById(R.id.show_saved_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(ScheduleFactory.getSchedule(getActivity()).hasSavedSchedule()) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new ViewPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("load_from_cloud", false);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            }else {
                Toast.makeText(getActivity(), R.string.no_saved_schedule, Toast.LENGTH_SHORT);
            }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.no_internet_access, container, false);
        return rootView;
    }
}
