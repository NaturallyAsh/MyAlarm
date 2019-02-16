package com.ashleigh.myalarm.views;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

public class MyCountdownPicker extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new CountdownPickerDialog(getActivity(), 2);
    }

}
