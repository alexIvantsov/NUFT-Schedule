package com.example.alex.nuryschedulev2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.nuryschedulev2.About.AboutActivity;
import com.example.alex.nuryschedulev2.HelpClasses.StyleFactory;
import com.example.alex.nuryschedulev2.MapUniversity.MapActivity;
import com.example.alex.nuryschedulev2.Schedule.ScheduleFactory;
import com.example.alex.nuryschedulev2.Settings.SettingsActivity;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class MainActivity extends AppCompatActivity {

    private String[] mScreenTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    public static Context context;

    protected abstract Fragment getCurrentFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        StyleFactory.init(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(StyleFactory.getToolbarColorId()));
        setSupportActionBar(toolbar);

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

        /* Set the drawer toggle as the DrawerListener */
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragmentContainer, getCurrentFragment()).commit();
    }

    private void setDrawerListHeader(){
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        String dayLongName = Calendar.getInstance().getDisplayName(
                Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        View headerView = getLayoutInflater().inflate(R.layout.drawer_list_header, null);
        ImageView imageView = (ImageView)headerView.findViewById(R.id.imageView);
        imageView.setImageResource(StyleFactory.getNavDrawerImageId());
        TextView currentDate = (TextView)headerView.findViewById(R.id.current_date);
        currentDate.setText(currentDateTimeString);
        TextView currentDay = (TextView)headerView.findViewById(R.id.current_day);
        currentDay.setText(dayLongName);
        mDrawerList.addHeaderView(headerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle != null &&
                mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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
        Fragment fragment = null;
        Class activityClass = null;
        switch (position) {
            case 1:
                fragment = new ListGroupsFragment();
                break;
            case 2:
                if(ScheduleFactory.getSchedule(this).hasLoadedSchedule()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("load_from_cloud", false);
                    fragment = new ViewPagerFragment();
                    fragment.setArguments(bundle);
                }
                else
                    Toast.makeText(this, getResources().getString(R.string.group_not_selected),
                        Toast.LENGTH_SHORT).show();
                break;
            case 3:
                activityClass = MapActivity.class;
                break;
            case 4:
                activityClass = SettingsActivity.class;
                break;
            case 5:
                activityClass = AboutActivity.class;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment).commit();
        } else {
            if(activityClass != null){
                Intent intent = new Intent(context, activityClass);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        }

        mDrawerList.setItemChecked(position, true);
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

