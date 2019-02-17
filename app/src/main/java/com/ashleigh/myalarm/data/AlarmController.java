package com.ashleigh.myalarm.data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ashleigh.myalarm.model.Alarm;
import com.ashleigh.myalarm.utils.ParcelableUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class AlarmController {

    private static final String TAG = AlarmController.class.getSimpleName();

    public static final String ARG_ITEM = "alarm_item";
    public static int id;

    public static void addAlarm(Context context, Alarm model) {

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_ITEM, model);
        Intent notifyIntent = new Intent(context, AlarmReceiver.class);
        id = model.getmId();
        notifyIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, id);
        notifyIntent.putExtra(ARG_ITEM, ParcelableUtil.marshall(model));

        long timeToNotify = setDayAlarm(model);

        PendingIntent sendIntent = PendingIntent.getBroadcast(context, id,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.setExact(AlarmManager.RTC_WAKEUP, timeToNotify, sendIntent);
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        date.setTime(timeToNotify);
        calendar.setTime(date);
        Log.i(TAG, "time to notify: " + calendar.getTime());

    }

    private static long setDayAlarm(Alarm model) {
        Calendar calendar = Calendar.getInstance();
        int day = getNextDayAlarm(model);
        if (day <= calendar.get(Calendar.DAY_OF_WEEK)) {
            //calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }
        calendar.set(Calendar.DAY_OF_WEEK, day + 1);
        calendar.set(Calendar.HOUR_OF_DAY, model.getmHour());
        calendar.set(Calendar.MINUTE, model.getmMin());
        Log.i(TAG, "DOW: " + calendar.get(Calendar.DAY_OF_WEEK));
        return calendar.getTimeInMillis();
    }

    private static int getNextDayAlarm(Alarm model) {
        String list = model.getDayList();
        if (list != null) {
            JSONArray getList = null;
            try {
                JSONObject json = new JSONObject(list);
                getList = json.optJSONArray("daysArray");
                if (getList != null) {
                    for (int i = 0; i < getList.length(); i++) {
                        return getList.getInt(i);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
