package com.ashleigh.myalarm.views;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class MyTimePicker extends DialogFragment implements
        OnTimeSetListener {

    private TimePickerListener listener;

    public interface TimePickerListener {
        void OnTimePicked(int hour, int minute);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TimePickerListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), 2, this, hour, min,
                true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listener.OnTimePicked(hourOfDay, minute);
    }
}
