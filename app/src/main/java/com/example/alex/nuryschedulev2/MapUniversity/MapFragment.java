package com.example.alex.nuryschedulev2.MapUniversity;

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
import com.example.alex.nuryschedulev2.R;

public class MapFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((FrameLayout)getView().findViewById(R.id.progress)).setVisibility(View.GONE);;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ViewPager mViewPager = (ViewPager)view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int pos) {

                if(pos == 0)
                    return new GoogleMapFragment();
                else
                    return new PictureMapFragment();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                // Generate title based on item position
                return getResources().getStringArray(R.array.map_type)[position];
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
}
