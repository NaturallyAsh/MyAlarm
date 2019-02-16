package com.ashleigh.myalarm.views;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.ashleigh.myalarm.R;

import androidx.appcompat.app.AlertDialog;

public class CountdownPickerDialog extends AlertDialog implements NumberPicker.OnValueChangeListener,
        DialogInterface.OnClickListener {

    private final NumberPicker mNumberPicker;
    private OnCountdownSetListener listener;


    public interface OnCountdownSetListener {
        void CountdownSet(NumberPicker view);
    }

    protected CountdownPickerDialog(Context context, int themeResId) {
        super(context, themeResId);

        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.number_picker_layout, null);
        setView(view);
        setButton(BUTTON_POSITIVE, themeContext.getString(R.string.okString), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(R.string.cancel), this);

        mNumberPicker = view.findViewById(R.id.number_picker);
        mNumberPicker.setOnValueChangedListener(this);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (listener != null) {
                    listener.CountdownSet(mNumberPicker);
                }
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }
}
