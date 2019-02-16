package com.ashleigh.myalarm.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class DateHelper {

    private static final String TAG = DateHelper.class.getSimpleName();

    private static final SimpleDateFormat SHORT_WEEK_DAYS_FORMAT = new SimpleDateFormat("E", Locale.getDefault());


    public static ArrayList<String> getShortWeekDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        ArrayList<String> weekDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            //weekDays[i] = SHORT_WEEK_DAYS_FORMAT.format(calendar.getTime());
            weekDays.add(SHORT_WEEK_DAYS_FORMAT.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        Log.i(TAG, "weekdays: " + weekDays.toString());
        return weekDays;
    }
}
