package com.example.alex.nuryschedulev2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by alex on 11.06.15.
 */
public class ViewPagerFragment extends Fragment {

    private static ViewPager mViewPager;
    private static FrameLayout progress;

    public ViewPagerFragment (){
        this.setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        final String groupName = bundle.getString("Name");
        progress = (FrameLayout)getView().findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        if(Schedule.schedule == null || !Schedule.schedule.getGroupName().equals(groupName)) {
            (new AsTask()).execute(groupName);
            progress.setVisibility(View.VISIBLE);
        }

        FragmentManager fm = getActivity().getSupportFragmentManager();
        mViewPager = (ViewPager)view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int pos) {
                return WeekContainer.newInstance(pos);
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {}
            public void onPageSelected(int pos) {}
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_with_view_pager, container, false);
        return rootView;
    }

    private class AsTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            Schedule.schedule = new Schedule(params[0]);
            Schedule.schedule.getSchedule(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mViewPager.getAdapter().notifyDataSetChanged();
            progress.setVisibility(View.GONE);
        }
    }

}
