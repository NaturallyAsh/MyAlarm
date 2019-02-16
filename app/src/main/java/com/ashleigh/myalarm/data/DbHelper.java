package com.ashleigh.myalarm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.ashleigh.myalarm.model.Alarm;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    private static DbHelper instance = null;
    private Context mContext;
    private static final String DATABASE_NAME = "myalarmapp.db";
    private static final int DATABASE_VERSION = 11;


    public static synchronized DbHelper getInstance() {
        return getInstance(MyAlarmApp.getInstance());
    }

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
            Log.i(TAG, "db instance is null");
        }
        return instance;
    }

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    String[] alarmColumns = new String[] {
            AlarmEntry._ID,
            AlarmEntry.COLUMN_NAME,
            AlarmEntry.COLUMN_HOUR,
            AlarmEntry.COLUMN_MIN,
            AlarmEntry.COLUMN_DAYS
    };

    public static final class AlarmEntry implements BaseColumns {
        public static final String TABLE_NAME = "ALARM";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_MIN = "min";
        public static final String COLUMN_DAYS = "days";
        public static final String COLUMN_ALARM_FIRED = "alarm_fired";
    }

    String SQL_CREATE_ALARM_TABLE = "CREATE TABLE " + AlarmEntry.TABLE_NAME +
        " ("
        + AlarmEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + AlarmEntry.COLUMN_NAME + " TEXT, "
        + AlarmEntry.COLUMN_HOUR + " INTEGER, "
        + AlarmEntry.COLUMN_MIN + " INTEGER, "
        + AlarmEntry.COLUMN_DAYS + " TEXT, "
        + AlarmEntry.COLUMN_ALARM_FIRED + " INTEGER);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALARM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AlarmEntry.TABLE_NAME);
        onCreate(db);
    }

    public long addAlarm(Alarm model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if (model.getmId() != 0) {
            values.put(AlarmEntry._ID, model.getmId());
        }
        values.put(AlarmEntry.COLUMN_NAME, model.getmName());
        values.put(AlarmEntry.COLUMN_HOUR, model.getmHour());
        values.put(AlarmEntry.COLUMN_MIN, model.getmMin());
        values.put(AlarmEntry.COLUMN_DAYS, model.getDayList());

        long res = db.insertWithOnConflict(AlarmEntry.TABLE_NAME, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return res;
    }

    public Cursor getAlarmCursor() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(AlarmEntry.TABLE_NAME, alarmColumns, null, null,
                null, null, null);
    }

    public Alarm getAlarmAt(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(AlarmEntry.TABLE_NAME, alarmColumns, null, null,
                null, null, null);

        if (cursor.moveToPosition(position)) {
            Alarm model = new Alarm();
            model.setmId(cursor.getInt(0));
            model.setmName(cursor.getString(1));
            model.setmHour(cursor.getInt(2));
            model.setmMin(cursor.getInt(3));
            model.setDayList(cursor.getString(4));
            cursor.close();
            return model;
        }
        return null;
    }

    public long removeAlarm(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(AlarmEntry.TABLE_NAME, AlarmEntry._ID + " =?",
                new String[]{String.valueOf(id)});
    }

    public int getAlarmCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {AlarmEntry._ID};

        Cursor cursor = db.query(AlarmEntry.TABLE_NAME, projection, null, null,
                null, null, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void setAlarmFired(int id, boolean fired) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AlarmEntry.COLUMN_ALARM_FIRED, fired);
        db.update(AlarmEntry.TABLE_NAME, values,
                AlarmEntry._ID + " =?", new String[]{String.valueOf(id)});
    }
}
