package com.ashleigh.myalarm.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable {

    private int mId;
    private String mName;
    private int mHour;
    private int mMin;
    private String dayList;
    private Boolean alarmFired;

    public Alarm(int id, String name, int hour, int min, String days) {
        this.mId = id;
        this.mName = name;
        this.mHour = hour;
        this.mMin = min;
        this.dayList = days;
    }

    public Alarm(String name, int hour, int min, String days) {
        this.mName = name;
        this.mHour = hour;
        this.mMin = min;
        this.dayList = days;
    }

    public Alarm() {

    }

    public int getmId() {
        return mId;
    }
    public void setmId(int id) {
        this.mId = id;
    }
    public String getmName() {
        return mName;
    }
    public void setmName(String name) {
        this.mName = name;
    }

    public int getmHour() {
        return mHour;
    }

    public void setmHour(int mHour) {
        this.mHour = mHour;
    }

    public int getmMin() {
        return mMin;
    }

    public void setmMin(int mMin) {
        this.mMin = mMin;
    }

    public String getDayList() {
        return dayList;
    }

    public void setDayList(String dayList) {
        this.dayList = dayList;
    }

    public Boolean isReminderFired() {
        return this.alarmFired != null && this.alarmFired;
    }

    public void setReminderFired(Boolean alarmFired) {
        this.alarmFired = alarmFired;
    }
    public void setReminderFired(int alarmFired) {
        this.alarmFired = alarmFired == 1;
    }

    protected Alarm(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mHour = in.readInt();
        mMin = in.readInt();
        dayList = in.readString();
        setReminderFired(in.readInt());
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mHour);
        dest.writeInt(mMin);
        dest.writeString(dayList);
        dest.writeInt(isReminderFired() ? 1 : 0);
    }
}
