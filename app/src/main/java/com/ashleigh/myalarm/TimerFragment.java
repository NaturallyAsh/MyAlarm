package com.ashleigh.myalarm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ashleigh.myalarm.views.MyTimePicker;

import java.text.Format;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerFragment extends Fragment implements MyTimePicker.TimePickerListener {

    @BindView(R.id.timer_showButton)
    Button showButton;
    @BindView(R.id.timer_stop_button)
    Button stopButton;
    @BindView(R.id.timer_resume_button)
    Button resumeButton;
    @BindView(R.id.timer_reset_button)
    Button resetButton;
    @BindView(R.id.timer_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.timer_countdownTV)
    TextView countdownTV;
    private String FORMAT = "%02d:%02d:%02d";
    private CountDownTimer countDownTimer;

    public TimerFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_timer, container, false);
        ButterKnife.bind(this, rootView);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFrag = new MyTimePicker();
                newFrag.setTargetFragment(TimerFragment.this, 0);
                newFrag.show(getFragmentManager(), "timepicker");
                //DialogFragment newFrag = new MyCountdownPicker();
                //newFrag.show(getChildFragmentManager(), "countpicker");
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer.onFinish();
                }
            }
        });

        return rootView;
    }

    @Override
    public void OnTimePicked(int hour, int minute) {
        Toast.makeText(getContext(), "hour: " + hour + " min: " + minute,
                Toast.LENGTH_SHORT).show();

        showButton.setVisibility(View.INVISIBLE);
        countdownTV.setVisibility(View.VISIBLE);

        startCountdown(hour, minute);
    }

    private void startCountdown(int hour, int min) {

        long milliSec = (hour * 1000 * 60 * 60) + (min * 1000 * 60);

        countDownTimer = new CountDownTimer(milliSec, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTV.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                        )));
            }

            @Override
            public void onFinish() {
                countdownTV.setText(R.string.done_string);
            }
        }.start();
    }
}
