package com.ashleigh.myalarm.data;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.ashleigh.myalarm.AlarmFragment;
import com.ashleigh.myalarm.R;
import com.ashleigh.myalarm.model.Alarm;
import com.ashleigh.myalarm.utils.ParcelableUtil;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getSimpleName();

    public static final String NOTIFICATION_ID = "notification_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "alarm fired");
        try{
            Alarm alarm = ParcelableUtil.unmarshall(intent.getExtras().getByteArray(
                    AlarmController.ARG_ITEM), Alarm.CREATOR);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            createNotification(context, id, alarm);

        } catch (Exception e) {
            Log.i(TAG, "error receiving alarm");
        }
    }

    private void createNotification(Context mContext, int id, Alarm model) {

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext,
                MyAlarmApp.CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_important_black_18dp)
                .setContentTitle(model.getmName())
                .setContentText("Alarm fired").setSound(alarmSound)
                .setVibrate(new long[]{1000, 1000, 1000, 1000});

        Intent resultIntent = new Intent(mContext, AlarmFragment.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext, model.getmId(),
                resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, mBuilder.build());
    }
}
