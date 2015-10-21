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
import com.astuetz.PagerSlidingTabStrip;
import com.example.alex.nuryschedulev2.HelpClasses.InternetAccess;
import com.example.alex.nuryschedulev2.HelpClasses.MyPreferences;
import com.example.alex.nuryschedulev2.Schedule.ScheduleFactory;

public class ViewPagerFragment extends Fragment {

    private static ViewPager mViewPager;
    private static FrameLayout progress;
    public String groupName = "";
    private FragmentManager fragmentManager;

    public ViewPagerFragment (){
        this.setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = (FrameLayout)getView().findViewById(R.id.progress);

        groupName = MyPreferences.load(MyPreferences.saved_group_name, getActivity());
        Bundle bundle = getArguments();
        boolean fromCloud = bundle.getBoolean("load_from_cloud");

        new AsTask().execute(fromCloud);

        mViewPager = (ViewPager)view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int pos) {
                return WeekContainer.newInstance(pos+1);
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

    private class AsTask extends AsyncTask<Boolean, Void, Boolean> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Boolean... params) {
            if (params[0]){
                if (!InternetAccess.isOnline(getActivity())) {
                    showFragmentNoInternetAccess();
                } else {
                    return ScheduleFactory.getSchedule(getActivity()).updateShedule(groupName);
                }
            }else {
                return ScheduleFactory.getSchedule(getActivity()).loadShedule("");
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result && getActivity() != null) {
                getActivity().setTitle(ScheduleFactory.getSchedule(getActivity()).getGroupName());
                mViewPager.getAdapter().notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }else{
                showFragmentNoInternetAccess();
            }
        }
    }


    private void showFragmentNoInternetAccess(){
        Bundle bundle = getArguments();
        bundle.putString("class_name", this.getClass().getName());
        Fragment fragment = new NoInternetAccessFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }
}
