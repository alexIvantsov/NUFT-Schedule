package com.example.alex.nuryschedulev2;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.nuryschedulev2.Model.Lesson;
import com.example.alex.nuryschedulev2.Schedule.ScheduleFactory;
import com.example.alex.nuryschedulev2.Schedule.Serealisation;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class MainActivity extends ActionBarActivity {

    private String[] mScreenTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    public static Activity activity;
    public static Context context;

    Fragment fragment = null;

    protected abstract Fragment getCurrentFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //initial timeLessonArray
        if(Lesson.timeLessonList == null){
            Lesson.timeLessonList = getResources().getStringArray(R.array.time_lessons);
        }

        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));

        activity = this.getParent();
        mScreenTitles = getResources().getStringArray(R.array.screen_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setDrawerListHeader();

        mDrawerList.setAdapter(new DrawerListAdapter(mScreenTitles, this));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        );

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        if(ScheduleFactory.loadSavedShedule(getApplicationContext())){
            fragment = new ViewPagerFragment();
        }else{
            fragment = getCurrentFragment();
        }
        fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();

    }

    private void setDrawerListHeader(){
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        String dayLongName = Calendar.getInstance().getDisplayName(
                Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        View headerView = getLayoutInflater().inflate(R.layout.drawer_list_header, null);
        ImageView imageView = (ImageView)headerView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.menu_image_1);
        TextView currentDate = (TextView)headerView.findViewById(R.id.current_date);
        currentDate.setText(currentDateTimeString);
        TextView currentDay = (TextView)headerView.findViewById(R.id.current_day);
        currentDay.setText(dayLongName);
        mDrawerList.addHeaderView(headerView);
    }

    @Override
    public void onStop(){
        super.onStop();
        Serealisation.writeToFile(getApplicationContext(), ScheduleFactory.getSchedule());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Если событие обработано переключателем, то выходим
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item))
            return true;
        // Иначе — всё как обычно
        return super.onOptionsItemSelected(item);
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            mDrawerLayout.closeDrawer(mDrawerList);
            mDrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectItem(position);
                }
            }, 300);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 1:
                fragment = new MyListGroupFragment();
                break;
            case 2:
                if(!ScheduleFactory.getSchedule().hasSavedSchedule())
                    fragment = new ViewPagerFragment();
                else
                    Toast.makeText(this, getResources().getString(R.string.group_not_selected),
                        Toast.LENGTH_SHORT).show();
                break;
            case 3:
                fragment = new AboutFragment();
                break;
            case 4:
                fragment = new MapFragment();
                break;
            default:
                break;
        }

        // Insert the fragment by replacing any existing fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment).commit();
        } else {
            // Error
            Log.e(this.getClass().getName(), "Error. Fragment is not created");
        }

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null)
            mDrawerToggle.syncState();
    }

}

