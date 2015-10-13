package com.example.alex.nuryschedulev2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.alex.nuryschedulev2.Schedule.Schedule;
import com.example.alex.nuryschedulev2.Schedule.ScheduleFactory;

/**
 * Created by alex on 11.06.15.
 */
public class ViewPagerFragment extends Fragment {

    private static ViewPager mViewPager;
    private static FrameLayout progress;
    public String groupName = "";

    public ViewPagerFragment (){
        this.setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        progress = (FrameLayout) getView().findViewById(R.id.progress);
        progress.setVisibility(View.GONE);

        if(getArguments() != null) {
            groupName = bundle.getString("Name");
            if(!ScheduleFactory.getSchedule().isLoadedScheduleGruopName(groupName)) update();
        }else{
            groupName = ScheduleFactory.getSchedule().getGroupName();
        }

        getActivity().setTitle(groupName);

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

            @Override
            public CharSequence getPageTitle(int position) {
                // Generate title based on item position
                return getResources().getStringArray(R.array.number_week)[position];
            }

        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {}
            public void onPageSelected(int pos) {}
        });

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip)getView().findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(mViewPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_with_view_pager, container, false);
        return rootView;
    }

    public void update(){
        if(InternetAccess.isOnline(this.getActivity().getApplicationContext())) {
            progress.setVisibility(View.GONE);
            (new AsTask()).execute(groupName);
            progress.setVisibility(View.VISIBLE);
        }else{
            // message about no internet access
            Toast toast = Toast.makeText(this.getActivity().getApplicationContext(), R.string.no_internet_access, Toast.LENGTH_SHORT);
            toast.show();

            //return to the first fragment
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragmentContainer, new MyListGroupFragment()).commit();
        }
    }

    private class AsTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            //Schedule.schedule = new Schedule(params[0]);
            ScheduleFactory.getSchedule().loadShedule(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mViewPager.getAdapter().notifyDataSetChanged();
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                update();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

}
