package com.ashleigh.myalarm.adapter;

import android.content.Context;

import com.ashleigh.myalarm.AlarmFragment;
import com.ashleigh.myalarm.StopwatchFragment;
import com.ashleigh.myalarm.TimerFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int pageCount = 3;
    private Context mContext;
    private String tabNames[] = new String[]{"Alarm", "Timer", "Stopwatch"};

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AlarmFragment();
            case 1:
                return new TimerFragment();
            case 2:
                return new StopwatchFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
